package SortAlgo

object HeapSort extends App{
  val data = Array(1,0,2,9,3,8,4,7,5,6)
  val hs = new mHeapSort
  hs.buildMaxHeap(data, data.length)
 data.foreach(println)
  hs.heapSort(data)
  println("**************")
  data.foreach(println)
}

class mHeapSort{
  def left(i: Int): Int = (2 *i) + 1
  def right(i: Int): Int = left(i) + 1
  def parent(i: Int): Int = (i-1)/2

  def swap(a:Array[Int], i: Int, j: Int): Unit = {
    val t = a(i)
    a(i) = a(j)
    a(j) = t
  }

  def maxHeap(a: Array[Int], i: Int, size: Int): Unit = {
    val l = left(i)
    val r = right(i)
    var m = -1
    m = if(l < size && a(l) > a(i)) l else i
    m = if(r < size && a(r) > a(m)) r else m
    if(m != i) {
      swap(a,i,m)
      maxHeap(a,m, size)
    }
  }

  def buildMaxHeap(a: Array[Int], size: Int): Unit ={
    val hs = size / 2
    for (i <- 0 to hs) {
      maxHeap(a, hs - i, size)
    }
  }

  def heapSort(a: Array[Int]): Unit = {
    buildMaxHeap(a,a.length)
    for (i <- (0 until a.length).reverse) {
      swap(a,0,i)
      maxHeap(a,0,i)
    }
  }
}
