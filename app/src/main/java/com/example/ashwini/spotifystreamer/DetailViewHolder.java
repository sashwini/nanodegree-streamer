package com.example.ashwini.spotifystreamer;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by Ashwini on 3/6/2016.
 */
public class DetailViewHolder extends RecyclerView.ViewHolder {

    private TextView titleTextView;
    private TextView synopsisTextView;
    private TextView releaseTextView;
    private RatingBar ratingBar;
    private ImageView moviePosterView;
    private Button favButton;

    public Button getFavButton() {
        return favButton;
    }

    public void setFavButton(Button favButton) {
        this.favButton = favButton;
    }

    public TextView getTitleTextView() {
        return titleTextView;
    }

    public void setTitleTextView(TextView titleTextView) {
        this.titleTextView = titleTextView;
    }

    public TextView getSynopsisTextView() {
        return synopsisTextView;
    }

    public void setSynopsisTextView(TextView synopsisTextView) {
        this.synopsisTextView = synopsisTextView;
    }

    public TextView getReleaseTextView() {
        return releaseTextView;
    }

    public void setReleaseTextView(TextView releaseTextView) {
        this.releaseTextView = releaseTextView;
    }

    public RatingBar getRatingBar() {
        return ratingBar;
    }

    public void setRatingBar(RatingBar ratingBar) {
        this.ratingBar = ratingBar;
    }

    public ImageView getMoviePosterView() {
        return moviePosterView;
    }

    public void setMoviePosterView(ImageView moviePosterView) {
        this.moviePosterView = moviePosterView;
    }

    public DetailViewHolder(View itemView) {
        super(itemView);
        titleTextView = ((TextView)itemView.findViewById(R.id.txt_movie_title));
        synopsisTextView = ((TextView)itemView.findViewById(R.id.txt_movie_synopsis));
        releaseTextView = ((TextView)itemView.findViewById(R.id.txt_movie_date));
        ratingBar = ((RatingBar) itemView.findViewById(R.id.rb_movie_rating));
        moviePosterView = (ImageView)itemView.findViewById(R.id.img_movie_detail_poster);
        favButton = (Button)itemView.findViewById(R.id.btn_mark_favorite);
    }

}
