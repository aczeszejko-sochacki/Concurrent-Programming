sealed trait BT[+A]

case object Empty extends BT[Nothing]

case class Node[+A](elem: A, left: BT[A], right: BT[A]) extends BT[A]

object TreeSum extends App {
  def treeSum(bt: BT[Int]): Int = bt match {
    case Node(elem, left, right) => treeSum(left) + elem + treeSum(right)
    case Empty => 0
  }

  def foldBT[A, B](f: A => (B, B) => B)(acc: B)(bt: BT[A]): B = bt match {
    case Node(elem, left, right) => f(elem)(foldBT(f)(acc)(left), foldBT(f)(acc)(right))
    case Empty => acc
  }

  def sumBTFold(bt: BT[Int]): Int = {
    foldBT((elem: Int) => (left: Int, right: Int) => elem + left + right)(0)(bt)
  }

  def inorderBTFold[A](bt: BT[A]): List[A] = {
    foldBT((elem: A) => (left: List[A], right: List[A]) => left ::: List(elem) ::: right)(Nil)(bt)
  }

  def mapBT[A, B](f: A => B)(bt: BT[A]): BT[B] = {
    foldBT((elem: A) => (left: BT[B], right: BT[B]) => Node(f(elem), left, right): BT[B])(Empty)(bt)
  }

  val t = Node(1, Node(2, Empty, Node(3, Empty, Empty)), Empty)

  println(treeSum(Empty))
  println(treeSum(t))

  println(sumBTFold(Empty))
  println(sumBTFold(t))

  println(inorderBTFold(Empty))
  println(inorderBTFold(t))

  println(mapBT((v: Int) => 2*v)(Empty))
  println(mapBT((v: Int) => 2*v)(t))
  println(mapBT((x: Int) => (x.asInstanceOf[Double]))(t))
}