package com.learnreactiveprogramming.service;

import java.util.List;
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

  // BOBA -> Flux("B", "O", "B", "A")
  public Flux<String> splitString(String name) {
    return Flux.fromArray(name.split("")).log();
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
