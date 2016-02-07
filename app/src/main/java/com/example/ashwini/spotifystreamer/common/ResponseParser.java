package com.example.ashwini.spotifystreamer.common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ashwini on 2/4/2016.
 */
public class ResponseParser {

    public ArrayList<Movie> parseMovieList(String response) {

        ArrayList<Movie> movieList = new ArrayList<Movie>();

        try {
            JSONObject responseObject = new JSONObject(response);
            int pageNumber = responseObject.getInt("page");
            JSONArray resultArray = responseObject.getJSONArray("results");
            for (int i=0; i< resultArray.length(); i++) {
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
        }
        catch (JSONException je){
            je.printStackTrace();
        }

        return movieList;
    }
}
