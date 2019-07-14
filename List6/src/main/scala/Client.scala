package client

import akka.actor._
import scala.util.Random

import server.ServerActor

class ClientActor(name: String, server: ActorRef, var upperLimit: Int) extends Actor {

	private var lowerLimit = 0
	private var actualApprox: Int = {
		actualApprox = Random.nextInt(upperLimit + 1)
		actualApprox
	}

	private def nextApprox: Int = {
		actualApprox = (lowerLimit + upperLimit) / 2
		actualApprox
	}

	def uninitialized: Receive = {
		case ClientActor.Start(msg) => {
			println(msg)
			println(s"$name trying $actualApprox")
			context.become(running)
			server ! ServerActor.Approx(actualApprox)
		}
	}

	def running: Receive = {
		case ClientActor.GuessTooSmall(msg) => {
			lowerLimit = actualApprox
			actualApprox = nextApprox
			println(s"$name Response: too small. I'm trying $actualApprox")
			server ! ServerActor.Approx(actualApprox)
		}
		case ClientActor.GuessEqual(msg) => { println(s"$name I guessed it: $actualApprox"); context.system.terminate }
		case ClientActor.GuessTooBig(msg) => {
			upperLimit = actualApprox
			actualApprox = nextApprox
			println(s"$name Response: too big. I'm trying $actualApprox")
			server ! ServerActor.Approx(actualApprox)
		}
	}

	override def receive = uninitialized
}

object ClientActor {
	def props(name: String, server: ActorRef, upperLimit: Int) =
		Props(classOf[ClientActor], name, server, upperLimit)

	// Messages
 	case class GuessTooSmall(msg: String)
 	case class GuessEqual(msg: String)
 	case class GuessTooBig(msg: String)
 	case class Wrong(msg: String)

 	case class Start(msg: String)
}