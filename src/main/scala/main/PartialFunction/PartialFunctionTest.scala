package main.PartialFunction

trait PartialFunctionTest {
  def receive(f : scala.PartialFunction[scala.Any, Unit]) : Unit = { /* compiled code */ }
  //def mytry[B](f : scala.PartialFunction[scala.Any, B]):B = { }
}
