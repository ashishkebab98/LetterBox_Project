# Letterbox Microservices Project

A microservices architecture project for a movie-related social networking application (similar to Letterboxd).

## Table of Contents

* [Project Description](#project-description)
* [Services](#services)
    * [User Service](#user-service)
    * [Movie Information Service](#movie-information-service)
    * [User Ratings Service](#user-ratings-service)
    * [Review Service](#review-service)
    * [Watchlist Service](#watchlist-service)
* [Technologies Used](#technologies-used)

## Project Description

This project implements a microservices architecture for a movie-related social networking application. It is designed to allow users to:

* View information about movies
* Rate movies
* Write reviews for movies
* Maintain a watchlist of movies they want to see

The application is broken down into several independent services, each responsible for a specific domain of functionality. This architecture promotes scalability, maintainability, and fault tolerance.

## Services

### User Service

* **Description:** Manages user accounts and authentication.
* **Functionality:**
    * User registration and login
    * User profile management
    * Authentication and authorization
* **Endpoints:**
    * `POST /users/register`
    * `POST /users/login`
    * `GET /users/{userId}`
    * `PUT /users/{userId}`

### Movie Information Service

* **Description:** Stores and manages movie data.
* **Functionality:**
    * CRUD operations for movie information
    * Retrieving movie details
* **Endpoints:**
    * `POST /movies`
    * `GET /movies/{movieId}`
    * `PUT /movies/{movieId}`
    * `DELETE /movies/{movieId}`

### User Ratings Service

* **Description:** Handles user ratings for movies.
* **Functionality:**
    * Storing and retrieving user ratings
    * Calculating average movie ratings
* **Endpoints**
    * `POST /ratings`
    * `GET /ratings/{ratingId}`
    * `GET /ratings/movies/{movieId}`
    * `GET /ratings/users/{userId}`

### Review Service

* **Description:** Manages user reviews for movies.
* **Functionality:**
    * CRUD operations for reviews
    * Retrieving reviews for a movie
    * Retrieving reviews by a user
* **Endpoints:**
    * `POST /reviews`
    * `GET /reviews/{reviewId}`
    * `PUT /reviews/{reviewId}`
    * `DELETE /reviews/{reviewId}`
    * `GET /reviews/movies/{movieId}`
    * `GET /reviews/users/{userId}`

### Watchlist Service

* **Description:** Manages users' watchlists.
* **Functionality:**
    * Adding movies to a user's watchlist
    * Removing movies from a user's watchlist
    * Retrieving a user's watchlist
* **Endpoints:**
    * `POST /watchlists/{userId}/movies/{movieId}`
    * `DELETE /watchlists/{userId}/movies/{movieId}`
    * `GET /watchlists/{userId}`

## Technologies Used

\* Programming Language: Java
\* Framework: Spring Boot
\* Database: H2 Database
\* API Gateway: Spring Cloud Gateway
\* Build Tool: Maven/Gradle
