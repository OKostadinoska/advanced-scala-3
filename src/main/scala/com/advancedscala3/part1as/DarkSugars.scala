package com.advancedscala3.part1as

object DarkSugars {

  // 1 - sugar for methods with one argument
  def singleArgMethod(arg: Int): String =
    s"$arg little ducks.."

  // with parentheses
  val aMethodCall = singleArgMethod({
    // write some complex code
    42
  })

  // without parentheses
  val aMethodCall_v2 = singleArgMethod{
    // write some complex code
    42
  }

  // 2 - single abstract method pattern (since Scala 2.12)
  trait Action {
    // can also have other implemented fields/methods here
    def act(x: Int): Int
  }

  val anAction = new Action {
    override def act(x: Int): Int = x + 1
  }

  val anotherAction = (x: Int) => x + 1 // new Action {def act(x: Int): Int = x + 1}

  // example: Runnable
  val aThread = new Thread(new Runnable {
    override def run(): Unit = println("Hi, Scala, from another thread")
  })

  val aSweeterThread = new Thread(() => println("Hi, Scala"))

  // 3 . methods ending in a: are so called RIGHT-ASSOCIATIVE
  val aList = List(1,2,3)
  val aPrependedList = 0 :: aList // :: prepends on the aList and only exists on the List
  val aBigList = 0 :: 1 :: 2 :: List(3,4) // List(3,4).::(2).::(1).::(0)

  class MyStream[T] {
    infix def -->:(value: T): MyStream[T] = this // imp not important
  }

  val myStream = 1 -->: 2 -->: 3 -->: 4 -->: new MyStream[Int]

  // 4 - multi-word identifiers
  class Talker(name: String) {
    infix def `and then said`(gossip: String) = println(s"$name said $gossip")
  }

  val daniel = new Talker("Daniel")
  val danielsStatement = daniel `and then said` "I love Scala"

  // example: HTTP libraries
  object `Content-Type` {
    val `application/json` = "application/JSON"
  }

  // 5 - infix types
  infix class  -->[A, B]
  val compositeType: Int --> String = new -->[Int, String]

  // 6 - update()
  val anArray = Array(1,2,3,4)
  anArray.update(2, 45)
  anArray(2) = 45 // same

  // 7 - mutable fields
  class Mutable {
    private var internalMember: Int = 0
    def member = internalMember // "getter"
    def member_=(value: Int): Unit =
      internalMember = value // "setter"
  }

  val aMuttableContainer = new Mutable
  aMuttableContainer.member =  42 // aMuttableContainer.member_=(42)

  // 8 - variable arguments (varargs)
  // if we would like to access the arguments we can treat them as seq
  // args has the same API as sequence and because is a sequence we can turn that into a List, a Vector, a Set
  def methodWithVarargs(args: Int*) = {
    // return the number of arguments supplied
    args.length
  }
  def callWithZeroArgs = methodWithVarargs()
  def callWithOneArgs = methodWithVarargs(78)
  def callWithTwoArgs = methodWithVarargs(12, 34)

  // dynamicArgs
  val aCollection = List(1,2,3,4)
  val callWithDynamicArgs = methodWithVarargs(aCollection*) // will unwrap all the numbers in the aCollection List and passed them as an argument to args: Int*




  def main(args: Array[String]): Unit = {

  }

}
