// Custom queue
class MyQueue[T] private (private val xs: List[T], private val ys: List[T]) {
	def enqueue[S >: T](x: S): MyQueue[S] = 
		if (xs == Nil) new MyQueue(List(x), ys)
		else new MyQueue(xs, x :: ys)

	def dequeue(): MyQueue[T] = xs match {
			case Nil => this
			case List(_) => new MyQueue(ys.reverse, Nil)
			case _::tail => new MyQueue(tail, ys)
	}
	def first(): T = if (isEmpty()) throw new Exception("First of empty queue") else xs.head

	def isEmpty(): Boolean = xs.isEmpty

	def firstOption(): Option[T] = if (isEmpty()) None else Some(first())

	def this() { this(Nil, Nil) }
}

object MyQueue {
	def apply[T](xs: T*) = new MyQueue[T](xs.toList, Nil)

	def empty() = new MyQueue(Nil, Nil)
}

// BFS on non-lazy tree using the queue
sealed trait BT[+A]

case object Empty extends BT[Nothing]

case class Node[+A](elem: A, left: BT[A], right: BT[A]) extends BT[A]

object BFS {
	def breadthBT[A](tree: BT[A]): List[A] = {
		def aux(queue: MyQueue[BT[A]]): List[A] = {

			queue.firstOption() match {
				case Some(Node(elem, left, right)) => elem :: aux(queue.dequeue.enqueue(left).enqueue(right))
				case Some(Empty) => aux(queue.dequeue)
				case None => Nil
			}
		}

		aux(MyQueue(tree))
	}
}

object Lista3 extends App { 

	// Creating queues
	MyQueue.empty()
	MyQueue()
	new MyQueue
	var queue = MyQueue(1, 2, 3)

	// Operations on queue
	println(queue.first())
	println(queue.firstOption())
	println(queue.isEmpty())

	queue = queue.dequeue()
	println(queue.first())
	
	queue = queue.dequeue().dequeue()
	println(queue.isEmpty())
	println(queue.firstOption())

	queue = queue.enqueue(1)
	println(queue.first())

	// BFS using MyQueue
	val tree = Node(1, Node(2, Node(4, Empty,
																		 Empty),
														 Empty),
										 Node(3, Node(5, Empty,
										  							 Node(6, Empty,
										  											 Empty)),
										 				 Empty))

	println(BFS.breadthBT(tree))
}