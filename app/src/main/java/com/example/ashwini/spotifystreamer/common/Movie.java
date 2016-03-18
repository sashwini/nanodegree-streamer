package com.example.ashwini.spotifystreamer.common;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ashwini on 2/4/2016.
 */
public class Movie implements Parcelable{

    private int movieId;
    private String posterPath;
    private String originalTitle;
    private String plotSynopsis;
    private double userRating;
    private String releaseDate;

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            Movie movie = new Movie();
            movie.setMovieId(source.readInt());
            movie.setPosterPath(source.readString());
            movie.setOriginalTitle(source.readString());
            movie.setPlotSynopsis(source.readString());
            movie.setUserRating(source.readDouble());
            movie.setReleaseDate(source.readString());
            return movie;
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public void setPlotSynopsis(String plotSynopsis) {
        this.plotSynopsis = plotSynopsis;
    }

    public double getUserRating() {
        return userRating;
    }

    public void setUserRating(double userRating) {
        this.userRating = userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getPosterUrl() {
        return "http://image.tmdb.org/t/p/w185/" + posterPath;
    }

    @Override
    public int describeContents() {
        return movieId;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(movieId);
        dest.writeString(posterPath);
        dest.writeString(originalTitle);
        dest.writeString(plotSynopsis);
        dest.writeDouble(userRating);
        dest.writeString(releaseDate);
    }
}
