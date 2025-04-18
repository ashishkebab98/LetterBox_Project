package com.letterbox.user_ratings_service.service;

import com.letterbox.user_ratings_service.client.MovieInformationServiceClient;
import com.letterbox.user_ratings_service.model.Rating;
import com.letterbox.user_ratings_service.repository.RatingRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final MovieInformationServiceClient movieInformationServiceClient; // Feign client

    @Autowired
    public RatingService(RatingRepository ratingRepository, MovieInformationServiceClient movieInformationServiceClient) {
        this.ratingRepository = ratingRepository;
        this.movieInformationServiceClient = movieInformationServiceClient;
    }

    public Rating addRating(Rating rating) {
        Rating savedRating = ratingRepository.save(rating);
        updateAverageMovieRating(rating.getMovieId());
        return savedRating;
    }

    public Optional<Rating> getRatingByUserAndMovie(Long userId, Long movieId) {
        return ratingRepository.findByUserIdAndMovieId(userId, movieId);
    }

    public List<Rating> getRatingsByMovie(Long movieId) {
        return ratingRepository.findByMovieId(movieId);
    }

    public List<Rating> getRatingsByUser(Long userId) {
        return ratingRepository.findByUserId(userId);
    }

    @Transactional
    public Rating updateRating(Long id, @Valid Rating updatedRating) {
        return ratingRepository.findById(id)
                .map(rating -> {
                    rating.setRatingValue(updatedRating.getRatingValue() != null ? updatedRating.getRatingValue() : rating.getRatingValue());
                    rating.setReviewText(updatedRating.getReviewText() != null ? updatedRating.getReviewText() : rating.getReviewText());
                    rating.setWatchDate(updatedRating.getWatchDate() != null ? updatedRating.getWatchDate() : rating.getWatchDate());
                    Rating savedRating = ratingRepository.save(rating);
                    updateAverageMovieRating(savedRating.getMovieId());
                    return savedRating;
                })
                .orElseThrow(() -> new RuntimeException("Rating not found with id: " + id));
    }

    public void deleteRating(Long id) {
        Optional<Rating> ratingToDelete = ratingRepository.findById(id);
        ratingToDelete.ifPresent(rating -> {
            ratingRepository.deleteById(id);
            updateAverageMovieRating(rating.getMovieId());
        });
    }

    private void updateAverageMovieRating(Long movieId) {
        List<Rating> movieRatings = ratingRepository.findByMovieId(movieId);
        if (!movieRatings.isEmpty()) {
            double average = movieRatings.stream()
                    .collect(Collectors.averagingDouble(Rating::getRatingValue));
            movieInformationServiceClient.updateMovieRating(movieId, average);
        } else {
            movieInformationServiceClient.updateMovieRating(movieId, 0.0);
        }
    }
}