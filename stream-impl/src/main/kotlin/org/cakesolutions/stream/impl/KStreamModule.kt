package org.cakesolutions.stream.impl

import com.google.inject.AbstractModule
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport
import org.cakesolutions.hello.api.HelloService
import org.cakesolutions.stream.api.StreamService


class KStreamModule : AbstractModule(), ServiceGuiceSupport {
	override fun configure() {
		bindServices(serviceBinding(StreamService::class.java, KStreamServiceImpl::class.java))
		bindClient(HelloService::class.java)
	}
}