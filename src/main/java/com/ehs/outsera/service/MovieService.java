package com.ehs.outsera.service;

import com.ehs.outsera.model.Movie;
import com.ehs.outsera.repository.MovieRepository;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void createMovie(Movie movie) {
        movieRepository.save(movie);
    }

}

