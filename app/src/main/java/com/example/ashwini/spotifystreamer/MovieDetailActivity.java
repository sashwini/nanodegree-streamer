package com.example.ashwini.spotifystreamer;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.ashwini.spotifystreamer.common.Movie;

/**
 * Created by Ashwini on 2/4/2016.
 */
public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.detail_fragment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Bundle bundle = getIntent().getBundleExtra("bundle");
        Movie movie = bundle.getParcelable("movie");

        FragmentManager fragmentManager = getFragmentManager();
        DetailFragment detailFragment = (DetailFragment)fragmentManager.findFragmentById(
                R.id.detailFragmentLayout);
        detailFragment.setMovie(movie);

    }

}
