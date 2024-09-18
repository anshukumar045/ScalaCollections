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
  val res = recursiveFibonacciNumber(40)
  val endTime = System.nanoTime()
  val diff = endTime - startTime
  println(s"res - $res time taken - $diff")

  // Top Down Approach
  val fibTopDownApproach : Int => Int = (n: Int) =>
    if(n == 0) 0 else if (n == 1) 1
    else (2 to n).foldLeft(Seq(0,1))((acc, v) => acc :+ (acc(v -1) + acc(v-2))).last

  val startTime1 = System.nanoTime()
  val res1 = fibTopDownApproach(40)
  val endTime1 = System.nanoTime()
  val diff1 = endTime1 - startTime1
  println(s"res - $res1 time taken - $diff1")

  // Bottom up Approach

  val fibBottomUpApproach: Int => Int = (n: Int) =>
    if (n == 0) 0 else if (n == 1) 1
    else (2 to n).foldLeft(0,1){case((a,b), _) => (b, a+b)}._2

  val startTime2 = System.nanoTime()
  val res2 = fibBottomUpApproach(40)
  val endTime2 = System.nanoTime()
  val diff2 = endTime2 - startTime2
  println(s"res - $res2 time taken - $diff2")

  // flowerBox problem

  /**
   * 3 10 3 1 2
   * 3 -> 3
   * 10 -> 10
   * 10 + 3 = 13
   */

  def flowerBox(seq: Seq[Int]): Int = seq.foldLeft(0,0){case((a,b), v) => (b, Math.max(a+v,b))}._2

  println(flowerBox(Seq(3,10,3,1,2)))

  // (a,b) v (b, max(a+v,b))
  // (0,0) v = 3 (0, max(0+3, 0) -> 3)
  // (0, 3) v = 10 (3, max(0+ 10, 3) -> 10)
  // (3, 10) v = 3 (10, max(3 + 3, 10) -> 10)
  // (10, 10) v = 1 (10, max(10+1, 10) -> 11)
  // (10, 11) v = 2 (10, max(10 + 2, 11) -> 12)
  // (11,12)

  // Change Making Problem
  /*
  Denominations - 5,2,12,19 target = 13 ->(2,2,2,2,5) or -1
   */

  def minCoins(coins: Array[Int], amount: Int): Int = {
    // create an array to store the min number of coins for each amount
    val dp = Array.fill(amount + 1)(Int.MaxValue)
    dp(0) = 0 // No coins needed for amount 0
    // iterate thought the amount from 1 to target amount
    for (i <- 1 to amount){
      // iterate though each coin
      for (coin <- coins) {
        if(i-coin >= 0 && dp(i -coin) != Int.MaxValue) {
          dp(i) = math.min(dp(i), dp(i-coin) + 1)
        }
      }
    }

    if(dp(amount) == Int.MaxValue) -1 else dp(amount)

   }
  val coins = Array(5,2,12,19)
  val target = 13
  println(minCoins(coins,target))








}
