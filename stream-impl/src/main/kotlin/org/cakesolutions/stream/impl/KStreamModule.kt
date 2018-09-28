package org.cakesolutions.stream.impl

import com.google.inject.AbstractModule
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport
import org.cakesolutions.hello.api.KHelloService
import org.cakesolutions.stream.api.KStreamService

class KStreamModule : AbstractModule(), ServiceGuiceSupport {
	override fun configure() {
		bindServices(serviceBinding(KStreamService::class.java, KStreamServiceImpl::class.java))
		bindClient(KHelloService::class.java)
	}
}