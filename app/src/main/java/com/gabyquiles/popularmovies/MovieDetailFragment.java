package com.gabyquiles.popularmovies;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gabyquiles.popularmovies.models.Movie;
import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailFragment extends Fragment {

    public MovieDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        Intent intent = getActivity().getIntent();

        if(intent != null && intent.hasExtra(MainActivity.MOVIE_DATA)) {

            Movie movie = getActivity().getIntent().getExtras().getParcelable(MainActivity.MOVIE_DATA);

            if(movie != null) {
                TextView titleView = (TextView) rootView.findViewById(R.id.textView_movie_title);
                titleView.setText(movie.getTitle());

                ImageView posterView = (ImageView) rootView.findViewById(R.id.imageView_movie_poster);
                Picasso.with(getActivity().getBaseContext()).load(movie.getPosterURI("w500")).into(posterView);

                TextView ratingsView = (TextView) rootView.findViewById(R.id.textView_ratings);
                ratingsView.setText(movie.getUserRating());

                TextView synopsisView = (TextView) rootView.findViewById(R.id.textView_synopsis);
                synopsisView.setText(movie.getSynopsis());

                TextView dateView = (TextView) rootView.findViewById(R.id.textView_year);
                dateView.setText(movie.getReleaseDate());
            }
        }

        return rootView;
    }
}
