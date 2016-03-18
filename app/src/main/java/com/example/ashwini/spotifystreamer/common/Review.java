package com.example.ashwini.spotifystreamer.common;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ashwini on 2/25/2016.
 */
public class Review implements Parcelable{

    private int movieId;
    private String reviewId;
    private String author;
    private String content;

    public static final Parcelable.Creator<Review> CREATOR = new Parcelable.Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel source) {
            Review review = new Review();
            review.setMovieId(source.readInt());
            review.setReviewId(source.readString());
            review.setAuthor(source.readString());
            review.setContent(source.readString());
            return review;
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    @Override
    public int describeContents() {
        return Integer.parseInt(reviewId.substring(reviewId.length()-4, reviewId.length()));
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(movieId);
        dest.writeString(reviewId);
        dest.writeString(author);
        dest.writeString(content);
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
