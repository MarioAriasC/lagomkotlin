package org.cakesolutions.hello.impl

import akka.Done
import com.lightbend.lagom.javadsl.persistence.PersistentEntity
import java.time.LocalDateTime
import java.util.*


/**
 * Created by IntelliJ IDEA.
 * @author Mario Arias
 * Date: 6/03/17
 * Time: 5:41 PM
 */
class KHelloEntity : PersistentEntity<KHelloCommand, KHelloEvent, KHelloState>() {
	override fun initialBehavior(snapshotState: Optional<KHelloState>): Behavior {
		val b = newBehaviorBuilder(snapshotState.orElse(KHelloState("Hello", LocalDateTime.now().toString())))

		b.setCommandHandler(KHelloCommand.UserGreetingMessage::class.java) { (message), ctx ->
			ctx.thenPersist(KHelloEvent.GreetingMessageChanged(message)) { _ ->
				ctx.reply(Done.getInstance())
			}
		}

		b.setEventHandler(KHelloEvent.GreetingMessageChanged::class.java) { (message) ->
			KHelloState(message, LocalDateTime.now().toString())
		}

		b.setReadOnlyCommandHandler(KHelloCommand.Hello::class.java){ (name), ctx ->
			ctx.reply("${state().message}, $name")
		}
		
		return b.build()
	}
}