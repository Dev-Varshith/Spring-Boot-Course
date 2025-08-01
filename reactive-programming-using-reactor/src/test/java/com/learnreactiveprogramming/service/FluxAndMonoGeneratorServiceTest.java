package com.learnreactiveprogramming.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

class FluxAndMonoGeneratorServiceTest {

  FluxAndMonoGeneratorService service = new FluxAndMonoGeneratorService();

  // Add your test methods here
  @Test
  void sanityTest() {
    System.out.println(">> Sanity test running...");
    assertTrue(true);
  }

  @Test
  void namesFlux() {
    System.out.println("Running namesFlux test...");
    //given

    //when
    var namesFlux = service.namesFlux();

    //then
    StepVerifier
      .create(namesFlux)
      .expectSubscription()
      // .expectNext("Varshith", "Ruthvik", "Alex", "Vaishnavi")
      .expectNext("Varshith")
      .expectNextCount(3)
      .verifyComplete();
  }

  @Test
  void namesFlux_map() {
    //given

    //when
    var namesFluxMap = service.namesFlux_map();

    //then
    StepVerifier
      .create(namesFluxMap)
      // .expectSubscription()
      .expectNext("VARSHITH", "RUTHVIK", "ALEX", "VAISHNAVI")
      .verifyComplete();
  }

  @Test
  void namesFlux_immutability() {
    //given
    //when
    var namesFluxImmutability = service.namesFlux_immutability();
    //then
    StepVerifier
      .create(namesFluxImmutability)
      .expectNext("Varshith", "Ruthvik", "Alex", "Vaishnavi")
      .verifyComplete();
  }

  @Test
  void namesFlux_filter() {
    //given
    int stringLength = 6;
    //when
    var namesFlux = service.namesFlux_filter(stringLength);

    //then
    StepVerifier
      .create(namesFlux)
      .expectNext("8 - Varshith", "7 - Ruthvik", "9 - Vaishnavi")
      .verifyComplete();
  }

  // @Test
  // void namesMono_filter() {
  //   //given
  //   int stringLength = 6;
  //   //when
  //   var namesFlux = service.namesMono_filter(stringLength);

  //   //then
  //   StepVerifier
  //     .create(namesFlux)
  //     .expectNext("8 - Varshith", "7 - Ruthvik", "9 - Vaishnavi")
  //     .verifyComplete();
  // }

  @Test
  void namesFlux_flatMap() {
    //given
    int stringLength = 3;

    //when
    var namesFlux = service.namesFlux_flatMap(stringLength);

    //then
    StepVerifier
      .create(namesFlux)
      .expectNext("B", "O", "B", "A", "C", "H", "O", "L", "E")
      .verifyComplete();
  }

  @Test
  void namesFlux_flatMap_async() {
    //given
    int stringLength = 3;

    //when
    var namesFlux = service.namesFlux_flatMap_async(stringLength);

    //then
    StepVerifier
      .create(namesFlux)
      // .expectNext("B", "O", "B", "A", "C", "H", "O", "L", "E")
      .expectNextCount(9)
      .verifyComplete();
  }

  @Test
  void namesFlux_concatMap() {
    //given
    int stringLength = 3;

    //when
    var namesFlux = service.namesFlux_concatMap(stringLength);

    //then
    StepVerifier
      .create(namesFlux)
      // .expectNext("B", "O", "B", "A", "C", "H", "O", "L", "E")
      .expectNextCount(9)
      .verifyComplete();
  }

  @Test
  void namesMono_flatMap() {
    //given
    int stringLength = 6;

    //when
    var namesMono = service.namesMono_flatMap(6);

    //then
    StepVerifier
      .create(namesMono)
      .expectNext(List.of("V", "A", "R", "S", "H", "I", "T", "H"))
      .verifyComplete();
  }

  @Test
  void namesMono_flatMapMany() {
    //given
    int stringLength = 6;

    //when
    var namesMono = service.namesMono_flatMapMany(stringLength);

    //then
    StepVerifier
      .create(namesMono)
      .expectNext("V", "A", "R", "S", "H", "I", "T", "H")
      .verifyComplete();
  }

  @Test
  void namesFlux_transform() {
    //given
    int stringLength = 3;

    //when
    var namesFlux = service.namesFlux_transform(stringLength);

    //then
    StepVerifier
      .create(namesFlux)
      .expectNext("B", "O", "B", "A", "C", "H", "O", "L", "E")
      // .expectNextCount(9)
      .verifyComplete();
  }

  @Test
  void namesFlux_transform_switchIfEmpty() {
    //given
    int stringLength = 6;

    //when
    var namesFlux = service.namesFlux_transform_switchIfEmpty(stringLength);

    //then
    StepVerifier
      .create(namesFlux)
      .expectNext("D", "E", "F", "A", "U", "L", "T")
      // .expectNextCount(9)
      .verifyComplete();
  }

  @Test
  void explore_concat() {
    //given

    //when
    var concatFlux = service.explore_concat();

    //then
    StepVerifier
      .create(concatFlux)
      .expectNext("A", "B", "C", "D", "E", "F")
      // .expectNextCount(9)
      .verifyComplete();
  }

  @Test
  void explore_concatWith_flux() {
    //given

    //when
    var concatWithFlux = service.explore_concatWith_flux();

    //then
    StepVerifier
      .create(concatWithFlux)
      .expectNext("A", "B", "C", "D", "E", "F")
      // .expectNextCount(9)
      .verifyComplete();
  }

  @Test
  void explore_concatWith_mono() {
    //given

    //when
    var concatWithMono = service.explore_concatWith_mono();

    //then
    StepVerifier
      .create(concatWithMono)
      .expectNext("A", "B")
      // .expectNextCount(9)
      .verifyComplete();
  }

  @Test
  void explore_merge() {
    //given

    //when
    var concatFlux = service.explore_merge();

    //then
    StepVerifier
      .create(concatFlux)
      .expectNext("A", "D", "B", "E", "C", "F")
      // .expectNextCount(9)
      .verifyComplete();
  }

  @Test
  void explore_mergeWith() {
    //given

    //when
    var concatFlux = service.explore_mergeWith();

    //then
    StepVerifier
      .create(concatFlux)
      .expectNext("A", "D", "B", "E", "C", "F")
      // .expectNextCount(9)
      .verifyComplete();
  }

  @Test
  void explore_mergeWith_mono() {
    //given

    //when
    var concatMono = service.explore_mergeWith_mono();

    //then
    StepVerifier
      .create(concatMono)
      .expectNext("A", "D")
      // .expectNextCount(9)
      .verifyComplete();
  }

  @Test
  void explore_mergeSequential() {
    //given

    //when
    var concatFlux = service.explore_mergeSequential();

    //then
    StepVerifier
      .create(concatFlux)
      .expectNext("A", "B", "C", "D", "E", "F")
      // .expectNextCount(9)
      .verifyComplete();
  }

  @Test
  void explore_zip() {
    //given

    //when
    var concatFlux = service.explore_zip();

    //then
    StepVerifier
      .create(concatFlux)
      .expectNext("AD", "BE", "CF")
      // .expectNextCount(9)
      .verifyComplete();
  }

  @Test
  void explore_zip_1() {
    //given

    //when
    var concatFlux = service.explore_zip_1();

    //then
    StepVerifier
      .create(concatFlux)
      .expectNext("AD14", "BE25", "CF36")
      // .expectNextCount(9)
      .verifyComplete();
  }

  @Test
  void explore_zipWith() {
    //given

    //when
    var concatFlux = service.explore_zipWith();

    //then
    StepVerifier
      .create(concatFlux)
      .expectNext("AD", "BE", "CF")
      // .expectNextCount(9)
      .verifyComplete();
  }

  @Test
  void explore_zipWith_mono() {
    //given

    //when
    var concatFlux = service.explore_zipWith_mono();

    //then
    StepVerifier
      .create(concatFlux)
      .expectNext("AD")
      // .expectNextCount(9)
      .verifyComplete();
  }
}
