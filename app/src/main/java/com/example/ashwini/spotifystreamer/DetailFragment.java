package com.example.ashwini.spotifystreamer;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ashwini.spotifystreamer.common.Movie;
import com.example.ashwini.spotifystreamer.common.MovieApiHelper;
import com.example.ashwini.spotifystreamer.common.ResponseParser;
import com.example.ashwini.spotifystreamer.common.Review;
import com.example.ashwini.spotifystreamer.common.Trailer;

import java.util.ArrayList;

/**
 * Created by Ashwini on 2/22/2016.
 */
public class DetailFragment extends Fragment{

    View contentView;
    private Movie movie;
    ArrayList<Trailer> trailerList;
    ArrayList<Review> reviewList;

    RecyclerView recyclerView;
    DetailViewAdapter detailViewAdapter;

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
        updateScreenDetails();
    }

    private void updateScreenDetails() {
        recyclerView = (RecyclerView)contentView.findViewById(R.id.recycler_view);

        detailViewAdapter = new DetailViewAdapter(getActivity(), movie);
        recyclerView.setAdapter(detailViewAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        //load trailers & reviews
        fetchTrailers();
        fetchReviews();
    }

    private void fetchTrailers() {

        final Runnable uiUpdater = new Runnable() {
            @Override
            public void run() {
                detailViewAdapter.setTrailers(trailerList);
            }
        };

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String response =  MovieApiHelper.getMovieTrailers(movie.getMovieId());
                trailerList = ResponseParser.parseMovieTrailers(response);
                getActivity().runOnUiThread(uiUpdater);
            }
        };

        new Thread(runnable).start();

    }

    private void fetchReviews() {

        final Runnable uiUpdater = new Runnable() {
            @Override
            public void run() {
                detailViewAdapter.setReviews(reviewList);
            }
        };

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String response =  MovieApiHelper.getMovieReviews(movie.getMovieId());
                reviewList = ResponseParser.parseReviewList(response);
                getActivity().runOnUiThread(uiUpdater);
            }
        };

        new Thread(runnable).start();

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        contentView = inflater.inflate(R.layout.activity_detail, container, false);
        return contentView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_share_trailer:
                if(trailerList.size() > 0) {
                    Trailer trailer = trailerList.get(0);
                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("text/plain");
                    share.putExtra(Intent.EXTRA_TEXT, trailer.getVideoUrl());
                    startActivity(Intent.createChooser(share, getString(R.string.share_trailer)));
                }
                else {
                    Toast.makeText(getActivity(), R.string.no_trailer_message,
                            Toast.LENGTH_LONG).show();
                }
                return true;
        }
        return false;
    }
}
