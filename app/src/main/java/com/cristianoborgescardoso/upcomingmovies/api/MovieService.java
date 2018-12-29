package com.cristianoborgescardoso.upcomingmovies.api;

import com.cristianoborgescardoso.upcomingmovies.modelJson.GenreResponse;
import com.cristianoborgescardoso.upcomingmovies.modelJson.Movie;
import com.cristianoborgescardoso.upcomingmovies.modelJson.MovieDetailsResponse;
import com.cristianoborgescardoso.upcomingmovies.modelJson.MovieMediaResponse;
import com.cristianoborgescardoso.upcomingmovies.modelJson.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {

    /**
     * Get a list of upcoming movies in theatres.
     */
    @GET("movie/upcoming")
    Call<MoviesResponse> getUpcomingMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("region") String region,
            @Query("page") long pageNumber);

    @GET("genre/movie/list")
    Call<GenreResponse> getGenres(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    /**
     * Get movie details.
     */
    @GET("movie/{id}")
    Call<MovieDetailsResponse> getMovieDetails(
            @Path("id") Long id,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("region") String region
    );

    /**
     * Get a list of movies in theatres.
     */
    @GET("movie/now_playing")
    Call<MoviesResponse> getMoviesInTheater(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("region") String region,
            @Query("page") long pageNumber);

    /**
     * Get the most newly created movie.
     */
    @GET("movie/latest")
    Call<Movie> getLatestMovies();

    /**
     * Get the videos that have been added to a movie.
     */
    @GET("movie/{movie_id}/videos")
    Call<MovieMediaResponse> getMovieVideo(
            @Path("id") Long id,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("region") String region);
}
