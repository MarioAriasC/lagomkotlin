package org.cakesolutions.hello.impl

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.lightbend.lagom.serialization.Jsonable
import javax.annotation.concurrent.Immutable

interface KHelloEvent : Jsonable {
	@Immutable
	@JsonDeserialize
	data class GreetingMessageChanged @JsonCreator constructor(val message: String) : KHelloEvent
}