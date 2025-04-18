package com.letterbox.user_ratings_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "movie-information-service")
public interface MovieInformationServiceClient {

    @PutMapping("/movies/{movieId}/rating")
    void updateMovieRating(@PathVariable("movieId") Long movieId, @RequestParam("averageRating") Double averageRating);
}