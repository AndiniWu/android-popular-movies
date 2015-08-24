package com.gabyquiles.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

/**
 * Created by gabrielquiles-perez on 8/21/15.
 */
public class Trailer implements Parcelable {
    @Expose
    private String id;
    @Expose
    private String key;
    @Expose
    private String name;
    @Expose
    private String site;


    private Trailer(Parcel in) {
        this.id = in.readString();
        this.key = in.readString();
        this.name = in.readString();
        this.site = in.readString();
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getSite() {
        return site;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int position) {
        parcel.writeString(id);
        parcel.writeString(key);
        parcel.writeString(name);
        parcel.writeString(site);
    }

    public final static Parcelable.Creator<Trailer> CREATOR = new Parcelable.Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel parcel) { return new Trailer(parcel); }
        @Override
        public Trailer[] newArray(int i) { return new Trailer[i]; }
    };
}
