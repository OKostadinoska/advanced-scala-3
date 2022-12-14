package com.advancedscala3.part1as

import java.io.ObjectInputFilter.Status
import java.time.Instant
import scala.annotation.tailrec

object AdvancedPatternMatching {

  /*
    PM:
    - constants
    - objects
    - wildcards
    - variables
    - infix patterns
    - lists
    - case classes
  */

  class Person(val name: String, val age: Int)
  class PersonsBrother(val name: String, val age: Int)

  object Person {
    def unapply(person: Person): Option[(String, Int)] =
      if (person.age < 21) None
      else Some((person.name, person.age))

    def unapply(age: Int): Option[String] =
      if (age < 21) Some("minor")
      else Some("legally allowed to drink")
  }

  object PersonsBrother {
    def unapply(PersonsBrother: PersonsBrother): Option[(String, Int)] =
      if (PersonsBrother.age < 21) None
      else Some((PersonsBrother.name, PersonsBrother.age))

    def unapply(age: Int): Option[String] =
      if (age < 21) Some("illegal")
      else Some("legally not allowed to drink")
  }


//  def showBrothersStatus(status: Status): String =
//    status match
//      case Person(name, age) =>
//        s"Daniel's legal drinking status is $status"
//      case PersonsBrother(name, age) =>
//        s"Daniel's legal drinking status is $status"

  val daniel = new Person("Daniel", 102)

  val danielsBrother = new PersonsBrother ("Roger", 20)

  val danielPM = daniel match { // Person.unapply(daniel) => Option((n,a))
    case Person(n, a) => s"Hi there, i am $n"
  }

  val danielsLegalStatus = daniel.age match {
    case Person(status) => s"Daniel's legal drinking status is $status"
  }

  val danielsBrotherLegalStatus = danielsBrother.age match {
    case PersonsBrother(status) => s"Daniel's brother legal drinking status is $status"
  }

//  val brothersStatus = daniel.age & danielsBrother.age match {
//    case Person(status) => s"Daniel's legal drinking status is $status"
//    case PersonsBrother(status) => s"Daniel's brother legal drinking status is $status"
//  }

  // boolean patterns
  object even {
    def unapply(arg: Int): Boolean = arg % 2 == 0
  }

  object singleDigits {
    def unapply(arg: Int): Boolean = arg > -10 && arg < 10
  }

  val n: Int = 43
  val mathProperty = n match {
    case even() => "an even number"
    case singleDigits() => "a one digit number"
    case _ => "no special property"
  }


  // infix patterns
  infix case class Or[A, B](a: A, b: B)
  val anEither = Or(2, "two")
  val humanDescriptionEither = anEither match {
    case number Or string => s"$number is written as $string"
  }

  val aList = List(1,2,3)
  val listPM = aList match {
    case 1 :: rest => "a list starting with 1"
    case _ => "some uninteresting list"
  }

  // decomposing sequences
  val vararg = aList match {
    case List(1, _*) => "a list starting with 1"
    case _ => "some other list"
  }

  abstract class MyList[A] {
    def head: A = throw new NoSuchElementException
    def tail: MyList[A] = throw new NoSuchElementException
  }

  case class Empty[A]() extends MyList[A]
  case class Cons[A](override val head: A, override val tail: MyList[A]) extends MyList[A]

  object MyList {
    def unapplySeq[A](list: MyList[A]): Option[Seq[A]] =
      if (list == Empty()) Some(Seq.empty)
      else unapplySeq(list.tail).map(restOfSequence => list.head +: restOfSequence)
  }

  val myList: MyList[Int] = Cons(1, Cons(2, Cons(3, Empty())))
  val varargCustom = myList match {
    case MyList(1, _*) => "list startin with 1"
    case _ => "some other list"
  }

  // custom return type for unapply
  abstract class Wrapper[T] {
    def isEmpty: Boolean
    def get: T
  }

  object PersonWrapper {
    def unapply(person: Person): Wrapper[String] = new Wrapper[String] {
      override def isEmpty: Boolean = false
      override def get: String = person.name
    }
  }

  val weirdPersonPM = daniel match {
    case PersonWrapper(name) => s"Hey my name is $name"
  }

  //

  case class Job (id: Int, title: String, created: Instant)

  class Database {
    private var jobMap: Map[Int, Job] = Map.empty
    def save(job: Job): Unit = jobMap = jobMap + ((job.id, job))
    def list(): Map[Int, Job] = jobMap
    def get(id: Int): Option[Job] = jobMap.get(id)

    // we need a method for update
//    def update(id: Int, title: String): Option[Job] = jobMap = jobMap + ((id, title))
    def delete(id: Int): Unit = jobMap = jobMap - id
  }
  // example of a database

  // superclass
  sealed trait Event

  case class JobCreated (id: Int, title: String, created: Instant) extends Event
  case class JobUpdated (id: Int, title: String) extends Event
  case class JobDeleted (id: Int) extends Event


  def handleEvent(db: Database, event: Event): Unit = {
    event match
      case JobCreated(id, title, created) => db.save(Job(id, title, created))
      case JobUpdated(id, title) =>
        val maybeOldJob: Option[Job] = db.get(id)
        // copy method allows to construct new instances of a case class
        // while specifying just the fields that are changing
        val maybeNewJob: Option[Job] = maybeOldJob.map(oldJob => oldJob.copy(title = title))
        db.delete(id)
        db.save(maybeNewJob.getOrElse(Job(id, title, Instant.now())))
      case JobDeleted(id) => db.delete(id)
  }


  def main(args: Array[String]): Unit = {
    val database = new Database()
    val job1 = JobCreated(1, "icecream maker", Instant.now())
    val job2 = JobCreated(2, "icecream maker2", Instant.now())
    val jobUpdated = JobUpdated(1, "coffee maker")
    val jobDeleted = JobDeleted(1)


//    def countTo(n: Int): Unit =
//
//      def count(i : Int): Unit =
//        if (i <= n) then
//          println(i)
//          count(i + 1)
//      countTo(5)

    println(danielPM)
    println(danielsLegalStatus)
    println(danielsBrotherLegalStatus)
    //    println(brothersStatus)
    println(mathProperty)
    println(humanDescriptionEither)
    println(listPM)

    println(database.list())

//    handleEvent(database, job1)
    handleEvent(database, job2)

    println(database.list())

    handleEvent(database, jobUpdated)

    println(database.list())





  }

}
