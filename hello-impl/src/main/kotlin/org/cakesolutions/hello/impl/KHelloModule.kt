package org.cakesolutions.hello.impl

import com.google.inject.AbstractModule
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport
import org.cakesolutions.hello.api.KHelloService

class KHelloModule : AbstractModule(), ServiceGuiceSupport {
	override fun configure() {
		bindServices(serviceBinding(KHelloService::class.java, KHelloServiceImpl::class.java))
	}
}