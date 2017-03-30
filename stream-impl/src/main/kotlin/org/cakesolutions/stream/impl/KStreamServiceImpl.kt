package org.cakesolutions.stream.impl

import akka.NotUsed
import akka.stream.javadsl.Source
import com.lightbend.lagom.javadsl.api.ServiceCall
import org.cakesolutions.hello.api.HelloService
import org.cakesolutions.stream.api.StreamService
import java.util.concurrent.CompletableFuture.completedFuture
import javax.inject.Inject


class KStreamServiceImpl @Inject constructor(val helloService: HelloService) : StreamService {
	override fun stream(): ServiceCall<Source<String, NotUsed>, Source<String, NotUsed>> {
		return ServiceCall { hellos ->
			completedFuture(hellos.mapAsync(8){ name ->
				helloService.hello(name)()
			})
		}
	}
}