package com.gabyquiles.popularmovies.models;

import com.google.gson.annotations.Expose;

/**
 * Created by gabrielquiles-perez on 8/24/15.
 */
public class Review {
    @Expose
    private Long id;
    @Expose
    private String author;
    @Expose
    private String content;
}
