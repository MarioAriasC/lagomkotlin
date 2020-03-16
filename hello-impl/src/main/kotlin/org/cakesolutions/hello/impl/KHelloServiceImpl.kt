package org.cakesolutions.hello.impl

import akka.Done
import akka.NotUsed
import com.lightbend.lagom.javadsl.api.ServiceCall
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry
import kotlinx.coroutines.future.await
import org.cakesolutions.coroutine.serviceCall
import org.cakesolutions.hello.api.KGreetingMessage
import org.cakesolutions.hello.api.KHelloService
import java.util.Optional
import javax.inject.Inject


class KHelloServiceImpl @Inject constructor(private val persistentEntityRegistry: PersistentEntityRegistry) : KHelloService {

	init {
		persistentEntityRegistry.register(KHelloEntity::class.java)
	}

	override fun hello(id: String): ServiceCall<NotUsed, String> = serviceCall {
		val ref = persistentEntityRegistry.refFor(KHelloEntity::class.java, id)
		ref.ask<String, KHelloCommand.Hello>(KHelloCommand.Hello(id, Optional.empty())).await()
	}

	override fun useGreeting(id: String): ServiceCall<KGreetingMessage, Done> = serviceCall { request ->
		val ref = persistentEntityRegistry.refFor(KHelloEntity::class.java, id)
		ref.ask<Done, KHelloCommand.UserGreetingMessage>(KHelloCommand.UserGreetingMessage(request.message)).await()
	}
}