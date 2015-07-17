package com.gabyquiles.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.gabyquiles.popularmovies.api.MovieDBService;
import com.gabyquiles.popularmovies.models.Movie;
import com.gabyquiles.popularmovies.models.MoviePage;

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
    private final String API_URL = "http://api.themoviedb.org/3";
    private final String API_KEY = "bee86948b9e4fac93a62b0c5afe7ad27";
    protected ThumbnailAdapter adapter;
    private ArrayList<Movie> list = new ArrayList<>();
    private String sortOrder = "";
    private MovieDBService movieService;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Restore app if it was previousy opened
        if (savedInstanceState != null && savedInstanceState.containsKey(PARCELABLE_MOVIES_KEY)) {
            list = savedInstanceState.getParcelableArrayList(PARCELABLE_MOVIES_KEY);
            sortOrder = savedInstanceState.getString(PARCELABLE_SORTING_KEY);
        }

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(API_URL).build();
        movieService = restAdapter.create(MovieDBService.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    @Override
    public void onSaveInstanceState(Bundle instanceState) {
        instanceState.putParcelableArrayList(PARCELABLE_MOVIES_KEY, list);
        instanceState.putString(PARCELABLE_SORTING_KEY, sortOrder);
        super.onSaveInstanceState(instanceState);
    }

    private void updateMovies() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String newSortOrder = sharedPreferences.getString(
                getString(R.string.pref_sort_order_key),
                getString(R.string.pref_sort_order_default));
        // ReFetch information only if sorting order has changed
        if(!sortOrder.equals(newSortOrder)) {
            sortOrder = newSortOrder;
            movieService.getMovies(sortOrder, API_KEY,new Callback<MoviePage>() {
                @Override
                public void success(MoviePage movies, Response response) {
                    if(movies != null && !movies.isEmpty()) {
                        adapter.clear();
                        adapter.addAll(movies.getMovies());
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    int i = 2;
                }

            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        adapter = new ThumbnailAdapter(getActivity(), R.layout.movie_grid_cell, list);

        View rootView = inflater.inflate(R.layout.fragment_main, container);

        GridView gridView = (GridView) rootView.findViewById(R.id.gridview_movies);

        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Context context = getActivity();
                Movie selected_movie = adapter.getItem(position);
                Log.v(LOG_TAG, selected_movie.getTitle());
                Intent intent = new Intent(context, MovieDetail.class);
                intent.putExtra(MainActivity.MOVIE_DATA, selected_movie);
                context.startActivity(intent);
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
}
