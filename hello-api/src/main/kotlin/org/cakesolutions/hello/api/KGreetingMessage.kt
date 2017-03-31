package org.cakesolutions.hello.api

import com.fasterxml.jackson.databind.annotation.*
import javax.annotation.concurrent.*

@Immutable
@JsonDeserialize
data class KGreetingMessage(val message: String)