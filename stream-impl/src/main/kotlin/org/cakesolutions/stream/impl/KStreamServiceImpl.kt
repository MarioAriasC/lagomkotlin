package org.cakesolutions.stream.impl

import akka.NotUsed
import akka.stream.javadsl.Source
import com.lightbend.lagom.javadsl.api.ServiceCall
import org.cakesolutions.coroutine.serviceCall
import org.cakesolutions.hello.api.KHelloService
import org.cakesolutions.stream.api.KStreamService
import javax.inject.Inject


class KStreamServiceImpl @Inject
constructor(private val helloService: KHelloService) : KStreamService {
	override fun stream(): ServiceCall<Source<String, NotUsed>, Source<String, NotUsed>> = serviceCall { hellos ->
		hellos.mapAsync(8) { name ->
			helloService.hello(name)()
		}
	}
}