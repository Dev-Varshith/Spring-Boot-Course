package com.reactivespring.routes;

import com.reactivespring.domain.Review;
import com.reactivespring.repository.ReviewReactiveRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
public class ReviewsIntgTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    ReviewReactiveRepository reviewReactiveRepository;

    static String MOVIES_REVIEW_URL = "/v1/reviews";

    @BeforeEach
    void setUp() {
        var reviewsList = List.of(
                new Review("1", 1L, "Awesome Movie", 9.0),
                new Review(null, 1L, "Awesome Movie1", 9.0),
                new Review(null, 2L, "Excellent Movie", 8.0));
        reviewReactiveRepository.saveAll(reviewsList)
                .blockLast();
    }

    @AfterEach
    void tearDown() {
        reviewReactiveRepository.deleteAll().block();
    }

    @Test
    void addReview() {
        //given
        var review = new Review("1", 1L, "Awesome Movie", 9.0);

        //when
        webTestClient
                .post()
                .uri(MOVIES_REVIEW_URL)
                .bodyValue(review)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(Review.class)
                .consumeWith(movieReviewEntityExchangeResult -> {
                    var savedReview = movieReviewEntityExchangeResult.getResponseBody();
                    assert savedReview != null;
                    assert savedReview.getReviewId() != null;
                });

        //then
    }

    @Test
    void getReviews() {
        //given

        //when
        webTestClient
                .get()
                .uri(MOVIES_REVIEW_URL)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Review.class)
                .hasSize(3);

        //then
    }

    @Test
    void updateReview() {
        //given
        var reviewId = "1";

        var movieReview = new Review("1", 1L, "Awesome Movie Update", 9.0);

        //when
        webTestClient
                .put()
                .uri(MOVIES_REVIEW_URL + "/{reviewId}", reviewId)
                .bodyValue(movieReview)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(Review.class)
                .consumeWith(movieReviewEntityExchangeResult -> {
                    var updatedMovieReview = movieReviewEntityExchangeResult.getResponseBody();
                    assert updatedMovieReview != null;
                    assert updatedMovieReview.getComment() != null;
                    assertEquals("Awesome Movie Update", updatedMovieReview.getComment());
                });
        //then
    }

    @Test
    void deleteReview() {
        //given
        var reviewId = "1";

        //when
        webTestClient
                .delete()
                .uri(MOVIES_REVIEW_URL + "/{reviewId}", reviewId)
                .exchange()
                .expectStatus()
                .isNoContent();

    }

    @Test
    void getReviewsByMovieInfoId() {
        //given
        var uri = UriComponentsBuilder.fromUriString(MOVIES_REVIEW_URL)
                .queryParam("movieInfoId", 1L)
                .buildAndExpand().toUri();

        //when
        webTestClient
                .get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Review.class);
    }
}
