import scala.collection.immutable.Queue

sealed trait LBT[+A]

case object LEmpty extends LBT[Nothing]

case class LNode[+A](elem: A, left: () => LBT[A], right: () => LBT[A]) extends LBT[A]

object LazyTree extends App {
	def lBreadth[A](ltree: LBT[A]): Stream[A] = {

		def rec(queue: Queue[LBT[A]]): Stream[A] = if (queue.isEmpty) Stream.Empty else {
			val (node, tail) = queue.dequeue
			node match {
				case LNode(elem, left, right) => elem #:: rec(tail ++ List(left(), right()))
				case LEmpty => rec(tail)
			}
		}

		rec(Queue(ltree))
	}

	def lTree(n: Int): LBT[Int] = LNode(n, () => lTree(2*n), () => lTree(2*n + 1))

	val tree = LNode(1, () => LNode(2, () => LNode(4, () => LEmpty,
																										() => LEmpty),
														 				 () => LEmpty),
										  () => LNode(3, () => LNode(5, () => LEmpty,
										  															() => LNode(6, () => LEmpty,
										  																						 () => LEmpty)),
										 				 			 	 () => LEmpty))
	val emptyTree = LEmpty

	println(lBreadth(tree))
	println(lBreadth(tree).force)
	println(lBreadth(emptyTree))

	println(lBreadth(lTree(1)).take(7).force)
  println(lBreadth(lTree(2)).take(5).force)
}
