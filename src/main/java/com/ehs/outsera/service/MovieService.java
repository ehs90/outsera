package com.ehs.outsera.service;

import com.ehs.outsera.model.Movie;
import com.ehs.outsera.repository.MovieRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @CacheEvict(value = "intervals", allEntries = true)
    public void saveAllMovies(List<Movie> movies) {
        movieRepository.saveAll(movies);
    }

}
