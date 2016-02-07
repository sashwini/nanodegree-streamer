package com.example.ashwini.spotifystreamer.common;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by Ashwini on 2/3/2016.
 */
public class MovieApiHelper {

    //replace the below with your own key
    private static final String API_KEY = "YOUR_API_KEY";
    private static final String POPULAR_MOVIE_URL = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&page=";
    private static final String HIGH_RATED_MOVIE_URL = "http://api.themoviedb.org/3/discover/movie?sort_by=vote_average.desc&page=";

    public String getPopularMovies(int page) {
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
        }
        catch (MalformedURLException me) {
            me.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return response;
    }

    public String getRatedMovies(int page) {
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
        }
        catch (MalformedURLException me) {
            me.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return response;
    }

    private String readResponse(InputStream inputStream) {

        StringBuilder responseBuffer = new StringBuilder();
        try {
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = streamReader.readLine()) != null){
                responseBuffer.append(line);
            }
            System.out.println(responseBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseBuffer.toString();
    }

    private String getFormedUrl(String baseUrl) {
        return baseUrl + "&api_key=" + API_KEY;
    }
}
