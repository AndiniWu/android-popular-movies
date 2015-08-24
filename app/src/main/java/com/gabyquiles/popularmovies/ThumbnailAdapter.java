/**
 * Created by gabrielquiles-perez on 7/12/15.
 *
 * This adapter receives a Movie object and display its poster.
 */
package com.gabyquiles.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.gabyquiles.popularmovies.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ThumbnailAdapter extends ArrayAdapter<Movie> {
    private int adapterLayout;

    public ThumbnailAdapter(Activity context, int viewResourceId, List<Movie> list) {
        super(context,viewResourceId,list);
        this.adapterLayout = viewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this.adapterLayout, null);
        }

        String posterSize = "w500";

        Movie movie = this.getItem(position);
        if(movie != null) {
            ImageView poster = (ImageView) convertView.findViewById(R.id.imageView_poster);
            Picasso.with(getContext()).load(movie.getPosterURI(posterSize)).into(poster);
        }
        return convertView;
    }
}
