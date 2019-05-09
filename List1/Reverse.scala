object Reverse extends App {
  def reverse[A](xs: List[A]): List[A] = {
    def reverseIter[A](xs: List[A], acc: List[A]): List[A] = xs match {
      case head::tail => reverseIter(tail, head :: acc)
      case Nil => acc
    }

    reverseIter(xs, Nil)
  }

  println(reverse(List(1, 2, 3)))
  println(reverse(Nil))
  println(reverse(List("a", "b", "c", "d")))
}