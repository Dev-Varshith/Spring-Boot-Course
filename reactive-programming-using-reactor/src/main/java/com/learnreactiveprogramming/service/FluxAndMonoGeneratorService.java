package com.learnreactiveprogramming.service;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FluxAndMonoGeneratorService {

  public Flux<String> namesFlux() {
    return Flux
      .fromIterable(List.of("Varshith", "Ruthvik", "Alex", "Vaishnavi"))
      .log();
  }

  public Mono<String> nameMono() {
    return Mono.just("Varshith").log();
  }

  public Flux<String> namesFlux_map() {
    return Flux
      .fromIterable(List.of("Varshith", "Ruthvik", "Alex", "Vaishnavi"))
      .map(String::toUpperCase)
      // .map(s -> s.toUpperCase())
      .log();
  }

  public Flux<String> namesFlux_immutability() {
    // This demonstrates immutability in Flux
    var namesFlux = Flux.fromIterable(
      List.of("Varshith", "Ruthvik", "Alex", "Vaishnavi")
    );

    namesFlux.map(String::toUpperCase);
    return namesFlux;
  }

  public Mono<String> namesMono_map_filter(int stringLength) {
    return Mono
      .just("Varshith")
      .map(String::toUpperCase)
      .filter(s -> s.length() > stringLength);
  }

  public Mono<List<String>> namesMono_flatMap(int stringLength) {
    return Mono
      .just("Varshith")
      .map(String::toUpperCase)
      .filter(s -> s.length() > stringLength)
      .flatMap(this::splitStringMono)
      .log(); // Mono<List of A, L, E, X>
  }

  public Flux<String> namesMono_flatMapMany(int stringLength) {
    return Mono
      .just("Varshith")
      .map(String::toUpperCase)
      .filter(s -> s.length() > stringLength)
      .flatMapMany(this::splitString)
      .log(); // Mono<List of A, L, E, X>
  }

  private Mono<List<String>> splitStringMono(String name) {
    var charArray = name.split("");
    var charList = List.of(charArray);
    return Mono.just(charList);
    //  return null;
  }

  public Flux<String> namesFlux_filter(int stringLength) {
    return Flux
      .fromIterable(List.of("Varshith", "Ruthvik", "Alex", "Vaishnavi"))
      // .filter(name -> name.startsWith("V"))
      .filter(name -> name.length() > stringLength)
      .map(s -> s.length() + " - " + s)
      .log();
  }

  public Flux<String> namesFlux_flatMap(int stringLength) {
    return Flux
      .fromIterable(List.of("Boba", "Chole"))
      .map(String::toUpperCase)
      .filter(s -> s.length() > stringLength)
      .flatMap(name -> splitString(name))
      .log();
  }

  public Flux<String> namesFlux_flatMap_async(int stringLength) {
    return Flux
      .fromIterable(List.of("Boba", "Chole"))
      .map(String::toUpperCase)
      .filter(s -> s.length() > stringLength)
      .flatMap(name -> splitString_withDelay(name))
      .log();
  }

  public Flux<String> namesFlux_concatMap(int stringLength) {
    return Flux
      .fromIterable(List.of("Boba", "Chole"))
      .map(String::toUpperCase)
      .filter(s -> s.length() > stringLength)
      .concatMap(name -> splitString_withDelay(name))
      .log();
  }

  // BOBA -> Flux("B", "O", "B", "A")
  public Flux<String> splitString(String name) {
    return Flux.fromArray(name.split("")).log();
  }

  public Flux<String> splitString_withDelay(String name) {
    var charArray = name.split("");
    var delay = new Random().nextInt(1000);
    return Flux
      .fromArray(charArray)
      .delayElements(Duration.ofMillis(delay))
      .log();
  }

  public static void main(String[] args) {
    FluxAndMonoGeneratorService service = new FluxAndMonoGeneratorService();
    service.namesFlux().subscribe(name -> System.out.println("Name: " + name));

    service
      .nameMono()
      .subscribe(name -> System.out.println("Mono Name: " + name));
    // .subscribe(name -> System.out.println("Name: " + name));
  }
}
