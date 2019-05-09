class MyPair[A, B](var fst: A, var snd: B) {
  override def toString() = {s"($fst $snd)"}
}

case class MyPairCase[A, B](var fst: A, var snd: B)

object Main extends App {
  var myPair = new MyPair(2, 3)
  var myPairCase = new MyPairCase(2, 3)

  println(myPair)
  println(myPairCase)

  println({
    myPair.fst = 10
    myPair
  })
}