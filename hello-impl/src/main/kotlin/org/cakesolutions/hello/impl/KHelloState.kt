package org.cakesolutions.hello.impl

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.lightbend.lagom.serialization.CompressedJsonable
import javax.annotation.concurrent.Immutable

@Immutable
@JsonDeserialize
data class KHelloState @JsonCreator constructor(val message: String, val timestamp: String) : CompressedJsonable