package org.cakesolutions.stream.api

import akka.NotUsed
import akka.stream.javadsl.Source
import com.lightbend.lagom.javadsl.api.Descriptor
import com.lightbend.lagom.javadsl.api.Service
import com.lightbend.lagom.javadsl.api.Service.named
import com.lightbend.lagom.javadsl.api.Service.namedCall
import com.lightbend.lagom.javadsl.api.ServiceCall
import kotlin.reflect.jvm.javaMethod

/**
 * The stream interface.
 *
 * This describes everything that Lagom needs to know about how to serve and
 * consume the HelloStream service.
 */
interface KStreamService : Service {

    fun stream(): ServiceCall<Source<String, NotUsed>, Source<String, NotUsed>>

    @JvmDefault
    override fun descriptor(): Descriptor {
        return named("stream").withCalls(
            namedCall<Source<String, NotUsed>, Source<String, NotUsed>>("stream", KStreamService::stream.javaMethod)
        ).withAutoAcl(true)
    }
}