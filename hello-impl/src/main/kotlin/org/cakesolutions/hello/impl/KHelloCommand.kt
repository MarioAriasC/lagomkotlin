package org.cakesolutions.hello.impl

import akka.*
import com.fasterxml.jackson.databind.annotation.*
import com.lightbend.lagom.javadsl.persistence.*
import com.lightbend.lagom.serialization.*
import java.util.*
import javax.annotation.concurrent.*


interface KHelloCommand : Jsonable {
	@Immutable
	@JsonDeserialize
	data class UserGreetingMessage(val message: String) : KHelloCommand, CompressedJsonable, PersistentEntity.ReplyType<Done>

	@Immutable
	@JsonDeserialize
	data class Hello(val name: String, val organization:Optional<String>) : KHelloCommand, PersistentEntity.ReplyType<String>
}