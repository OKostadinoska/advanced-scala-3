package com.advancedscala3.part1as

import scala.annotation.tailrec

object Recap {

  // values, types, expressions
  val aCondition = false // values are constants
  val anIfExpression = if (aCondition) 42 else 55 // expressions evaluate to a value

  val aCodeBlock = {
    if (aCondition) 54
    78 // expressions that evaluate the value of its last expression
  }

  // types: Int, String, Double, Boolean, Char, ...
  // Unit has only one possible instance () which is equal to void
  val theUnit = println("Hello Scala")

  // function
  def aFunction(x: Int): Int = x + 1

  // recursion: stack and tail
  @tailrec
  def factorial(n: Int, acc: Int): Int =
    if (n <= 0) acc
    else factorial(n - 1, n * acc)


  val fact10 = factorial(10,1)

  // object oriented programming
  class Animal
  class Dog extends Animal
  val aDog: Animal = new Dog

  trait Carnivore {
    def eat(a: Animal): Unit
  }

  class Crocodile extends Animal with Carnivore {
    override def eat(a: Animal): Unit = println("I'm a croc, I eat everything")
  }

  // method notation
  val aCroc = new Crocodile
  aCroc.eat(aDog)
  aCroc eat aDog // "operator"/infix position

  // anonymous classes
  val aCarnivore = new Carnivore:
    override def eat(a: Animal): Unit = println("I'm a carnivore")

  // generics
  abstract class LList[A] {
    // type A is known inside the implementation
  }

  // singletons and companions
  object LList // companion object, used for instance-independent ("static") fields/methods

  // case class
  case class Person(name: String, age: Int)

  // enums
  enum BasicColor {
    case RED, GREEN, BLUE
  }

  // exceptions and try/catch/finally
  def throwSomeException(): Int =
    throw new RuntimeException

  val aPotentialFailure = try {
    // code that might fail
    throwSomeException()
  } catch {
    case e: Exception => "I caught and exception"
  } finally {
    // closing resources
    println("some important logs")
  }

  // functional programming
  val incrementer = new Function[Int, Int] :
    override def apply(v1: Int): Int = v1 + 1

  val two = incrementer(1)

  // lambdas
  val anonymousIncrementer = (x: Int) => x + 1

  // hof = higher-order functions
  val anIncrementerList = List(1,2,3).map(anonymousIncrementer) // [2,3,4]

  // map, flatMap, filer

  // for-comprehensions
  val pairs = for {
    number <- List(1,2,3)
    char <- List('a', 'b')
  } yield s"$number-$char"

  // Scala collections: Seqs, Arrays, List, Vectors, Maps, Tuples, Sets

  // options, try
  // we can think of options as collections with at most one value
  // options are eligible to for-comprehensions, map, flatMap and etc..
  val anOption: Option[Int] = Option(42)

  // pattern matching
  val x = 2
  val order = x match {
    case 1 => "first"
    case 2 => "second"
    case _ => "not important"
  }

  // braceless syntax
  val pairs_v2 =
    for
      number <- List(1, 2, 3)
      char <- List('a', 'b')
    yield s"$number-$char"
    // same for if, match, while

    // indentation tokes
    class BracelessAnimal:
      def eat(): Unit = {
        println("I'm doing something")
        println("I'm eating")
    }


  val bob = Person("Bob", 22)
  val greeting = bob match {
    case Person(n, _) => s"Hi, my name is $n"
  }

  def main(args: Array[String]): Unit = {

  }

}
