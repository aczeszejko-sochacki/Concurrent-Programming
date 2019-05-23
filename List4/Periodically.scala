import scala.annotation.tailrec

object Periodically extends App {
	def periodically(duration: Long, times: Int)(body: =>Unit): Unit = {

		@tailrec
		def iter(times: Int): Unit = if (times > 0) { body; Thread.sleep(duration); iter(times-1) }

		val t = new Thread( () => iter(times) )
		t.setDaemon(true)
		t.start()
	}

	periodically(1000, 5)(println("x"))
	periodically(1000, 25)(println("y"))
	Thread.sleep(10000)
	println("Done sleeping")
}