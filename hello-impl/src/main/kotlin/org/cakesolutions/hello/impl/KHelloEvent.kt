package org.cakesolutions.hello.impl

import com.fasterxml.jackson.databind.annotation.*
import com.lightbend.lagom.serialization.*
import javax.annotation.concurrent.*

interface KHelloEvent : Jsonable {
	@Immutable
	@JsonDeserialize
	data class GreetingMessageChanged(val message: String) : KHelloEvent
}