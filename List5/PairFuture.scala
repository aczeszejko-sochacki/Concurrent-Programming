import scala.concurrent._
import ExecutionContext.Implicits.global

object PairFut extends App {
	def pairFut[A, B](fut1: Future[A], fut2: Future[B]): Future[(A, B)] = fut1 zip fut2

	def pairFutFor[A, B](fut1: Future[A], fut2: Future[B]): Future[(A, B)] = 
		for {
			fut1Result <- fut1
			fut2Result <- fut2
		} yield (fut1Result, fut2Result)

	val p1Result = pairFut(Future { 2 + 2 }, Future { "a" })
	val p2Result = pairFutFor(Future { 2 + 2 }, Future { "a" })

	p1Result foreach { case x => println(x) }
	p2Result foreach { case x => println(x) }

	Thread.sleep(1000)
}