package com.gabyquiles.popularmovies.provider.movie;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.gabyquiles.popularmovies.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code movie} table.
 */
public class MovieCursor extends AbstractCursor implements MovieModel {
    public MovieCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(MovieColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Id in MovieDB
     */
    public long getMoviedbId() {
        Long res = getLongOrNull(MovieColumns.MOVIEDB_ID);
        if (res == null)
            throw new NullPointerException("The value of 'moviedb_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Title of the movie.
     * Can be {@code null}.
     */
    @Nullable
    public String getTitle() {
        String res = getStringOrNull(MovieColumns.TITLE);
        return res;
    }

    /**
     * Date in which the movie will be released.
     * Can be {@code null}.
     */
    @Nullable
    public String getReleaseDate() {
        String res = getStringOrNull(MovieColumns.RELEASE_DATE);
        return res;
    }

    /**
     * Get the {@code synopsis} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getSynopsis() {
        String res = getStringOrNull(MovieColumns.SYNOPSIS);
        return res;
    }

    /**
     * Get the {@code poster_path} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getPosterPath() {
        String res = getStringOrNull(MovieColumns.POSTER_PATH);
        return res;
    }

    /**
     * Get the {@code user_rating} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getUserRating() {
        String res = getStringOrNull(MovieColumns.USER_RATING);
        return res;
    }
}
