package com.reactivespring.moviesinfoservice.controller;

import com.reactivespring.moviesinfoservice.domain.MovieInfo;
import com.reactivespring.moviesinfoservice.service.MoviesInfoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1")
@Slf4j
public class MoviesInfoController {

    private MoviesInfoService moviesInfoService;

    public MoviesInfoController(MoviesInfoService moviesInfoService) {
        this.moviesInfoService = moviesInfoService;
    }

    @GetMapping("/movieInfos")
    @ResponseStatus(HttpStatus.OK)
    public Flux<MovieInfo> getAllMovieInfos(
            @RequestParam(value = "year",  required = false) Integer year,
            @RequestParam(value = "name", required = false) String name) {

        log.info("Year is : {}", year);
        log.info("Name is : {}", name);

        if (year != null) {
            return moviesInfoService.getMovieInfoByYear(year).log();
        }

        if (name != null) {
            return moviesInfoService.getMovieInfoByName(name) // Mono<MovieInfo>
                    .flux(); // wrap Mono in Flux so test expecting list still passes
        }

        return moviesInfoService.getAllMovieInfos().log();
    }

    @GetMapping("/movieInfos/{movieInfoId}")
    public Mono<ResponseEntity<MovieInfo>> getMovieInfoById(@PathVariable String movieInfoId) {
        return moviesInfoService.getMovieInfoById(movieInfoId)
                .map(movieInfo1 -> ResponseEntity.ok().body(movieInfo1))
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .log();
    }

    @PostMapping("/movieInfos")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MovieInfo> addMovieInfo(@RequestBody @Valid MovieInfo movieInfo) {
        return moviesInfoService.addMovieInfo(movieInfo).log();
    }

    @PutMapping("/movieInfos/{movieInfoId}")
    public Mono<ResponseEntity<MovieInfo>> updateMovieInfo(@PathVariable String movieInfoId, @RequestBody MovieInfo movieInfo) {
        return moviesInfoService.updateMovieInfo(movieInfoId, movieInfo)
                .map(ResponseEntity.ok()::body)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()))
                .log();
    }

    @DeleteMapping("movieInfos/{movieInfoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteMovieInfo(@PathVariable String movieInfoId) {
        return moviesInfoService.deleteMovieInfo(movieInfoId);
    }
}
