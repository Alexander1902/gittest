package cn.itcast.actor

import scala.actors.{Actor, Future}
import scala.collection.{GenTraversableOnce, mutable}
import scala.collection.mutable.ListBuffer
import scala.io.{Codec, Source}
/**
 * @Author: zhangshancheng
 * @Description:
 */
class WordCountActor() extends Actor{

  /**
   * @Description:
   */
  override def act(): Unit = {
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
    loop(
      react{
        case MySubmitTask(path)=>{
          //implicit charset = new Codec()
          var file = Source.fromFile(path)
          val lines: Iterator[String] = file.getLines()
          //lines.foreach(println(_))
          val toList: List[String] = lines.toList
          val list: List[String] = toList.flatMap(f1)
          val tuples: List[(String, Int)] = list.map(f2)
          val map: Map[String, List[(String, Int)]] = tuples.groupBy(f3)
          val count: Map[String, Int] = map.mapValues(f4)
          sender!MyResultTask(count)
          /*var lins = file.getLines()
          var words = lins.flatMap(f1)
          val tuples: Iterator[(String, Int)] = words.map(f2)
          var it = tuples
          var list = it.toList
          var group = list.groupBy(f3)
          var count = group.mapValues(f4)
          sender!MyResultTask(count)*/
        }
        case MyStopTask => {
          exit()
        }
      }

    )

  }


}

case class MySubmitTask(path:String)
case class MyResultTask(reslut : Map[String, Int])
case object MyStopTask

object Main{
  def main(args: Array[String]): Unit = {

    var allReply = new mutable.HashSet[Future[Any]]()
    var resList = new ListBuffer[MyResultTask]

    val paths = Array[String]("C:\\word1.txt","C:\\word2.txt")
    for(f<-paths){
      val act = new WordCountActor()
      val reply = act.start()!! MySubmitTask(f)
      allReply+=reply
      act!!MyStopTask
    }
    val f1:Future[Any]=>Boolean={
      (x:Future[Any])=>{
        x.isSet
      }
    }
    while(allReply.size>0){
      var isSet = allReply.filter(f1)
      for(it<-isSet){
        val task: MyResultTask = it.apply().asInstanceOf[MyResultTask]
        resList+=task
        allReply-=it
      }
    }

    //println(resList)
    val tuples: ListBuffer[(String, Int)] = resList.flatMap(_.reslut)
    val stringToTuples: Map[String, ListBuffer[(String, Int)]] = tuples.groupBy(_._1)
    val stringToInt: Map[String, Int] = stringToTuples.mapValues(_.foldLeft(0)(_+_._2))
    println(stringToInt)

  }
}



