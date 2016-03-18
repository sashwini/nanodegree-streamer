package com.example.ashwini.spotifystreamer;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Ashwini on 2/22/2016.
 */
public class GridFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.content_main, container, false);
//        Activity activity = (Activity)getActivity();
//        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
//        activity.setSupportActionBar(toolbar);

        return view;
    }
}
