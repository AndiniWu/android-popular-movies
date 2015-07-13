/**
 * Created by gabrielquiles-perez on 7/12/15.
 *
 * Represents a movie. Includes all the information to be displayed.
 */
package com.gabyquiles.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable{
    private Long id;
    private String posterPath;
    private String title;
    private String synopsis;
    private String userRating;
    private String releaseDate;

    public Movie(Long id, String title, String posterPath, String synopsis, String userRating, String releaseDate) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.synopsis = synopsis;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
    }

    private Movie(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.posterPath = in.readString();
        this.synopsis = in.readString();
        this.userRating = in.readString();
        this.releaseDate = in.readString();
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getUserRating() {
        return userRating + "/10";
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPosterURI(String size) {
        return "http://image.tmdb.org/t/p/" + size + "/" + this.posterPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int position) {
        parcel.writeLong(id);
        parcel.writeString(title);
        parcel.writeString(posterPath);
        parcel.writeString(synopsis);
        parcel.writeString(userRating);
        parcel.writeString(releaseDate);
    }

    public final static Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) { return new Movie(parcel); }
        @Override
        public Movie[] newArray(int i) { return new Movie[i]; }
    };
}
