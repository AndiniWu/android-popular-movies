package com.gabyquiles.popularmovies;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.gabyquiles.popularmovies.api.MovieDBService;
import com.gabyquiles.popularmovies.models.Movie;
import com.gabyquiles.popularmovies.models.Page;
import com.gabyquiles.popularmovies.provider.movie.MovieColumns;
import com.gabyquiles.popularmovies.provider.movie.MovieCursor;
import com.gabyquiles.popularmovies.provider.movie.MovieSelection;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    private final String LOG_TAG = MainActivityFragment.class.getSimpleName();
    //Used to store app state
    private final String PARCELABLE_MOVIES_KEY = "movie";
    private final String PARCELABLE_SORTING_KEY = "sorting";
    protected ThumbnailAdapter mAdapter;
    private ArrayList<Movie> mList = new ArrayList<>();
    private String mSortOrder = "";
    private MovieDBService mMovieService;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Restore app if it was previousy opened
        if (savedInstanceState != null && savedInstanceState.containsKey(PARCELABLE_MOVIES_KEY)) {
            mList = savedInstanceState.getParcelableArrayList(PARCELABLE_MOVIES_KEY);
            mSortOrder = savedInstanceState.getString(PARCELABLE_SORTING_KEY);
        }

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(getString(R.string.moviedb_api_url)).build();
        mMovieService = restAdapter.create(MovieDBService.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    @Override
    public void onSaveInstanceState(Bundle instanceState) {
        instanceState.putParcelableArrayList(PARCELABLE_MOVIES_KEY, mList);
        instanceState.putString(PARCELABLE_SORTING_KEY, mSortOrder);
        super.onSaveInstanceState(instanceState);
    }

    private void updateMovies() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String newSortOrder = sharedPreferences.getString(
                getString(R.string.pref_sort_order_key),
                getString(R.string.pref_sort_order_default));
        // ReFetch information only if sorting order has changed
        if(!mSortOrder.equals(newSortOrder)) {
            mSortOrder = newSortOrder;
            if(mSortOrder == getString(R.string.pref_sort_order_favorite_value)) {
                mAdapter.clear();
                MovieSelection movies = new MovieSelection();
                movies.orderByMoviedbId();
                MovieCursor cursor = movies.query(getActivity().getContentResolver());
                while (cursor.moveToNext()) {
                    mAdapter.add(new Movie(cursor));
                }
            } else {
                mMovieService.getMovies(mSortOrder, getString(R.string.moviedb_api_key), new Callback<Page<Movie>>() {
                    @Override
                    public void success(Page<Movie> movies, Response response) {
                        if (movies != null && !movies.isEmpty()) {
                            mAdapter.clear();
                            mAdapter.addAll(movies.getResults());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        int i = 2;
                    }

                });
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mAdapter = new ThumbnailAdapter(getActivity(), R.layout.movie_grid_cell, mList);

        View rootView = inflater.inflate(R.layout.fragment_main, container);

        GridView gridView = (GridView) rootView.findViewById(R.id.gridview_movies);

        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie selected_movie = mAdapter.getItem(position);

                String[] db_columns = { MovieColumns.MOVIEDB_ID };
                MovieSelection movie_db = new MovieSelection();
                movie_db.moviedbId(selected_movie.getId());
                MovieCursor cursor = movie_db.query(getActivity().getContentResolver(), db_columns);
                if(cursor.getCount() > 0) {
                    selected_movie.setFavorited(true);
                }
                cursor.close();
                ((MainActivity) getActivity()).onItemSelected(selected_movie);
            }
        });

        int orientation = getResources().getConfiguration().orientation;
        int numColumns = GridView.AUTO_FIT;
        if ( orientation == Configuration.ORIENTATION_LANDSCAPE ) {
            numColumns = 3;
        }

        gridView.setNumColumns(numColumns);

        return rootView;
    }

    // This transmits clicks on grid items to activity
    public interface SelectionCallback {
        void onItemSelected(Movie selected_movie);
    }
}
