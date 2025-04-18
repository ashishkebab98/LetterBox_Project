package com.letterbox.movie.movie_information_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Entity
@Table(name = "movies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    private String title;

    private Integer releaseYear;

    @ElementCollection
    private List<String> directors;

    @ElementCollection
    private List<String> actors;

    @ElementCollection
    private List<String> genres;

    @Size(max = 1000, message = "Synopsis cannot exceed 1000 characters")
    private String synopsis;

    @Size(max = 255, message = "Poster URL cannot exceed 255 characters")
    private String posterImageUrl;

    private Integer runtime;

    private Double averageRating;
}