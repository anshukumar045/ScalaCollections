package com.kanshu.datastructures

import scala.annotation.tailrec

object BestTimeToBuyAndSellStock extends App{
  /**
   * Given an array prices[] of length N, representing the prices of the stocks on different days,
   * the task is to find the maximum profit possible for buying and selling the stocks
   * on different days using transactions where at most one transaction is allowed.
   * input prices = [7, 1, 5, 3, 6, 4]
   * output = 5
   * - on day 2 the price is lowest - [_, 1 , _ , _ , _,_]
   * - on day 5 the price is highest - [_, 1 , _ , _ , 6,_]
   * - profit = 6-1 = 5
   */
// for one buy and sell calculate max profit
  private val prices = Seq(7,1,5,2,6,4)

@tailrec
  private def maxProfit(prices: Seq[Int], buy: Int, mxProfit: Int = 0): Int ={
    prices match {
      case Nil => mxProfit
      case price :: priceLst =>
        (buy, mxProfit) match {
          case (b,mx) if b > price => maxProfit(priceLst,price,mx)
          case (b,mx) if price-b > mx => maxProfit(priceLst, b, price-b)
          case _ => maxProfit(priceLst, buy, mxProfit)
        }
    }
  }

//  println(maxProfit(prices,prices.head))

  // for one buy and sell calculate max profit with positions(buy,sell)
  private val priceZip = prices.zipWithIndex

  @tailrec
  private def maxProfitPos(prices: Seq[(Int, Int)],
                           buy: Int,
                           mxProfit: Int = 0,
                           position: (Int, Int)= (0,0)): (Int, (Int,Int)) ={
    prices match {
      case Nil => (mxProfit, position)
      case price :: priceLst =>
        println(s"$prices -- $buy -- $mxProfit -- $position")
        (buy, mxProfit, position) match {
          case (b,mx, pos) if b > price._1 && price._2 > pos._1 =>
            pos match {
              case p if p._2 == 0 => maxProfitPos(priceLst, price._1, mx, (price._1,p._2))
              case p if price._2 > p._2 =>maxProfitPos(priceLst, b,mx,p)
              case _ => maxProfitPos(priceLst, b,mx,pos)
            }
          case (b,mx,pos) if price._1 -b > mx =>
            pos match {
              case p if price._2 < p._1 => maxProfitPos(priceLst,b,mx,p)
              case _ => maxProfitPos(priceLst, b , price._1-b, (pos._1, price._2))
            }
          case _ => maxProfitPos(priceLst, buy, mxProfit, position)
        }
    }
  }

//  println(maxProfitPos(priceZip, priceZip.head._1))

  // find the max possible profit with max possible trade
  // find max possible trades - buy first , sell later
  // find the max possible profit


  private val priceList = Seq(7,1,5,2,6,4,3,9)
  case class Trade(buy: Option[Int]= None, sell : Option[Int] = None, profit: Int = 0)

  val tradeList = priceList.foldLeft(Seq[Trade]())((trades,price) =>{
    trades match {
      case Nil => Trade(Some(price)) +: trades
      case trade :: rest =>
        val buy = trade.buy
        val sell = trade.sell
        (buy,sell) match {
          case(Some(b), Some(s)) =>
            if (price > b & price > s) Trade(Some(b), Some(price), price-b) +: rest
            else Trade(Some(price)) +: trades

          case (Some(b) , None) =>
            if(price > b) Trade(Some(b), Some(price), price-b) +: trades
            else Trade(Some(price)) +: rest // 7, 1 => 1

          case _ => Trade(Some(price)) +: trades
        }
    }
  })

  tradeList.foreach(println)

  val maximumProfit = tradeList.map(_.profit).reduce(_ +_)

  println(s"max profit is $maximumProfit")
  val maxNoOfTrades = tradeList.filterNot(_.profit == 0).length

  println(s"the max no of trades - $maxNoOfTrades")

    val tradesList = tradeList.filterNot(_.profit == 0).reverse
  tradesList.foreach(println)

  // find the max possible profit for k trades
  // 1 < k < n
  // n = max possible trade

private def groupTrades(trdList: Seq[Trade], k: Int): Seq[Seq[Trade]] = {
  val (first, rest) = trdList.splitAt(trdList.length - (k-1))
  Seq(first) ++ rest.grouped(1)
}

  val testGrp = groupTrades(tradesList, 2)
  testGrp.foreach(println)

  private def reduceTrade(trades: Seq[Trade], ac: Trade = Trade(), tmp: Trade = Trade()): (Trade, Trade) = {
    trades match {
      case Nil => (ac, tmp)
      case t :: rest =>
        val Some(buy) = t.buy
        val Some(sell) = t.sell
        val profit = t.profit
        (ac, tmp, t) match {
          case (Trade(None, None, 0), Trade(None, None, 0), t) => reduceTrade(rest, t, Trade())

          case (Trade(Some(b), Some(s), p), Trade(None, None, 0), t) =>
            if (b < buy & s > sell) reduceTrade(rest, ac, t)
            else if (b < buy & s <= sell) reduceTrade(rest, Trade(Some(b), Some(sell), sell - b))
            else if (b >= buy & s > sell & p > profit) reduceTrade(rest, ac, t)
            else reduceTrade(rest, t)

          case (Trade(Some(b), Some(s), p), Trade(Some(tb), Some(ts), tp), t) =>
            if (b < buy & s > sell) {
              if (tb <= buy & ts >= sell) reduceTrade(rest, ac, tmp)
              else if (tb <= buy & ts < sell) reduceTrade(rest, ac, Trade(Some(tb), Some(sell), sell - tb))
              else if (tb > buy & ts > sell & tp > profit) reduceTrade(rest, ac, tmp)
              else reduceTrade(rest, ac, t)
            }
            else if (b < buy & s <= sell) reduceTrade(rest, Trade(Some(b), Some(sell), sell - b))
            else if (b >= buy & s > sell & p > profit) {
              if (tb < buy & ts > sell) reduceTrade(rest, ac, tmp)
              else if (tb < buy & ts <= sell) reduceTrade(rest, ac, Trade(Some(tb), Some(sell), sell - tb))
              else if (tb > buy & ts > sell & tp > profit) reduceTrade(rest, ac, tmp)
              else reduceTrade(rest, ac, t)
            }
            else reduceTrade(rest, t)
        }
    }

    }

  private def getMaxProfitWithKTrades(trades: Seq[Trade], k : Int): Seq[Trade] = {
    if (k >= trades.length) trades
    else {
      val grpsTrade = groupTrades(trades, k)
      grpsTrade.foldLeft(Seq[Trade](), Trade())((tradesTuple, trd) => {
        val (trades, tmpTrd) = tradesTuple
        val (res, tmp) = if (tmpTrd == Trade()) reduceTrade(trd)
        else reduceTrade(tmpTrd +: trd)
        (trades :+ res, tmp)
      })._1
    }
  }

  val maxProfitKTrades = getMaxProfitWithKTrades(tradesList, 2)
  println("******")
  maxProfitKTrades.foreach(println)

}
