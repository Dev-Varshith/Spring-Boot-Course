package com.reactivespring.moviesinfoservice.controller;

import com.reactivespring.moviesinfoservice.domain.MovieInfo;
import com.reactivespring.moviesinfoservice.service.MoviesInfoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1")
public class MoviesInfoController {

    private MoviesInfoService moviesInfoService;

    public MoviesInfoController(MoviesInfoService moviesInfoService) {
        this.moviesInfoService = moviesInfoService;
    }

    @GetMapping("/movieInfos")
    @ResponseStatus(HttpStatus.OK)
    public Flux<MovieInfo> getAllMovieInfos() {
        return moviesInfoService.getAllMovieInfos().log();
    }

    @GetMapping("/movieInfos/{movieInfoId}")
    public Mono<MovieInfo> getMovieInfoById(@PathVariable String movieInfoId) {
        return moviesInfoService.getMovieInfoById(movieInfoId).log();
    }

    @PostMapping("/movieInfos")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MovieInfo> addMovieInfo(@RequestBody @Valid MovieInfo movieInfo) {
        return moviesInfoService.addMovieInfo(movieInfo).log();
    }

    @PutMapping("/movieInfos/{movieInfoId}")
    public Mono<MovieInfo> updateMovieInfo(@PathVariable String movieInfoId, @RequestBody MovieInfo movieInfo) {
        return moviesInfoService.updateMovieInfo(movieInfoId, movieInfo).log();
    }

    @DeleteMapping("movieInfos/{movieInfoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteMovieInfo(@PathVariable String movieInfoId) {
        return moviesInfoService.deleteMovieInfo(movieInfoId);
    }
}
