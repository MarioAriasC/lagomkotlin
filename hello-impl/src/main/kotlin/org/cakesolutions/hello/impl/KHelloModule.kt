package org.cakesolutions.hello.impl

import com.google.inject.AbstractModule
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport
import org.cakesolutions.hello.api.HelloService


class KHelloModule : AbstractModule(), ServiceGuiceSupport {
	override fun configure() {
		bindServices(serviceBinding(HelloService::class.java, KHelloServiceImpl::class.java))
	}
}