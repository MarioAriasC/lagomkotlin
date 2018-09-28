package org.cakesolutions.hello.api

import akka.Done
import akka.NotUsed
import com.lightbend.lagom.javadsl.api.Descriptor
import com.lightbend.lagom.javadsl.api.Service
import com.lightbend.lagom.javadsl.api.Service.named
import com.lightbend.lagom.javadsl.api.Service.pathCall
import com.lightbend.lagom.javadsl.api.ServiceCall
import kotlin.reflect.jvm.javaMethod

/**
 * The Hello service interface.
 *
 * This describes everything that Lagom needs to know about how to serve and
 * consume the Hello.
 */
interface KHelloService : Service {

    /**
     * Example: `curl http://localhost:9000/api/hello/Alice`
     */
    fun hello(id: String): ServiceCall<NotUsed, String>

    /**
     * Example:
     * ```
     * curl -H "Content-Type: application/json" -X POST -d '{"message": "Hi"}' http://localhost:9000/api/hello/Alice
     * ```
     */
    fun useGreeting(id: String): ServiceCall<KGreetingMessage, Done>

    @JvmDefault
    override fun descriptor(): Descriptor {
        return named("hello").withCalls(
            pathCall<NotUsed, String>("/api/hello/:id", KHelloService::hello.javaMethod),
            pathCall<KGreetingMessage, Done>("/api/hello/:id", KHelloService::useGreeting.javaMethod)
        ).withAutoAcl(true)
    }
}