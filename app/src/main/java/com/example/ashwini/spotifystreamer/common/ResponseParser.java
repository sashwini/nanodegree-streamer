package com.example.ashwini.spotifystreamer.common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ashwini on 2/4/2016.
 */
public class ResponseParser {

    public static ArrayList<Movie> parseMovieList(String response) {

        ArrayList<Movie> movieList = new ArrayList<>();

        try {
            JSONObject responseObject = new JSONObject(response);
            JSONArray resultArray = responseObject.getJSONArray("results");
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject movieObject = resultArray.getJSONObject(i);
                Movie movie = new Movie();
                movie.setPosterPath(movieObject.getString("poster_path"));
                movie.setOriginalTitle(movieObject.getString("original_title"));
                movie.setPlotSynopsis(movieObject.getString("overview"));
                movie.setReleaseDate(movieObject.getString("release_date"));
                movie.setUserRating(movieObject.getDouble("vote_average"));
                movie.setMovieId(movieObject.getInt("id"));
                movieList.add(movie);
            }
        } catch (JSONException je) {
            je.printStackTrace();
        }

        return movieList;
    }

    public static ArrayList<Trailer> parseMovieTrailers(String response) {

        ArrayList<Trailer> trailers = new ArrayList<>();

        try {
            JSONObject responseObject = new JSONObject(response);
            JSONArray resultArray = responseObject.getJSONArray("results");
            int movieId = responseObject.getInt("id");
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject trailerObject = resultArray.getJSONObject(i);
                Trailer trailer = new Trailer();
                trailer.setMovieId(movieId);
                trailer.setKey(trailerObject.getString("key"));
                trailer.setName(trailerObject.getString("name"));
                trailers.add(trailer);
            }
        } catch (JSONException je) {
            je.printStackTrace();
        }

        return trailers;
    }

    public static ArrayList<Review> parseReviewList(String response) {

        ArrayList<Review> reviews = new ArrayList<>();

        try {
            JSONObject responseObject = new JSONObject(response);
            JSONArray resultArray = responseObject.getJSONArray("results");
            int movieId = responseObject.getInt("id");
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject reviewObject = resultArray.getJSONObject(i);
                Review review = new Review();
                review.setReviewId(reviewObject.getString("id"));
                review.setMovieId(movieId);
                review.setAuthor(reviewObject.getString("author"));
                review.setContent(reviewObject.getString("content"));
                reviews.add(review);
            }
        } catch (JSONException je) {
            je.printStackTrace();
        }

        return reviews;
    }

    //TODO: parse movie details
    public static Movie parseMovieDetails(String response) {

        try {
            JSONObject movieObject = new JSONObject(response);
            Movie movie = new Movie();
            movie.setPosterPath(movieObject.getString("poster_path"));
            movie.setOriginalTitle(movieObject.getString("original_title"));
            movie.setPlotSynopsis(movieObject.getString("overview"));
            movie.setReleaseDate(movieObject.getString("release_date"));
            movie.setUserRating(movieObject.getDouble("vote_average"));
            movie.setMovieId(movieObject.getInt("id"));
            return movie;
        } catch (JSONException je) {
            je.printStackTrace();
        }

        return null;
    }

}
