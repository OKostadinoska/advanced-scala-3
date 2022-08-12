package com.advancedscala3.part1as

object Either {

  // Either is an alternative to Option -
  // Option has the disadvantage that None carries no information that could tell us why no value is available. Did an error occur? What kind?
  /*
  Represents a value of one of two possible types (a disjoint union).
  An instance of Either is an instance of either scala.util.Left or scala.util.Right.
  A common use of Either is as an alternative to scala.Option for dealing with possibly missing values.
  In this usage:
  scala.None is replaced with a scala.util.Left which can contain useful information.
  scala.util.Right takes the place of scala.Some.
  Convention dictates that Left is used for failure and Right is used for success.
  */



  def main(args: Array[String]): Unit = {

  }

}
