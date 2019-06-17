import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.util.{ Success, Failure }

object Exists extends App {

	implicit class FutureOps[T](val self: Future[T]) {
		def exists(p: T => Boolean): Future[Boolean] = {
			val pr = Promise[Boolean]

			self onComplete { case x => x match {
				case Success(value) => pr success p(value)
				case Failure(e) => pr success false
			}}

			pr.future
		}

		def existsMap(p: T => Boolean): Future[Boolean] = 
			self map(_ match {
				case Success(_) => true 
				case Failure(_) => false 
			})
	}

	val f1 = Future { 2 + 2 }
	val f2 = Future { 2 + 2 }
	val f3 = Future { throw new IllegalArgumentException }

	val resultTrue = f1 exists ((x: Int) => x > 0)
	val resultFalse = f2 exists ((x: Int) => x < 0)
	val resultException = f3 exists ((x: Int) => x > 0)

	resultTrue foreach { case x => println(x) }
	resultFalse foreach { case x => println(x) }
	resultException foreach { case x => println(x) }

	Thread.sleep(1000)

	val resultTrueMap = f1 existsMap ((x: Int) => x > 0)
	val resultFalseMap = f2 existsMap ((x: Int) => x < 0)
	val resultExceptionMap = f3 existsMap ((x: Int) => x > 0)

	resultTrue foreach { case x => println(x) }
	resultFalse foreach { case x => println(x) }
	resultException foreach { case x => println(x) }

	Thread.sleep(1000)
}