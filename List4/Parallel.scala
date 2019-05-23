object Parallel extends App {
	def parallel[A, B](a: =>A, b: =>B): (A, B) = {

		var aResult: Option[A] = None
		var bResult: Option[B] = None

		// Printing for checking if they are really parallel; Using SAM
		val aThread = new Thread( () => aResult = Some(a) )
		val bThread = new Thread( () => bResult = Some(b) )

		aThread.start(); bThread.start()
		aThread.join(); bThread.join()

		(aResult.get, bResult.get)
	}

	println(parallel({println(1); println(2); 1}, {println(3); println(4); 2}))
	println(parallel(Thread.currentThread.getName, Thread.currentThread.getName))
}