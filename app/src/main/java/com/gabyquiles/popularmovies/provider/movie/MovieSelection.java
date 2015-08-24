package com.gabyquiles.popularmovies.provider.movie;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.gabyquiles.popularmovies.provider.base.AbstractSelection;

/**
 * Selection for the {@code movie} table.
 */
public class MovieSelection extends AbstractSelection<MovieSelection> {
    @Override
    protected Uri baseUri() {
        return MovieColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code MovieCursor} object, which is positioned before the first entry, or null.
     */
    public MovieCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new MovieCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public MovieCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code MovieCursor} object, which is positioned before the first entry, or null.
     */
    public MovieCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new MovieCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public MovieCursor query(Context context) {
        return query(context, null);
    }


    public MovieSelection id(long... value) {
        addEquals("movie." + MovieColumns._ID, toObjectArray(value));
        return this;
    }

    public MovieSelection idNot(long... value) {
        addNotEquals("movie." + MovieColumns._ID, toObjectArray(value));
        return this;
    }

    public MovieSelection orderById(boolean desc) {
        orderBy("movie." + MovieColumns._ID, desc);
        return this;
    }

    public MovieSelection orderById() {
        return orderById(false);
    }

    public MovieSelection moviedbId(long... value) {
        addEquals(MovieColumns.MOVIEDB_ID, toObjectArray(value));
        return this;
    }

    public MovieSelection moviedbIdNot(long... value) {
        addNotEquals(MovieColumns.MOVIEDB_ID, toObjectArray(value));
        return this;
    }

    public MovieSelection moviedbIdGt(long value) {
        addGreaterThan(MovieColumns.MOVIEDB_ID, value);
        return this;
    }

    public MovieSelection moviedbIdGtEq(long value) {
        addGreaterThanOrEquals(MovieColumns.MOVIEDB_ID, value);
        return this;
    }

    public MovieSelection moviedbIdLt(long value) {
        addLessThan(MovieColumns.MOVIEDB_ID, value);
        return this;
    }

    public MovieSelection moviedbIdLtEq(long value) {
        addLessThanOrEquals(MovieColumns.MOVIEDB_ID, value);
        return this;
    }

    public MovieSelection orderByMoviedbId(boolean desc) {
        orderBy(MovieColumns.MOVIEDB_ID, desc);
        return this;
    }

    public MovieSelection orderByMoviedbId() {
        orderBy(MovieColumns.MOVIEDB_ID, false);
        return this;
    }

    public MovieSelection title(String... value) {
        addEquals(MovieColumns.TITLE, value);
        return this;
    }

    public MovieSelection titleNot(String... value) {
        addNotEquals(MovieColumns.TITLE, value);
        return this;
    }

    public MovieSelection titleLike(String... value) {
        addLike(MovieColumns.TITLE, value);
        return this;
    }

    public MovieSelection titleContains(String... value) {
        addContains(MovieColumns.TITLE, value);
        return this;
    }

    public MovieSelection titleStartsWith(String... value) {
        addStartsWith(MovieColumns.TITLE, value);
        return this;
    }

    public MovieSelection titleEndsWith(String... value) {
        addEndsWith(MovieColumns.TITLE, value);
        return this;
    }

    public MovieSelection orderByTitle(boolean desc) {
        orderBy(MovieColumns.TITLE, desc);
        return this;
    }

    public MovieSelection orderByTitle() {
        orderBy(MovieColumns.TITLE, false);
        return this;
    }

    public MovieSelection releaseDate(String... value) {
        addEquals(MovieColumns.RELEASE_DATE, value);
        return this;
    }

    public MovieSelection releaseDateNot(String... value) {
        addNotEquals(MovieColumns.RELEASE_DATE, value);
        return this;
    }

    public MovieSelection releaseDateLike(String... value) {
        addLike(MovieColumns.RELEASE_DATE, value);
        return this;
    }

    public MovieSelection releaseDateContains(String... value) {
        addContains(MovieColumns.RELEASE_DATE, value);
        return this;
    }

    public MovieSelection releaseDateStartsWith(String... value) {
        addStartsWith(MovieColumns.RELEASE_DATE, value);
        return this;
    }

    public MovieSelection releaseDateEndsWith(String... value) {
        addEndsWith(MovieColumns.RELEASE_DATE, value);
        return this;
    }

    public MovieSelection orderByReleaseDate(boolean desc) {
        orderBy(MovieColumns.RELEASE_DATE, desc);
        return this;
    }

    public MovieSelection orderByReleaseDate() {
        orderBy(MovieColumns.RELEASE_DATE, false);
        return this;
    }

    public MovieSelection synopsis(String... value) {
        addEquals(MovieColumns.SYNOPSIS, value);
        return this;
    }

    public MovieSelection synopsisNot(String... value) {
        addNotEquals(MovieColumns.SYNOPSIS, value);
        return this;
    }

    public MovieSelection synopsisLike(String... value) {
        addLike(MovieColumns.SYNOPSIS, value);
        return this;
    }

    public MovieSelection synopsisContains(String... value) {
        addContains(MovieColumns.SYNOPSIS, value);
        return this;
    }

    public MovieSelection synopsisStartsWith(String... value) {
        addStartsWith(MovieColumns.SYNOPSIS, value);
        return this;
    }

    public MovieSelection synopsisEndsWith(String... value) {
        addEndsWith(MovieColumns.SYNOPSIS, value);
        return this;
    }

    public MovieSelection orderBySynopsis(boolean desc) {
        orderBy(MovieColumns.SYNOPSIS, desc);
        return this;
    }

    public MovieSelection orderBySynopsis() {
        orderBy(MovieColumns.SYNOPSIS, false);
        return this;
    }

    public MovieSelection posterPath(String... value) {
        addEquals(MovieColumns.POSTER_PATH, value);
        return this;
    }

    public MovieSelection posterPathNot(String... value) {
        addNotEquals(MovieColumns.POSTER_PATH, value);
        return this;
    }

    public MovieSelection posterPathLike(String... value) {
        addLike(MovieColumns.POSTER_PATH, value);
        return this;
    }

    public MovieSelection posterPathContains(String... value) {
        addContains(MovieColumns.POSTER_PATH, value);
        return this;
    }

    public MovieSelection posterPathStartsWith(String... value) {
        addStartsWith(MovieColumns.POSTER_PATH, value);
        return this;
    }

    public MovieSelection posterPathEndsWith(String... value) {
        addEndsWith(MovieColumns.POSTER_PATH, value);
        return this;
    }

    public MovieSelection orderByPosterPath(boolean desc) {
        orderBy(MovieColumns.POSTER_PATH, desc);
        return this;
    }

    public MovieSelection orderByPosterPath() {
        orderBy(MovieColumns.POSTER_PATH, false);
        return this;
    }

    public MovieSelection userRating(String... value) {
        addEquals(MovieColumns.USER_RATING, value);
        return this;
    }

    public MovieSelection userRatingNot(String... value) {
        addNotEquals(MovieColumns.USER_RATING, value);
        return this;
    }

    public MovieSelection userRatingLike(String... value) {
        addLike(MovieColumns.USER_RATING, value);
        return this;
    }

    public MovieSelection userRatingContains(String... value) {
        addContains(MovieColumns.USER_RATING, value);
        return this;
    }

    public MovieSelection userRatingStartsWith(String... value) {
        addStartsWith(MovieColumns.USER_RATING, value);
        return this;
    }

    public MovieSelection userRatingEndsWith(String... value) {
        addEndsWith(MovieColumns.USER_RATING, value);
        return this;
    }

    public MovieSelection orderByUserRating(boolean desc) {
        orderBy(MovieColumns.USER_RATING, desc);
        return this;
    }

    public MovieSelection orderByUserRating() {
        orderBy(MovieColumns.USER_RATING, false);
        return this;
    }
}
