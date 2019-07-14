package server

import akka.actor._
import scala.util.Random

import client.ClientActor

class ServerActor(secretLimit: Int) extends Actor {

	private val secretNumber = Random.nextInt(secretLimit + 1)

	override def receive = {
		case ServerActor.Approx(approx: Int) => approx compare secretNumber match { 
			case -1 => { Thread.sleep(1000); sender ! ClientActor.GuessTooSmall("Too small") }
			case 0 => { Thread.sleep(1000); sender ! ClientActor.GuessEqual("Equal") }
			case 1 => { Thread.sleep(1000); sender ! ClientActor.GuessTooBig("Too big") }
		}
		case _ => sender ! ClientActor.Wrong("Need to put a number")
	}
 }

object ServerActor {
	def props(secretLimit: Int) = Props(classOf[ServerActor], secretLimit)
	
	// Messages
	case class Approx(approx: Int)
}