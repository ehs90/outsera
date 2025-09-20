package com.ehs.outsera.service;

import com.ehs.outsera.model.Movie;
import com.ehs.outsera.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Optional<Movie> updateMovie(Long id, Movie movie) {
        Optional<Movie> existing = movieRepository.findById(id);
        if (existing.isEmpty()) {
            return Optional.empty();
        }
        movie.setId(id);
        return Optional.of(movieRepository.save(movie));
    }

    public boolean deleteMovie(Long id) {
        if (movieRepository.existsById(id)) {
            movieRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
