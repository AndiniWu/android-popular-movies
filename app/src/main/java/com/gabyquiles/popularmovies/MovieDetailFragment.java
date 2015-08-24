package com.gabyquiles.popularmovies;

import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gabyquiles.popularmovies.api.MovieDBService;
import com.gabyquiles.popularmovies.models.Movie;
import com.gabyquiles.popularmovies.models.Page;
import com.gabyquiles.popularmovies.models.Trailer;
import com.gabyquiles.popularmovies.provider.movie.MovieContentValues;
import com.gabyquiles.popularmovies.provider.movie.MovieSelection;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailFragment extends Fragment {
    private final String LOG_TAG = MovieDetailFragment.class.getSimpleName();
    public static final String DETAILED_MOVIE = "detailed_movie";
    private MovieDBService mMovieService;
    private ArrayAdapter<String> mAdapter;
    private ArrayList<Trailer> mTrailers = new ArrayList<>();
    private Movie mMovie;

    private ImageView mPosterView;
    private TextView mRatingsView;
    private TextView mTitleView;
    private TextView mSynopsisView;
    private TextView mDateView;
    private ListView mTrailersListView;
    private ImageButton mFavoriteButton;


    public MovieDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(getString(R.string.moviedb_api_url)).build();
        mMovieService = restAdapter.create(MovieDBService.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mMovie = arguments.getParcelable(MovieDetailFragment.DETAILED_MOVIE);
        }

        mAdapter = new ArrayAdapter<>(getActivity(), R.layout.trailer_list_item, R.id.text1,
                new ArrayList<String>());

        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        if(mMovie != null) {

            fillMovieDetails(rootView, mMovie);

            mMovieService.getTrailers(mMovie.getId(), getString(R.string.moviedb_api_key), new Callback<Page<Trailer>>() {
                @Override
                public void success(Page<Trailer> trailerPage, Response response) {
                    Log.v(LOG_TAG, "Success");
                    if (trailerPage != null && !trailerPage.isEmpty()) {
                        mAdapter.clear();
                        mTrailers = (ArrayList<Trailer>) trailerPage.getResults();
                        for (Trailer trailer: mTrailers) {
                            mAdapter.add(trailer.getName());
                            Log.v(LOG_TAG, trailer.getName());
                        }
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.v(LOG_TAG, "Failed: " + error.getMessage());
                }
            });
        }

        return rootView;
    }

    private void fillMovieDetails(View rootView, final Movie movie) {
        mTitleView = (TextView) rootView.findViewById(R.id.textView_movie_title);
        mTitleView.setText(movie.getTitle());

        mPosterView = (ImageView) rootView.findViewById(R.id.imageView_movie_poster);
        Picasso.with(getActivity().getBaseContext()).load(movie.getPosterURI("w500")).into(mPosterView);

        mRatingsView = (TextView) rootView.findViewById(R.id.textView_ratings);
        mRatingsView.setText(movie.getUserRating());

        mSynopsisView = (TextView) rootView.findViewById(R.id.textView_synopsis);
        mSynopsisView.setText(movie.getSynopsis());

        mDateView = (TextView) rootView.findViewById(R.id.textView_year);
        mDateView.setText(movie.getReleaseDate());

        // Favorit
        mFavoriteButton = (ImageButton) rootView.findViewById(R.id.favorite_imageButton);
        this.setFavoriteButtonImage(movie.isFavorited());
        mFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movie.setFavorited(!movie.isFavorited());
                if(movie.isFavorited()) {
                    saveMovieFavorite(movie);
                } else {
                    removeMovieFavorie(movie);
                }
                setFavoriteButtonImage(movie.isFavorited());
            }
        });

        // Trailers
        mTrailersListView = (ListView) rootView.findViewById(R.id.trailers_listView);
        mTrailersListView.setAdapter(mAdapter);
        mTrailersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Trailer trailer = mTrailers.get(position);
                getTrailerIntent(trailer.getKey());
            }
        });

        // Reviews
    }

    private void getTrailerIntent(String id) {
        try{
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
            startActivity(intent);
        }catch (ActivityNotFoundException ex){
            Intent intent=new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v="+id));
            startActivity(intent);
        }
    }

    private long saveMovieFavorite(Movie movie) {
        MovieContentValues values = new MovieContentValues();
        values.putMoviedbId(movie.getId());
        values.putPosterPath(movie.getPosterPath());
        values.putReleaseDate(movie.getReleaseDate());
        values.putSynopsis(movie.getSynopsis());
        values.putTitle(movie.getTitle());
        values.putUserRating(movie.getUserRating());

        Uri uri = values.insert(getActivity().getContentResolver());

        return ContentUris.parseId(uri);
    }

    private void removeMovieFavorie(Movie movie) {
        MovieSelection dbMovie = new MovieSelection();
        dbMovie.moviedbId(movie.getId());
        dbMovie.delete(getActivity().getContentResolver());
    }

    private void setFavoriteButtonImage(boolean isFavorite) {
        if(isFavorite) {
            mFavoriteButton.setImageResource(R.drawable.ic_action_star_10);
        } else {
            mFavoriteButton.setImageResource(R.drawable.ic_action_star_0);
        }
    }
}
