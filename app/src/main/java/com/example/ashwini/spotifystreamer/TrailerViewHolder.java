package com.example.ashwini.spotifystreamer;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Ashwini on 3/7/2016.
 */
public class TrailerViewHolder extends RecyclerView.ViewHolder {

    private TextView nameTextView;
    private TextView titleTextView;

    public TextView getNameTextView() {
        return nameTextView;
    }

    public void setNameTextView(TextView nameTextView) {
        this.nameTextView = nameTextView;
    }

    public TextView getTitleTextView() {
        return titleTextView;
    }

    public void setTitleTextView(TextView titleTextView) {
        this.titleTextView = titleTextView;
    }

    public TrailerViewHolder(View itemView) {
        super(itemView);
        nameTextView = (TextView)itemView.findViewById(R.id.txt_trailer_name);
        titleTextView = (TextView)itemView.findViewById(R.id.txt_trailer_title);
    }
}
