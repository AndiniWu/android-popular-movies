package com.gabyquiles.popularmovies.models;

import com.google.gson.annotations.Expose;

/**
 * Created by gabrielquiles-perez on 8/24/15.
 */
public class Review {
    @Expose
    private String id;
    @Expose
    private String author;
    @Expose
    private String content;

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
