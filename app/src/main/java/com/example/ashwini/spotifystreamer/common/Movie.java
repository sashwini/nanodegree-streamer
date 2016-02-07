package com.example.ashwini.spotifystreamer.common;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Ashwini on 2/4/2016.
 */
public class Movie implements Serializable{

    private String posterPath;
    private String originalTitle;
    private String plotSynopsis;
    private double userRating;
    private String releaseDate;

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    private int movieId;

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
}
