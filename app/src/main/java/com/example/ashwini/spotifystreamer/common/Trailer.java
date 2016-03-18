package com.example.ashwini.spotifystreamer.common;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ashwini on 2/25/2016.
 */
public class Trailer implements Parcelable{

    private int movieId;
    private int trailerId;
    private String key;
    private String name;

    public static final Creator<Trailer> CREATOR = new Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel source) {
            Trailer review = new Trailer();
            review.setMovieId(source.readInt());
            review.setTrailerId(source.readInt());
            review.setKey(source.readString());
            review.setName(source.readString());
            return review;
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    @Override
    public int describeContents() {
        return trailerId;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(movieId);
        dest.writeInt(trailerId);
        dest.writeString(key);
        dest.writeString(name);
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getTrailerId() {
        return trailerId;
    }

    public void setTrailerId(int trailerId) {
        this.trailerId = trailerId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideoUrl() {
        //https://www.themoviedb.org/talk/5451ec02c3a3680245005e3c
        //Referring to above link videos are assumed to be Youtube videos only
        return "https://www.youtube.com/watch?v=" + getKey();
    }
}
