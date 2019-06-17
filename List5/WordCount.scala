import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.util.{Success, Failure}
import scala.io.Source
object WordCount {
	def main(args: Array[String]) {
		val path = args(0)
		val promiseOfFinalResult = Promise[Seq[(String, Int)]]

		val result = scanFiles(path) flatMap processFiles

		result onComplete { case x => promiseOfFinalResult tryComplete x }

		promiseOfFinalResult.future onComplete {
			case Success(result) => result.sortWith(_._2 < _._2) foreach println
			case Failure(t) => t.printStackTrace
		}

		Thread.sleep(5000)

	}

	private def processFiles(fileNames: Seq[String]): Future[Seq[(String, Int)]] =
		Future.sequence(fileNames map processFile)

	private def processFile(fileName: String): Future[(String, Int)] = Future {
		val fileText = Source.fromFile(fileName).getLines().mkString

		(fileName, fileText.split(" ").length)
	}

	private def scanFiles(docRoot: String): Future[Seq[String]] = 
		Future { new java.io.File(docRoot).list.map(docRoot + _) }
}