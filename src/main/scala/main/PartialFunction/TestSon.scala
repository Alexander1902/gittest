package main.PartialFunction

class TestSon extends Test {

  def ter():Unit={
    receive{
      case "Hello" =>{
        println("你好")
      }
    }
  }
}
object TestSon{
  def main(args: Array[String]): Unit = {

  }
}
