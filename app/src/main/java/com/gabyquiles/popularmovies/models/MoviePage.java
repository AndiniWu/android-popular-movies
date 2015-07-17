package com.gabyquiles.popularmovies.models;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by gabrielquiles-perez on 7/17/15.
 */
public class MoviePage {
    @Expose
    private int page;
    @Expose
    private List<Movie> results;

    public List<Movie> getMovies() {
        return results;
    }

    public boolean isEmpty() {
        return results.isEmpty();
    }
}
