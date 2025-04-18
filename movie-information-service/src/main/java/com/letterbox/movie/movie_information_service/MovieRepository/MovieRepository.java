package com.letterbox.movie.movie_information_service.MovieRepository;

import com.letterbox.movie.movie_information_service.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByTitleContainingIgnoreCase(String title);
    List<Movie> findByGenresContainingIgnoreCase(String genre);
    List<Movie> findByReleaseYear(Integer releaseYear);
}