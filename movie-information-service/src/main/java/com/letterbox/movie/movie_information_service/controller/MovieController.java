package com.letterbox.movie.movie_information_service.controller;

import com.letterbox.movie.movie_information_service.model.Movie;
import com.letterbox.movie.movie_information_service.sevice.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<Movie> addMovie(@Valid @RequestBody Movie movie) {
        Movie addedMovie = movieService.addMovie(movie);
        return new ResponseEntity<>(addedMovie, HttpStatus.CREATED);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long movieId) {
        Optional<Movie> movie = movieService.getMovieById(movieId);
        return movie.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movies = movieService.getAllMovies();
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/search/title")
    public ResponseEntity<List<Movie>> searchMoviesByTitle(@RequestParam String title) {
        List<Movie> movies = movieService.searchMoviesByTitle(title);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<Movie>> findMoviesByGenre(@PathVariable String genre) {
        List<Movie> movies = movieService.findMoviesByGenre(genre);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/year/{year}")
    public ResponseEntity<List<Movie>> findMoviesByReleaseYear(@PathVariable Integer year) {
        List<Movie> movies = movieService.findMoviesByReleaseYear(year);
        return ResponseEntity.ok(movies);
    }

    @PutMapping("/{movieId}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long movieId, @Valid @RequestBody Movie updatedMovie) {
        try {
            Movie movie = movieService.updateMovie(movieId, updatedMovie);
            return ResponseEntity.ok(movie);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long movieId) {
        movieService.deleteMovie(movieId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{movieId}/rating")
    public ResponseEntity<Void> updateMovieRating(@PathVariable Long movieId, @RequestParam Double averageRating) {
        movieService.updateAverageRating(movieId, averageRating);
        return ResponseEntity.noContent().build();
    }
}