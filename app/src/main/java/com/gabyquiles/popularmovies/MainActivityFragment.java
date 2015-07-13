package com.gabyquiles.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.gabyquiles.popularmovies.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    private final String LOG_TAG = MainActivityFragment.class.getSimpleName();
    private final String PARCELABLE_MOVIES_KEY = "movie";
    private final String PARCELABLE_SORTING_KEY = "sorting";
    protected ThumbnailAdapter adapter;
    private ArrayList<Movie> list = new ArrayList<>();
    private String sortOrder = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(PARCELABLE_MOVIES_KEY)) {
            list = savedInstanceState.getParcelableArrayList(PARCELABLE_MOVIES_KEY);
            sortOrder = savedInstanceState.getString(PARCELABLE_SORTING_KEY);
        }
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
        if(sortOrder != newSortOrder) {
            sortOrder = newSortOrder;
            FetchMoviesTask task = new FetchMoviesTask();
            task.execute(sortOrder);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        adapter = new ThumbnailAdapter(getActivity(), R.layout.movie_grid_cell, list);

        View rootView = inflater.inflate(R.layout.fragment_main, container);

        GridView list = (GridView) rootView.findViewById(R.id.gridview_movies);

        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

        return rootView;
    }

    public class FetchMoviesTask extends AsyncTask<String , Void, Movie[]> {
        private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();

        @Override
        protected Movie[] doInBackground(String... params) {
            //If no param there is nothing to get
            if(params.length == 0) {
                return null;
            }

            String sorting = params[0];

            String apiKey = "bee86948b9e4fac93a62b0c5afe7ad27";

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String moviesJsonStr = "";

            try {
                // Construct the URL for The Movie DB query
                Uri.Builder builder = new Uri.Builder();
                builder.scheme("http")
                        .authority("api.themoviedb.org")
                        .appendPath("3")
                        .appendPath("discover")
                        .appendPath("movie")
                        .appendQueryParameter("sort_by", sorting)
                        .appendQueryParameter("api_key", apiKey);

                String urlString = builder.build().toString();
                URL url = new URL(urlString);
                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                moviesJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e(this.LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(this.LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return postersFromJSon(moviesJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
            return null;
        }

        private Movie[] postersFromJSon(String moviesJson) throws JSONException {
            // These are the names of the JSON objects that need to be extracted.
            final String TMDB_LIST = "results";
            final String TMDB_ID = "id";
            final String TMDB_TITLE = "original_title";
            final String TMDB_POSTER = "poster_path";
            final String TMDB_SYNOPSIS = "overview";
            final String TMDB_USER_RATING = "vote_average";
            final String TMDB_RELEASE_DATE = "release_date";

            JSONObject moviesObj = new JSONObject(moviesJson);
            JSONArray moviesArray = moviesObj.getJSONArray(TMDB_LIST);

            Movie[] movies = new Movie[moviesArray.length()];

            for(int i = 0; i < moviesArray.length(); i++) {
                JSONObject movie = moviesArray.getJSONObject(i);
                Long id = movie.getLong(TMDB_ID);
                String title = movie.getString(TMDB_TITLE);
                String posterPath = movie.getString(TMDB_POSTER);
                String synopsis = movie.getString(TMDB_SYNOPSIS);
                String userRating = movie.getString(TMDB_USER_RATING);
                String releaseDate = movie.getString(TMDB_RELEASE_DATE);
                movies[i] = new Movie(id, title, posterPath, synopsis, userRating, releaseDate);
            }

            return movies;
        }

        @Override
        protected void onPostExecute(Movie[] result) {
            if(result != null) {
                adapter.clear();
                adapter.addAll(result);
            }
        }
    }
}
