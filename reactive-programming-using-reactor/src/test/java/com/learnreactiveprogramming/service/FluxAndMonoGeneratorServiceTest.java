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
}
