import java.util.concurrent.Semaphore

object Increment extends App {
	var counter = 0

	val monitor = new AnyRef()

	val sem = new Semaphore(1, true)

	def readWriteCounterSemaphore(): Unit = {
		try {
			sem.acquire()

			val incrementCounter = counter + 1
			counter = incrementCounter

			sem.release()
		}

		catch { 
			case e: IllegalArgumentException => 
		}
	}

	def readWriteCounterMonitor(): Unit = monitor.synchronized {
		val incrementCounter = counter + 1
		counter = incrementCounter
	}

	val p = new Thread( () => for(_ <- 0 until 200000) readWriteCounterMonitor)
	val q = new Thread( () => for(_ <- 0 until 200000) readWriteCounterMonitor)

	val p2 = new Thread( () => for(_ <- 0 until 200000) readWriteCounterSemaphore)
	val q2 = new Thread( () => for(_ <- 0 until 200000) readWriteCounterSemaphore)

	def startThreadsAndPrintInfo(thread1: Thread, thread2: Thread): Unit = {
		val startTime = System.nanoTime

		thread1.start(); thread2.start()
		thread1.join(); thread2.join()

		val estimatedTime = (System.nanoTime - startTime) / 1000000
		println(s"The value of counter = $counter")
		println(s"Estimated time = ${estimatedTime}ms, Available processors = ${Runtime.getRuntime.availableProcessors}")
	}

	startThreadsAndPrintInfo(p, q)
	startThreadsAndPrintInfo(p2, q2)
}