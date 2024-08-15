package com.kanshu.datastructures

object DynamicProgrammingExamplesApp extends App{
  /**
   * Fibonacci Series
   *  0 1 1 2 3 5 8 13 21 34 55 89 144 ....
   *  f(n) = f(n-1) + f(n-2)
   */


    // n = 6 op = 8
    // 7 6 , 5 4 , 3 2 , 1 0
    // 5 4 , 3 2 , 1 0
    // 3 2 , 1 0
    // 1 0
  val recursiveFibonacciNumber : Int => Int = (n : Int) => {
    if(n == 0) 0 else if(n == 1) 1
    else recursiveFibonacciNumber(n-1) + recursiveFibonacciNumber(n-2)
  }

  val startTime = System.nanoTime()
  val res = recursiveFibonacciNumber(6)
  val endTime = System.nanoTime()
  val diff = endTime - startTime
  println(s"res - $res time taken - $diff")









}