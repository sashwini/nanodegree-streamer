package com.example.ashwini.spotifystreamer;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.ashwini.spotifystreamer.common.Movie;
import com.squareup.picasso.Picasso;

/**
 * Created by Ashwini on 2/4/2016.
 */
public class MovieDetailActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.movie_details);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        Movie movie = (Movie)bundle.getSerializable("movie");

        ((TextView)findViewById(R.id.txt_movie_title)).setText(movie.getOriginalTitle());
        ((TextView)findViewById(R.id.txt_movie_synopsis)).setText(movie.getPlotSynopsis());
        ((TextView)findViewById(R.id.txt_movie_date)).setText(movie.getReleaseDate());
        RatingBar movieRatingBar = ((RatingBar) findViewById(R.id.rb_movie_rating));
        movieRatingBar.setStepSize(1);
        movieRatingBar.setMax(10);
        movieRatingBar.setRating((float) movie.getUserRating());
        ImageView moviePosterView = (ImageView)findViewById(R.id.img_movie_detail_poster);
        Picasso.with(getBaseContext()).load(movie.getPosterUrl()).into(moviePosterView);

    }
}
