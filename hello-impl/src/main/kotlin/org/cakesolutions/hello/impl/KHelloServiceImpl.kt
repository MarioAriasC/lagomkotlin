package org.cakesolutions.hello.impl

import akka.Done
import akka.NotUsed
import com.lightbend.lagom.javadsl.api.ServiceCall
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry
import org.cakesolutions.hello.api.HelloService
import org.cakesolutions.hello.api.KGreetingMessage
import java.util.*
import javax.inject.Inject


class KHelloServiceImpl @Inject constructor(private val persistentEntityRegistry: PersistentEntityRegistry) : HelloService {

	init {
		persistentEntityRegistry.register(KHelloEntity::class.java)
	}

	override fun hello(id: String): ServiceCall<NotUsed, String> {
		return ServiceCall {
			val ref = persistentEntityRegistry.refFor(KHelloEntity::class.java, id)
			ref.ask(KHelloCommand.Hello(id, Optional.empty()))
		}
	}

	override fun useGreeting(id: String): ServiceCall<KGreetingMessage, Done> {
		return ServiceCall { request ->
			val ref = persistentEntityRegistry.refFor(KHelloEntity::class.java, id)
			ref.ask(KHelloCommand.UserGreetingMessage(request.message))
		}
	}
}