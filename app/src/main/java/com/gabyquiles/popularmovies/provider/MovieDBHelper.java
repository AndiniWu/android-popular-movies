package com.gabyquiles.popularmovies.provider;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.gabyquiles.popularmovies.BuildConfig;
import com.gabyquiles.popularmovies.provider.movie.MovieColumns;

public class MovieDBHelper extends SQLiteOpenHelper {
    private static final String TAG = MovieDBHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "popularmovies.db";
    private static final int DATABASE_VERSION = 1;
    private static MovieDBHelper sInstance;
    private final Context mContext;
    private final MovieDBHelperCallbacks mOpenHelperCallbacks;

    // @formatter:off
    public static final String SQL_CREATE_TABLE_MOVIE = "CREATE TABLE IF NOT EXISTS "
            + MovieColumns.TABLE_NAME + " ( "
            + MovieColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MovieColumns.MOVIEDB_ID + " INTEGER NOT NULL, "
            + MovieColumns.TITLE + " TEXT, "
            + MovieColumns.RELEASE_DATE + " TEXT, "
            + MovieColumns.SYNOPSIS + " TEXT, "
            + MovieColumns.POSTER_PATH + " TEXT, "
            + MovieColumns.USER_RATING + " TEXT "
            + ", CONSTRAINT unique_moviedb_id UNIQUE (moviedb_id) ON CONFLICT REPLACE"
            + " );";

    public static final String SQL_CREATE_INDEX_MOVIE_MOVIEDB_ID = "CREATE INDEX IDX_MOVIE_MOVIEDB_ID "
            + " ON " + MovieColumns.TABLE_NAME + " ( " + MovieColumns.MOVIEDB_ID + " );";

    // @formatter:on

    public static MovieDBHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static MovieDBHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */
    private static MovieDBHelper newInstancePreHoneycomb(Context context) {
        return new MovieDBHelper(context);
    }

    private MovieDBHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        mContext = context;
        mOpenHelperCallbacks = new MovieDBHelperCallbacks();
    }


    /*
     * Post Honeycomb.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static MovieDBHelper newInstancePostHoneycomb(Context context) {
        return new MovieDBHelper(context, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private MovieDBHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new MovieDBHelperCallbacks();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
        db.execSQL(SQL_CREATE_INDEX_MOVIE_MOVIEDB_ID);
        mOpenHelperCallbacks.onPostCreate(mContext, db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            setForeignKeyConstraintsEnabled(db);
        }
        mOpenHelperCallbacks.onOpen(mContext, db);
    }

    private void setForeignKeyConstraintsEnabled(SQLiteDatabase db) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            setForeignKeyConstraintsEnabledPreJellyBean(db);
        } else {
            setForeignKeyConstraintsEnabledPostJellyBean(db);
        }
    }

    private void setForeignKeyConstraintsEnabledPreJellyBean(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setForeignKeyConstraintsEnabledPostJellyBean(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mOpenHelperCallbacks.onUpgrade(mContext, db, oldVersion, newVersion);
    }
}
