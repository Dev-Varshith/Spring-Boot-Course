package com.learnreactiveprogramming.service;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
// import lombok.experimental.var;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

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

  public Flux<String> namesFlux_transform(int stringLength) {
    Function<Flux<String>, Flux<String>> filterMap = name ->
      name.map(String::toUpperCase).filter(s -> s.length() > stringLength);

    return Flux
      .fromIterable(List.of("Boba", "Chole"))
      .transform(filterMap)
      .flatMap(name -> splitString(name))
      .log();
  }

  public Flux<String> namesFlux_transform_switchIfEmpty(int stringLength) {
    Function<Flux<String>, Flux<String>> filterMap = name ->
      name
        .map(String::toUpperCase)
        .filter(s -> s.length() > stringLength)
        .flatMap(s -> splitString(s)); // "B", "O", "B", "A", "C", "H", "O", "L", "E"

    var defaultFlux = Flux.just("default").transform(filterMap); // "D", "E", "F", "A", "U", "L", "T"

    return Flux
      .fromIterable(List.of("Boba", "Chole"))
      .transform(filterMap)
      .switchIfEmpty(defaultFlux)
      .log();
  }

  public Flux<String> explore_concat() {
    var flux1 = Flux.just("A", "B", "C");
    var flux2 = Flux.just("D", "E", "F");

    return Flux.concat(flux1, flux2).log();
  }

  public Flux<String> explore_concatWith_flux() {
    var flux1 = Flux.just("A", "B", "C");
    var flux2 = Flux.just("D", "E", "F");

    return flux1.concatWith(flux2).log();
  }

  public Flux<String> explore_concatWith_mono() {
    var mono1 = Flux.just("A");
    var mono2 = Flux.just("B");

    return mono1.concatWith(mono2).log();
  }

  public Flux<String> explore_merge() {
    var flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofMillis(100));
    var flux2 = Flux.just("D", "E", "F").delayElements(Duration.ofMillis(125));

    return Flux.merge(flux1, flux2).log();
  }

  public Flux<String> explore_mergeWith() {
    var flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofMillis(100));
    var flux2 = Flux.just("D", "E", "F").delayElements(Duration.ofMillis(125));

    return flux1.mergeWith(flux2).log();
  }

  public Flux<String> explore_mergeWith_mono() {
    var mono1 = Mono.just("A");
    var mono2 = Mono.just("D");

    return mono1.mergeWith(mono2).log();
  }

  public Flux<String> explore_mergeSequential() {
    var flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofMillis(100));
    var flux2 = Flux.just("D", "E", "F").delayElements(Duration.ofMillis(125));

    return Flux.mergeSequential(flux1, flux2).log();
  }

  public Flux<String> explore_zip() {
    var flux1 = Flux.just("A", "B", "C");
    var flux2 = Flux.just("D", "E", "F");

    return Flux.zip(flux1, flux2, (first, second) -> first + second).log();
  }

  public Flux<String> explore_zip_1() {
    var flux1 = Flux.just("A", "B", "C");
    var flux2 = Flux.just("D", "E", "F");
    var flux3 = Flux.just("1", "2", "3");
    var flux4 = Flux.just("4", "5", "6");

    return Flux
      .zip(flux1, flux2, flux3, flux4)
      .map(t4 -> t4.getT1() + t4.getT2() + t4.getT3() + t4.getT4())
      .log();
  }

  public Flux<String> explore_zipWith() {
    var flux1 = Flux.just("A", "B", "C");
    var flux2 = Flux.just("D", "E", "F");

    return flux1.zipWith(flux2, (first, second) -> first + second).log();
  }

  public Mono<String> explore_zipWith_mono() {
    var mono1 = Mono.just("A");
    var mono2 = Mono.just("D");

    return mono1.zipWith(mono2).map(t2 -> t2.getT1() + t2.getT2()).log();
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
