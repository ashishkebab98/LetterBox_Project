package com.letterbox.user_ratings_service.repository;

import com.letterbox.user_ratings_service.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    Optional<Rating> findByUserIdAndMovieId(Long userId, Long movieId);
    List<Rating> findByMovieId(Long movieId);
    List<Rating> findByUserId(Long userId);
}