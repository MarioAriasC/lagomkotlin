/*
 * Copyright (C) 2016-2017 Lightbend Inc. <https://www.lightbend.com>
 */
package org.cakesolutions.it;

import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import com.lightbend.lagom.javadsl.client.integration.LagomClientFactory;
import org.cakesolutions.hello.api.KGreetingMessage;
import org.cakesolutions.hello.api.KHelloService;
import org.cakesolutions.stream.api.KStreamService;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class StreamIT {

    private static final String SERVICE_LOCATOR_URI = "http://localhost:8000";

    private static LagomClientFactory clientFactory;
    private static KHelloService helloService;
    private static KStreamService streamService;
    private static ActorSystem system;
    private static Materializer mat;

    @BeforeClass
    public static void setup() {
        clientFactory = LagomClientFactory.create("integration-test", StreamIT.class.getClassLoader());
        // One of the clients can use the service locator, the other can use the service gateway, to test them both.
        helloService = clientFactory.createDevClient(KHelloService.class, URI.create(SERVICE_LOCATOR_URI));
        streamService = clientFactory.createDevClient(KStreamService.class, URI.create(SERVICE_LOCATOR_URI));

        system = ActorSystem.create();
        mat = ActorMaterializer.create(system);
    }

    @Test
    public void helloWorld() throws Exception {
        String answer = await(helloService.hello("foo").invoke());
        assertEquals("Hello, foo", answer);
        await(helloService.useGreeting("bar").invoke(new KGreetingMessage("Hi")));
        String answer2 = await(helloService.hello("bar").invoke());
        assertEquals("Hi, bar", answer2);
    }

    @Test
    public void helloStream() throws Exception {
        // Important to concat our source with a maybe, this ensures the connection doesn't get closed once we've
        // finished feeding our elements in, and then also to take 3 from the response stream, this ensures our
        // connection does get closed once we've received the 3 elements.
        Source<String, ?> response = await(streamService.stream().invoke(
                Source.from(Arrays.asList("a", "b", "c"))
                        .concat(Source.maybe())));
        List<String> messages = await(response.take(3).runWith(Sink.seq(), mat));
        assertEquals(Arrays.asList("Hello, a", "Hello, b", "Hello, c"), messages);
    }

    private <T> T await(CompletionStage<T> future) throws Exception {
        return future.toCompletableFuture().get(10, TimeUnit.SECONDS);
    }

    @AfterClass
    public static void tearDown() {
        if (clientFactory != null) {
            clientFactory.close();
        }
        if (system != null) {
            system.terminate();
        }
    }




}
