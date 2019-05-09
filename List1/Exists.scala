import scala.annotation.tailrec

object Exists extends App {
  @tailrec
  def existsMatch[A](xs: List[A])(p: A => Boolean): Boolean = xs match {
    case head::tail => p(head) || existsMatch(tail)(p)
    case Nil => false
  }

  def existsMatchFoldL[A](xs: List[A])(p: A => Boolean): Boolean = 
    xs.foldLeft(false)((acc, head) => acc || p(head))

  def existsMatchFoldR[A](xs: List[A])(p: A => Boolean): Boolean = 
    xs.foldRight(false)((head, acc) => acc || p(head))

  println(existsMatch(List(1, 2, 3))(_ % 2 == 0))
  println(existsMatch(List(1, 2, 3))(_ % 2 == 4))

  println(existsMatchFoldL(List(1, 2, 3))(_ % 2 == 0))
  println(existsMatchFoldL(List(1, 2, 3))(_ % 2 == 4))

  println(existsMatchFoldR(List(1, 2, 3))(_ % 2 == 0))
  println(existsMatchFoldR(List(1, 2, 3))(_ % 2 == 4))
}