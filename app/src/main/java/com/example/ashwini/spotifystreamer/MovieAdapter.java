package com.example.ashwini.spotifystreamer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ashwini.spotifystreamer.common.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ashwini on 2/4/2016.
 */
public class MovieAdapter extends BaseAdapter {

    Context context;
    ArrayList<Movie> movies;
    int page;

    public MovieAdapter(Context context, ArrayList<Movie> movieArrayList) {
        this.context = context;
        movies = movieArrayList;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Movie getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return movies.get(position).getMovieId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View layout;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.movie_grid_cell, null);
        }
        else {
            layout = convertView;
        }

        ImageView imageView = (ImageView) layout.findViewById(R.id.img_movie_poster);
        imageView.setTag(movies.get(position).getMovieId());
        String imgUrl = movies.get(position).getPosterUrl();
        Picasso.with(context).load(imgUrl).into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int movieId = Integer.parseInt(v.getTag().toString());
                showMovieDetails(movieId);
            }
        });

        TextView posterTextView = (TextView) layout.findViewById(R.id.txt_poster_title);
        posterTextView.setText(movies.get(position).getOriginalTitle());
        return layout;
    }

    private void showMovieDetails(int movieId) {
        Movie movie = null;
        for (Movie m:movies) {
            if(m.getMovieId() == movieId){
                movie = m;
                break;
            }
        }

        if(movie == null){
            return;
        }

        Intent intent = new Intent(context, MovieDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("movie", movie);
        intent.putExtra("bundle", bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
