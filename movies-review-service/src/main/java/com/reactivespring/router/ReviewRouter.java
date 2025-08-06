package com.reactivespring.router;

import com.reactivespring.handler.ReviewHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ReviewRouter {
    @Bean
    public RouterFunction<ServerResponse> reviewsRoute(ReviewHandler reviewHandler) {
        return route()
                .GET("/v1/helloWorld", (request -> ServerResponse.ok().body(Mono.just("Hello World"), String.class)))
                .POST("/v1/reviews", reviewHandler::addReview)
                .GET("/v1/reviews", reviewHandler::getReviews)
                .build();
    }
}
