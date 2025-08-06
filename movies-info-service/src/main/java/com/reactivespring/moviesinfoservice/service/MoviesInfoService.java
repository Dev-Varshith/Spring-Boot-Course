package com.reactivespring.moviesinfoservice.service;

import com.reactivespring.moviesinfoservice.domain.MovieInfo;
import com.reactivespring.moviesinfoservice.repository.MovieInfoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@Service
public class MoviesInfoService {

    private MovieInfoRepository movieInfoRepository;

    public MoviesInfoService(MovieInfoRepository movieInfoRepository) {
        this.movieInfoRepository = movieInfoRepository;
    }

    public Mono<MovieInfo> addMovieInfo(MovieInfo movieInfo) {
        return movieInfoRepository.save(movieInfo);
    }

    public Flux<MovieInfo> getAllMovieInfos() {
        return movieInfoRepository.findAll();
    }

    public Mono<MovieInfo> getMovieInfoById(String movieInfoId) {
        return movieInfoRepository.findById(movieInfoId);
    }

    public Mono<MovieInfo> updateMovieInfo(String movieInfoId, MovieInfo movieInfo) {
        return movieInfoRepository.findById(movieInfoId)
                .flatMap(movieInfo1 -> {
                    movieInfo1.setCast(movieInfo.getCast());
                    movieInfo1.setName(movieInfo.getName());
                    movieInfo1.setReleaseDate(movieInfo.getReleaseDate());
                    movieInfo1.setYear(movieInfo.getYear());
                    return movieInfoRepository.save(movieInfo1);
                });
    }

    public Mono<Void> deleteMovieInfo(String movieInfoId) {
        return movieInfoRepository.deleteById(movieInfoId);
    }

    public Flux<MovieInfo> getMovieInfoByYear(Integer year) {
        return movieInfoRepository.findByYear(year);
    }

    public Mono<MovieInfo> getMovieInfoByName(String name) {
        return movieInfoRepository.findByName(name);
    }
}
