package org.cakesolutions.hello.impl

import com.fasterxml.jackson.databind.annotation.*
import com.lightbend.lagom.serialization.*
import javax.annotation.concurrent.*

@Immutable
@JsonDeserialize
data class KHelloState(val message: String, val timestamp: String) : CompressedJsonable