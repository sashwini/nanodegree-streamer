package com.example.ashwini.spotifystreamer;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.GridView;

import com.example.ashwini.spotifystreamer.common.Movie;
import com.example.ashwini.spotifystreamer.common.MovieApiHelper;
import com.example.ashwini.spotifystreamer.common.ResponseParser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final int SORT_ORDER_POPULAR = 1;
    private final int SORT_ORDER_RATED = 2;
    private final int VISIBLE_THRESHOLD = 5;
    private GridView movieGrid;
    private int page;
    private int sortOrder;
    private MovieApiHelper apiHelper;
    private boolean loading;
    ArrayList<Movie> movies;
    MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        page = 1;
        sortOrder = SORT_ORDER_POPULAR;
        loading = false;
        apiHelper = new MovieApiHelper();
        movies = new ArrayList<Movie>();
        movieAdapter = new MovieAdapter(getBaseContext(), movies);

        setupGrid();
    }

    private void setupGrid() {
        movieGrid = (GridView)findViewById(R.id.gridMovie);
        movieGrid.setAdapter(movieAdapter);

        //Reference: For fetching list items as user scrolls down referred below link
        //http://benjii.me/2010/08/endless-scrolling-listview-in-android/
        movieGrid.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + VISIBLE_THRESHOLD)) {
                    switch (sortOrder) {
                        case SORT_ORDER_RATED:
                            fetchRatedMovies();
                            break;
                        case SORT_ORDER_POPULAR:
                        default:
                            fetchPopularMovies();
                            break;
                    }
                }
            }
        });
    }

    private void fetchPopularMovies(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                loading = true;
                String response = apiHelper.getPopularMovies(page);
                final ArrayList<Movie> movieList = new ResponseParser().parseMovieList(response);
                movies.addAll(movieList);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        movieAdapter.notifyDataSetChanged();
                        loading = false;
                        page++;
                    }
                });
            }
        };

        new Thread(runnable).start();
    }

    private void fetchRatedMovies(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                loading = true;
                String response = apiHelper.getRatedMovies(page);
                final ArrayList<Movie> movieList = new ResponseParser().parseMovieList(response);
                movies.addAll(movieList);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        movieAdapter.notifyDataSetChanged();
                        loading = false;
                        page++;
                    }
                });
            }
        };

        new Thread(runnable).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_sort_popular:
                sortOrder = SORT_ORDER_POPULAR;
                page = 1;
                movies.clear();
                movieGrid.invalidateViews();
                movieAdapter.notifyDataSetInvalidated();
                fetchPopularMovies();
                break;
            case R.id.action_sort_rated:
                sortOrder = SORT_ORDER_RATED;
                page = 1;
                movies.clear();
                movieGrid.invalidateViews();
                movieAdapter.notifyDataSetInvalidated();
                fetchRatedMovies();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
