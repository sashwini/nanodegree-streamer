package com.example.ashwini.spotifystreamer;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashwini.spotifystreamer.common.Movie;
import com.example.ashwini.spotifystreamer.common.Review;
import com.example.ashwini.spotifystreamer.common.Trailer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ashwini on 3/6/2016.
 * Used below link as reference
 * https://guides.codepath.com/android/Heterogenous-Layouts-inside-RecyclerView#overview as
 */
public class DetailViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int DETAIL_VIEW_HOLDER = 111;
    public static final int TRAILER_VIEW_HOLDER = 112;
    public static final int REVIEW_VIEW_HOLDER = 113;

    private Movie movie;
    private ArrayList<Trailer> trailers;
    private ArrayList<Review> reviews;

    private Context context;

    public DetailViewAdapter(Context context, Movie movie) {
        this.context = context;
        this.movie = movie;
        trailers = new ArrayList<>();
        reviews = new ArrayList<>();
    }

    public void setTrailers(ArrayList<Trailer> trailerArrayList) {
        trailers = trailerArrayList;
        notifyDataSetChanged();
    }

    public void setReviews(ArrayList<Review> reviewArrayList) {
        reviews = reviewArrayList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case DETAIL_VIEW_HOLDER:
                View detailView = inflater.inflate(R.layout.movie_details, parent, false);
                viewHolder = new DetailViewHolder(detailView);
                break;
            case TRAILER_VIEW_HOLDER:
                View trailerView = inflater.inflate(R.layout.trailer_item, parent, false);
                viewHolder = new TrailerViewHolder(trailerView);
                break;
            case REVIEW_VIEW_HOLDER:
                View reviewView = inflater.inflate(R.layout.review_item, parent, false);
                viewHolder = new ReviewViewHolder(reviewView);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case DETAIL_VIEW_HOLDER:
                updateMovieDetails((DetailViewHolder)holder);
                break;
            case TRAILER_VIEW_HOLDER:
                updateTrailerList((TrailerViewHolder)holder, position);
                break;
            case REVIEW_VIEW_HOLDER:
                updateReviewList((ReviewViewHolder)holder, position);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return trailers.size() + reviews.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return DETAIL_VIEW_HOLDER;
        }
        else if (position <= trailers.size()){
            return TRAILER_VIEW_HOLDER;
        }
        else {
            return REVIEW_VIEW_HOLDER;
        }
    }

    private void updateMovieDetails(DetailViewHolder detailViewHolder) {
        detailViewHolder.getTitleTextView().setText(movie.getOriginalTitle());
        detailViewHolder.getReleaseTextView().setText(movie.getReleaseDate());
        detailViewHolder.getSynopsisTextView().setText(movie.getPlotSynopsis());
        detailViewHolder.getRatingBar().setRating((float) movie.getUserRating());
        detailViewHolder.getRatingBar().setStepSize(1);
        detailViewHolder.getRatingBar().setMax(10);
        Picasso.with(context).load(movie.getPosterUrl()).into(detailViewHolder.getMoviePosterView());
        detailViewHolder.getFavButton().setTag(movie.getMovieId());
        detailViewHolder.getFavButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   markFavorite((int)v.getTag());
            }
        });
    }

    private void updateTrailerList(TrailerViewHolder trailerViewHolder, int position) {
        TextView nameTextView = trailerViewHolder.getNameTextView();
        int trailerPosition = position - 1;
        int showTitle = (trailerPosition == 0) ? View.VISIBLE : View.GONE;

        trailerViewHolder.getTitleTextView().setVisibility(showTitle);
        nameTextView.setText(trailers.get(trailerPosition).getName());
        nameTextView.setTag(trailers.get(trailerPosition).getTrailerId());

        nameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int trailerId = Integer.parseInt(v.getTag().toString());
                showVideo(trailerId);
            }
        });
    }

    private void updateReviewList(ReviewViewHolder reviewViewHolder, int position) {
        int reviewPosition = position - trailers.size() - 1;
        int showTitle = (reviewPosition == 0) ? View.VISIBLE : View.GONE;

        reviewViewHolder.getReviewTitleView().setVisibility(showTitle);
        reviewViewHolder.getAuthorTextView().setText(reviews.get(reviewPosition).getAuthor());
        reviewViewHolder.getReviewTextView().setText(reviews.get(reviewPosition).getContent());
    }

    private void showVideo(int trailerId) {
        Trailer trailer = null;
        for (Trailer t: trailers) {
            if(t.getTrailerId() == trailerId){
                trailer = t;
                break;
            }
        }

        if(trailer == null){
            return;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailer.getVideoUrl()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private void markFavorite(int movieId) {
        ContentValues values = new ContentValues();
        values.put(FavoriteProvider.COL_MOVIE_ID, movieId);
        context.getContentResolver().insert(FavoriteProvider.CONTENT_URI, values);
        Toast.makeText(context, R.string.favorite_message, Toast.LENGTH_LONG).show();
    }
}
