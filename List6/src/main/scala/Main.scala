import akka.actor._

import client.ClientActor
import server.ServerActor

object ClientServer extends App {
	val ourSystem = ActorSystem("ClientServer")

	val secretLimit = 100
	val server: ActorRef = ourSystem.actorOf(ServerActor.props(secretLimit))

	for (name <- Seq("Client1", "Client2")) {
		val client = ourSystem.actorOf(ClientActor.props(name, server, secretLimit))
		client ! ClientActor.Start(s"$name starting")
	}

	Thread.sleep(10000)
	ourSystem.terminate
}