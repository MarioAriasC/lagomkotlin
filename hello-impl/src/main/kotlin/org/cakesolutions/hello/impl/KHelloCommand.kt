package org.cakesolutions.hello.impl

import akka.Done
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.lightbend.lagom.javadsl.persistence.PersistentEntity
import com.lightbend.lagom.serialization.CompressedJsonable
import com.lightbend.lagom.serialization.Jsonable
import java.util.*
import javax.annotation.concurrent.Immutable


interface KHelloCommand : Jsonable {
	@Immutable
	@JsonDeserialize
	data class UserGreetingMessage @JsonCreator constructor(val message: String) : KHelloCommand, CompressedJsonable, PersistentEntity.ReplyType<Done>

	@Immutable
	@JsonDeserialize
	data class Hello @JsonCreator constructor(val name: String, val organization:Optional<String>) : KHelloCommand, PersistentEntity.ReplyType<String>
}