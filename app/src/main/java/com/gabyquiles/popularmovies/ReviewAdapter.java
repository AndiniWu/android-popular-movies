package com.gabyquiles.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gabyquiles.popularmovies.models.Review;

import java.util.List;

/**
 * Created by gabrielquiles-perez on 8/24/15.
 */
public class ReviewAdapter extends ArrayAdapter<Review>{
    private int mAdapterLayout;

    public ReviewAdapter(Activity context, int viewResourceId, List<Review> list) {
        super(context,viewResourceId,list);
        mAdapterLayout = viewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mAdapterLayout, null);
        }

        Review review = getItem(position);
        if(review != null) {

            TextView authorView = (TextView) convertView.findViewById(R.id.author_textView);
            authorView.setText(review.getAuthor());

            TextView contentView = (TextView) convertView.findViewById(R.id.content_textView);
            contentView.setText(review.getContent());
        }
        return convertView;
    }

    public static class ViewHolder {
        public final TextView authorView;
        public final TextView contentView;

        public ViewHolder(View view) {
            authorView = (TextView) view.findViewById(R.id.author_textView);
            contentView = (TextView) view.findViewById(R.id.content_textView);
        }
    }
}
