package main

import scala.collection.GenTraversableOnce
import scala.io.{BufferedSource, Source}

object DomeTest {
  /**
    * 切分字符串
    */
  var f1:String=>GenTraversableOnce[String]={
    (str:String)=>{
      str.split(" ")
    }
  }
  /**
    * 返回数据元组
    */
  var f2:String=>Tuple2[String,Int]={
    (x:String)=>{
      Tuple2(x,1)
    }
  }
  /**
    * 返回重组的关键字
    */
  var f3:Tuple2[String,Int]=>String={
    (x:Tuple2[String,Int])=>{
      x._1
    }
  }
  var f4:List[(String, Int)] =>Int={
    (x:List[(String, Int)])=>x.size
  }
  def main(args: Array[String]): Unit = {
    /*val source: BufferedSource = Source.fromFile("C:\\word1.txt")
    val strings: Iterator[String] = source.getLines()
    val list: List[String] = strings.toList
    val str: List[String] = list.flatMap(_.split(" "))
    val str1:String ="Scala combines object-oriented and functional programming in one concise, high-level language"
    val strings2: Array[String] = str1.split(" ")

    source.foreach(print(_))*/

    /**
      * 切分字符串
      */
    var f1:String=>GenTraversableOnce[String]={
      (str:String)=>{
        str.split(" ")
      }
    }
    /**
      * 返回数据元组
      */
    var f2:String=>Tuple2[String,Int]={
      (x:String)=>{
        Tuple2(x,1)
      }
    }
    /**
      * 返回重组的关键字
      */
    var f3:Tuple2[String,Int]=>String={
      (x:Tuple2[String,Int])=>{
        x._1
      }
    }
    var f4:List[(String, Int)] =>Int={
      (x:List[(String, Int)])=>x.size
    }
    var file = Source.fromFile("C:\\word1.txt")
    val lines: Iterator[String] = file.getLines()
    val toList: List[String] = lines.toList

    val list: List[String] = toList.flatMap(f1)
    val tuples: List[(String, Int)] = list.map(f2)
    val map: Map[String, List[(String, Int)]] = tuples.groupBy(f3)
    val count: Map[String, Int] = map.mapValues(f4)
    println(count)
  }

}
