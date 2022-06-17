package com.winson.winsonforresilience;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.core.registry.EntryAddedEvent;
import io.github.resilience4j.core.registry.EntryRemovedEvent;
import io.github.resilience4j.core.registry.EntryReplacedEvent;
import io.github.resilience4j.core.registry.RegistryEventConsumer;
import io.github.resilience4j.retry.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.net.URI;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.permanentRedirect;

/**
 * @author winson
 * @date 2022/6/16
 **/
@Configuration
public class RConfig {

    private static Logger LOG = LoggerFactory.getLogger(RConfig.class);

    @Bean
    RouterFunction<ServerResponse> redirectRoot() {
        return route(GET("/"),
                req -> permanentRedirect(URI.create("/actuator")).build());
    }

    @Bean
    public RegistryEventConsumer<CircuitBreaker> myRegistryEventConsumer() {

        return new RegistryEventConsumer<CircuitBreaker>() {
            @Override
            public void onEntryAddedEvent(EntryAddedEvent<CircuitBreaker> entryAddedEvent) {
                entryAddedEvent.getAddedEntry().getEventPublisher().onEvent(event -> {
                    System.out.println("normal RegistryEventConsumer ------------> onEvent");
                    LOG.info(event.toString());
                });
            }

            @Override
            public void onEntryRemovedEvent(EntryRemovedEvent<CircuitBreaker> entryRemoveEvent) {

            }

            @Override
            public void onEntryReplacedEvent(EntryReplacedEvent<CircuitBreaker> entryReplacedEvent) {

            }
        };
    }

//    @Bean
//    public RegistryEventConsumer<Retry> myRetryRegistryEventConsumer() {
//
//        return new RegistryEventConsumer<Retry>() {
//            @Override
//            public void onEntryAddedEvent(EntryAddedEvent<Retry> entryAddedEvent) {
//                entryAddedEvent.getAddedEntry().getEventPublisher().onEvent(event -> {
//                    System.out.println("retry RegistryEventConsumer onEntryAddedEvent ------------> onEvent");
//
//                    LOG.info(event.toString());
//                });
//            }
//
//            @Override
//            public void onEntryRemovedEvent(EntryRemovedEvent<Retry> entryRemoveEvent) {
//
//            }
//
//            @Override
//            public void onEntryReplacedEvent(EntryReplacedEvent<Retry> entryReplacedEvent) {
//
//            }
//        };
//    }

}
