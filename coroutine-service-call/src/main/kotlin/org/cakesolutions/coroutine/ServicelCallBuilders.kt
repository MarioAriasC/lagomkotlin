package org.cakesolutions.coroutine

import com.lightbend.lagom.javadsl.api.ServiceCall
import com.lightbend.lagom.javadsl.api.transport.RequestHeader
import com.lightbend.lagom.javadsl.api.transport.ResponseHeader
import com.lightbend.lagom.javadsl.server.HeaderServiceCall
import com.lightbend.lagom.javadsl.server.ServerServiceCall
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.future.future

/**
 * @author Alex Mihailov {@literal <avmikhaylov@tectus-it.ru>}.
 */

/**
 * Starts new coroutine and returns its result as an implementation of [ServiceCall].
 *
 * @param context additional to [CoroutineScope.coroutineContext] context of the coroutine.
 * @param start coroutine start option. The default value is [CoroutineStart.DEFAULT].
 * @param block the coroutine code.
 */
fun <Request, Response> serviceCall(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.(request: Request) -> Response
) = ServiceCall<Request, Response> {
    CoroutineScope(Dispatchers.Unconfined).future(context, start) {
        block(it)
    }
}

/**
 * Starts new coroutine and returns its result as an implementation of [ServerServiceCall].
 *
 * @param context additional to [CoroutineScope.coroutineContext] context of the coroutine.
 * @param start coroutine start option. The default value is [CoroutineStart.DEFAULT].
 * @param block the coroutine code.
 */
fun <Request, Response> serverServiceCall(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.(request: Request) -> Response
) = ServerServiceCall<Request, Response> {
    CoroutineScope(Dispatchers.Unconfined).future(context, start) {
        block(it)
    }
}

/**
 * Starts new coroutine and returns its result as an implementation of [HeaderServiceCall].
 *
 * @param context additional to [CoroutineScope.coroutineContext] context of the coroutine.
 * @param start coroutine start option. The default value is [CoroutineStart.DEFAULT].
 * @param block the coroutine code.
 */
fun <Request, Response> headerServiceCall(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.(requestHeader: RequestHeader, request: Request) -> akka.japi.Pair<ResponseHeader, Response>
) = HeaderServiceCall<Request, Response> { requestHeader, request ->
    CoroutineScope(Dispatchers.Unconfined).future(context, start) {
        block(requestHeader, request)
    }
}
