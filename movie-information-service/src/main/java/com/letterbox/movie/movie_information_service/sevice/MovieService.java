package com.letterbox.movie.movie_information_service.sevice;

import com.letterbox.movie.movie_information_service.model.Movie;
import com.letterbox.movie.movie_information_service.MovieRepository.MovieRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public List<Movie> searchMoviesByTitle(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Movie> findMoviesByGenre(String genre) {
        return movieRepository.findByGenresContainingIgnoreCase(genre);
    }

    public List<Movie> findMoviesByReleaseYear(Integer releaseYear) {
        return movieRepository.findByReleaseYear(releaseYear);
    }

    @Transactional
    public Movie updateMovie(Long id, @Valid Movie updatedMovie) {
        return movieRepository.findById(id)
                .map(movie -> {
                    movie.setTitle(updatedMovie.getTitle() != null ? updatedMovie.getTitle() : movie.getTitle());
                    movie.setReleaseYear(updatedMovie.getReleaseYear() != null ? updatedMovie.getReleaseYear() : movie.getReleaseYear());
                    movie.setDirectors(updatedMovie.getDirectors() != null ? updatedMovie.getDirectors() : movie.getDirectors());
                    movie.setActors(updatedMovie.getActors() != null ? updatedMovie.getActors() : movie.getActors());
                    movie.setGenres(updatedMovie.getGenres() != null ? updatedMovie.getGenres() : movie.getGenres());
                    movie.setSynopsis(updatedMovie.getSynopsis() != null ? updatedMovie.getSynopsis() : movie.getSynopsis());
                    movie.setPosterImageUrl(updatedMovie.getPosterImageUrl() != null ? updatedMovie.getPosterImageUrl() : movie.getPosterImageUrl());
                    movie.setRuntime(updatedMovie.getRuntime() != null ? updatedMovie.getRuntime() : movie.getRuntime());
                    return movieRepository.save(movie);
                })
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    @Transactional
    public void updateAverageRating(Long movieId, Double averageRating) {
        movieRepository.findById(movieId)
                .ifPresent(movie -> {
                    movie.setAverageRating(averageRating);
                    movieRepository.save(movie);
                });
    }
}