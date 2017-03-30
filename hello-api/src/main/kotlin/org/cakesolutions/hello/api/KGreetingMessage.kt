package org.cakesolutions.hello.api

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import javax.annotation.concurrent.Immutable

@Immutable
@JsonDeserialize
data class KGreetingMessage @JsonCreator constructor(val message: String)