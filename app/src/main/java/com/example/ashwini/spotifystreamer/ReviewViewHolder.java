package com.example.ashwini.spotifystreamer;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Ashwini on 3/7/2016.
 */
public class ReviewViewHolder extends RecyclerView.ViewHolder {

    private TextView authorTextView;
    private TextView reviewTextView;
    private TextView reviewTitleView;

    public TextView getAuthorTextView() {
        return authorTextView;
    }

    public void setAuthorTextView(TextView authorTextView) {
        this.authorTextView = authorTextView;
    }

    public TextView getReviewTextView() {
        return reviewTextView;
    }

    public void setReviewTextView(TextView reviewTextView) {
        this.reviewTextView = reviewTextView;
    }

    public TextView getReviewTitleView() {
        return reviewTitleView;
    }

    public void setReviewTitleView(TextView reviewTitleView) {
        this.reviewTitleView = reviewTitleView;
    }

    public ReviewViewHolder(View itemView) {
        super(itemView);
        authorTextView = (TextView)itemView.findViewById(R.id.txt_author_name);
        reviewTextView = (TextView)itemView.findViewById(R.id.txt_review_content);
        reviewTitleView = (TextView)itemView.findViewById(R.id.txt_review_title);
    }
}
