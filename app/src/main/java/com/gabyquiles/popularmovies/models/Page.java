package com.gabyquiles.popularmovies.models;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by gabrielquiles-perez on 8/24/15.
 */
public class Page<T> {
    @Expose
    private int page;
    @Expose
    private List<T> results;

    public List<T> getResults() {
        return results;
    }

    public boolean isEmpty() {
        return results.isEmpty();
    }

}
