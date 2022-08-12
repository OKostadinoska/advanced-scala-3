package com.basicscala3.part2fp

object mapFlatMapFilterFor {

  val aList = List(1,2,3)

  // map - with map we can transform the elements in the list
  val anIncrementedList = aList.map(_ + 1)

  // filter
  val onlyOddNumbers = aList.filter(_ % 2 != 0)

  // flatMap - is called flatMap because we do not have nested lists
  val toPair = (x: Int) => List(x, x + 1)
  val aFlatMappedList = aList.flatMap(toPair)

  // All the possible combinations of all the elements of those lists, in the format "1a - black"
  val numbers = List(1, 2, 3, 4)
  val chars = List("a", "b", "c", "d")
  val colors = List("black", "white", "red", "yellow")

  // 
  val combinations =  numbers.flatMap(number => chars.flatMap(char => colors.map(color => s"$number$char - $color")))

  def main(args: Array[String]): Unit = {
    println(anIncrementedList)
    println(onlyOddNumbers)
    println(aFlatMappedList)
    println(combinations)
  }

}
