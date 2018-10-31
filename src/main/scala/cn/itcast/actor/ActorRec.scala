package cn.itcast.actor
import scala.actors.Actor

class ActorR1(num:Int) extends Actor{
  override def act(): Unit = {
    loop(
      receive{
        case "Hello" =>{
          println("你好"+num)
        }
      }
    )
  }
}
class ActorR2(num :Int) extends Actor{
  override def act(): Unit = {
    loop(
      react{
        case "Hello2"=>{
          println("你好2"+num)
        }
      }
    )
  }
}

object Act{
  def main(args: Array[String]): Unit = {
    val b=new ActorR2(8).start()
    val a=new ActorR1(8).start()

    val start2 = System.currentTimeMillis()
    for(i<-1 to 2){
      val b=new ActorR2(i).start()
      b!"Hello2"
    }
    println("react:"+(System.currentTimeMillis()-start2))

    val start = System.currentTimeMillis()
    for(i<-1 to 2){
      val a=new ActorR1(i).start()
      a ! "Hello"
    }
    println("receive:"+(System.currentTimeMillis()-start))

  }
}
