package com.reactivespring.router;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ReviewRouter {
    public RouterFunction<ServerResponse> reviewsRoute() {
        return route()
                .GET("/v1/helloWorld", (request -> ServerResponse.ok().body(Mono.just("Hello World"), String.class)))
                .build();
    }
}
