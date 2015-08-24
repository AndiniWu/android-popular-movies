package com.gabyquiles.popularmovies.models;

import android.util.Log;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by gabrielquiles-perez on 8/21/15.
 */
public class TrailersPage {
    private final String LOG_TAG = TrailersPage.class.getSimpleName();
    @Expose
    private int id;
    @Expose
    private List<Trailer> results;

    public List<Trailer> getTrailers() {
        Log.v(LOG_TAG, "getting trailers");
        return results;
    }

    public boolean isEmpty() {
        return results.isEmpty();
    }
}
