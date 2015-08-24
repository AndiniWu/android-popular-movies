package com.gabyquiles.popularmovies.provider.movie;

import android.net.Uri;
import android.provider.BaseColumns;

import com.gabyquiles.popularmovies.provider.MovieProvider;
import com.gabyquiles.popularmovies.provider.movie.MovieColumns;

/**
 * Columns for the {@code movie} table.
 */
public class MovieColumns implements BaseColumns {
    public static final String TABLE_NAME = "movie";
    public static final Uri CONTENT_URI = Uri.parse(MovieProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    /**
     * Id in MovieDB
     */
    public static final String MOVIEDB_ID = "moviedb_id";

    /**
     * Title of the movie.
     */
    public static final String TITLE = "title";

    /**
     * Date in which the movie will be released.
     */
    public static final String RELEASE_DATE = "release_date";

    public static final String SYNOPSIS = "synopsis";

    public static final String POSTER_PATH = "poster_path";

    public static final String USER_RATING = "user_rating";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            MOVIEDB_ID,
            TITLE,
            RELEASE_DATE,
            SYNOPSIS,
            POSTER_PATH,
            USER_RATING
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(MOVIEDB_ID) || c.contains("." + MOVIEDB_ID)) return true;
            if (c.equals(TITLE) || c.contains("." + TITLE)) return true;
            if (c.equals(RELEASE_DATE) || c.contains("." + RELEASE_DATE)) return true;
            if (c.equals(SYNOPSIS) || c.contains("." + SYNOPSIS)) return true;
            if (c.equals(POSTER_PATH) || c.contains("." + POSTER_PATH)) return true;
            if (c.equals(USER_RATING) || c.contains("." + USER_RATING)) return true;
        }
        return false;
    }

}
