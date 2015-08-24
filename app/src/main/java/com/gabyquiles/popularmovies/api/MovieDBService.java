package com.gabyquiles.popularmovies.api;

import com.gabyquiles.popularmovies.models.Movie;
import com.gabyquiles.popularmovies.models.Page;
import com.gabyquiles.popularmovies.models.Review;
import com.gabyquiles.popularmovies.models.Trailer;


import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by gabrielquiles-perez on 7/17/15.
 */
public interface MovieDBService {
    @GET("/discover/movie")
    void getMovies(@Query("sort_by") String sorting, @Query("api_key") String api_key, Callback<Page<Movie>> movies);
    @GET("/movie/{id}/videos")
    void getTrailers(@Path("id") Long id, @Query("api_key") String api_key, Callback<Page<Trailer>> trailers);
    @GET("/movie/{id}/reviews")
    void getReviews(@Path("id") Long id, @Query("api_key") String api_key, Callback<Page<Review>> reviews);
}
