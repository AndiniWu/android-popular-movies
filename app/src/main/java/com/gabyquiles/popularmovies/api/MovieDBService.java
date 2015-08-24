package com.gabyquiles.popularmovies.api;

import com.gabyquiles.popularmovies.models.MoviePage;
import com.gabyquiles.popularmovies.models.TrailersPage;


import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by gabrielquiles-perez on 7/17/15.
 */
public interface MovieDBService {
    @GET("/discover/movie")
    void getMovies(@Query("sort_by") String sorting, @Query("api_key") String api_key, Callback<MoviePage> movies);
    @GET("/movie/{id}/videos")
    void getTrailers(@Path("id") Long id, @Query("api_key") String api_key, Callback<TrailersPage> trailers);
}
