package com.gabyquiles.popularmovies.provider.movie;

import com.gabyquiles.popularmovies.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Data model for the {@code movie} table.
 */
public interface MovieModel extends BaseModel {

    /**
     * Id in MovieDB
     */
    long getMoviedbId();

    /**
     * Title of the movie.
     * Can be {@code null}.
     */
    @Nullable
    String getTitle();

    /**
     * Date in which the movie will be released.
     * Can be {@code null}.
     */
    @Nullable
    String getReleaseDate();

    /**
     * Get the {@code synopsis} value.
     * Can be {@code null}.
     */
    @Nullable
    String getSynopsis();

    /**
     * Get the {@code poster_path} value.
     * Can be {@code null}.
     */
    @Nullable
    String getPosterPath();

    /**
     * Get the {@code user_rating} value.
     * Can be {@code null}.
     */
    @Nullable
    String getUserRating();
}
