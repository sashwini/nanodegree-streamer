package com.example.ashwini.spotifystreamer.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Ashwini on 2/3/2016.
 */
public class MovieApiHelper {

    //replace the below with your own key
    private static final String API_KEY = "52a0c6535aa03bf8e70defc4c4bbf634";
    private static final String GET_MOVIE_URL = "http://api.themoviedb.org/3/movie/";
    private static final String POPULAR_MOVIE_URL = "http://api.themoviedb.org/3/movie/popular?page=";
    private static final String HIGH_RATED_MOVIE_URL = "http://api.themoviedb.org/3/movie/top_rated?page=";
    private static final String MOVIE_VIDEOS_URL = "http://api.themoviedb.org/3/movie/$/videos";
    private static final String MOVIE_REVIEWS_URL = "http://api.themoviedb.org/3/movie/$/reviews";

    public static String getPopularMovies(int page) {
        String response = "";
        try {
            String requestUrl = POPULAR_MOVIE_URL + String.valueOf(page);
            URL url = new URL(getFormedUrl(requestUrl));
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setDoInput(true);
            InputStream in = urlConnection.getInputStream();
            response = readResponse(in);
            urlConnection.disconnect();
        } catch (IOException e){
            e.printStackTrace();
        }

        return response;
    }

    public static String getRatedMovies(int page) {
        String response = "";
        try {
            String requestUrl = HIGH_RATED_MOVIE_URL + String.valueOf(page);
            URL url = new URL(getFormedUrl(requestUrl));
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setDoInput(true);
            InputStream in = urlConnection.getInputStream();
            response = readResponse(in);
            urlConnection.disconnect();
        } catch (IOException e){
            e.printStackTrace();
        }

        return response;
    }

    public static String getMovieTrailers(int movieId) {
        String response = "";
        try {
            String requestUrl = MOVIE_VIDEOS_URL.replace("$", String.valueOf(movieId));
            URL url = new URL(getFormedUrl(requestUrl));
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setDoInput(true);
            InputStream in = urlConnection.getInputStream();
            response = readResponse(in);
            urlConnection.disconnect();
        } catch (IOException e){
            e.printStackTrace();
        }

        return response;
    }

    public static String getMovieReviews(int movieId) {
        String response = "";
        try {
            String requestUrl = MOVIE_REVIEWS_URL.replace("$", String.valueOf(movieId));
            URL url = new URL(getFormedUrl(requestUrl));
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setDoInput(true);
            InputStream in = urlConnection.getInputStream();
            response = readResponse(in);
            urlConnection.disconnect();
        } catch (IOException e){
            e.printStackTrace();
        }

        return response;
    }

    //TODO: get particular movie details
    public static String getMovieDetails(int movieId) {
        String response = "";
        try {
            String requestUrl = GET_MOVIE_URL + String.valueOf(movieId);
            URL url = new URL(getFormedUrl(requestUrl));
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setDoInput(true);
            InputStream in = urlConnection.getInputStream();
            response = readResponse(in);
            urlConnection.disconnect();
        } catch (IOException e){
            e.printStackTrace();
        }

        return response;
    }

    private static String readResponse(InputStream inputStream) {

        StringBuilder responseBuffer = new StringBuilder();
        try {
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = streamReader.readLine()) != null){
                responseBuffer.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseBuffer.toString();
    }

    private static String getFormedUrl(String baseUrl) {

        return baseUrl.contains("?") ?
                baseUrl + "&api_key=" + API_KEY :
                baseUrl + "?api_key=" + API_KEY;
    }
}
