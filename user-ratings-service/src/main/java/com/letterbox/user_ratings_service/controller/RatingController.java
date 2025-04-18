package com.letterbox.user_ratings_service.controller;

import com.letterbox.user_ratings_service.service.RatingService;
import com.letterbox.user_ratings_service.model.Rating;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping
    public ResponseEntity<Rating> addRating(@Valid @RequestBody Rating rating) {
        Rating addedRating = ratingService.addRating(rating);
        return new ResponseEntity<>(addedRating, HttpStatus.CREATED);
    }

    @GetMapping("/users/{userId}/movies/{movieId}")
    public ResponseEntity<Rating> getRatingByUserAndMovie(@PathVariable Long userId, @PathVariable Long movieId) {
        Optional<Rating> rating = ratingService.getRatingByUserAndMovie(userId, movieId);
        return rating.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/movies/{movieId}")
    public ResponseEntity<List<Rating>> getRatingsByMovie(@PathVariable Long movieId) {
        List<Rating> ratings = ratingService.getRatingsByMovie(movieId);
        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Rating>> getRatingsByUser(@PathVariable Long userId) {
        List<Rating> ratings = ratingService.getRatingsByUser(userId);
        return ResponseEntity.ok(ratings);
    }

    @PutMapping("/{ratingId}")
    public ResponseEntity<Rating> updateRating(@PathVariable Long ratingId, @Valid @RequestBody Rating updatedRating) {
        try {
            Rating rating = ratingService.updateRating(ratingId, updatedRating);
            return ResponseEntity.ok(rating);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{ratingId}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long ratingId) {
        ratingService.deleteRating(ratingId);
        return ResponseEntity.noContent().build();
    }
}