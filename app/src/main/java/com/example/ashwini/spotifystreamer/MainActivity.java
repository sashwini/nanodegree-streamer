package com.example.ashwini.spotifystreamer;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.ashwini.spotifystreamer.common.Movie;
import com.example.ashwini.spotifystreamer.common.MovieApiHelper;
import com.example.ashwini.spotifystreamer.common.ResponseParser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final int SORT_ORDER_POPULAR = 1;
    private final int SORT_ORDER_RATED = 2;
    private final int SORT_ORDER_FAVORITE = 3;
    private final int VISIBLE_THRESHOLD = 5;
    private TextView emptyTextView;
    private GridView movieGrid;
    private int page;
    private int sortOrder;
    private boolean loading;
    ArrayList<Movie> movies;
    MovieAdapter movieAdapter;
    boolean isDualPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        page = 1;
        sortOrder = SORT_ORDER_POPULAR;
        loading = false;
        movies = new ArrayList<>();

        //check if inflated view is dual pane
        RecyclerView view = (RecyclerView) findViewById(R.id.recycler_view);
        isDualPane = view != null && view.getVisibility() == View.VISIBLE;

        movieAdapter = new MovieAdapter(getBaseContext(), movies, movieClickListener);

        emptyTextView = (TextView)findViewById(R.id.emptyTextView);

        //since our app is highly dependent on internet, check for connection on launch
        if(!isInternetAvailable()){
            //show message that no internet available and quit
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setTitle(getString(R.string.no_connection_title));
            alertBuilder.setMessage(getString(R.string.no_connection_message));
            alertBuilder.setCancelable(false);
            alertBuilder.setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
            alertBuilder.create().show();
        }

        setupGrid();

    }

    View.OnClickListener movieClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int movieId = Integer.parseInt(v.getTag().toString());
            showMovieDetails(movieId);
        }
    };

    private void showMovieDetails(int movieId) {
        Movie movie = null;
        for (Movie m : movies) {
            if (m.getMovieId() == movieId) {
                movie = m;
                break;
            }
        }

        if (movie == null) {
            return;
        }

        if (isDualPane) {
            FragmentManager fragmentManager = getFragmentManager();
            DetailFragment detailFragment = (DetailFragment)fragmentManager.findFragmentById(
                    R.id.detailFragment);
            detailFragment.getView().setVisibility(View.VISIBLE);
            detailFragment.setMenuVisibility(true);
            detailFragment.setMovie(movie);

        } else {
            Intent intent = new Intent(this, MovieDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("movie", movie);
            intent.putExtra("bundle", bundle);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }
    }

    /* This method checks for availability of internet. */
    private boolean isInternetAvailable() {
        ConnectivityManager connectivityManager =
                ((ConnectivityManager) getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isConnected();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(sortOrder == SORT_ORDER_FAVORITE){
            movies.clear();
            fetchFavoriteMovies();
        }
    }

    private void setupGrid() {
        movieGrid = (GridView) findViewById(R.id.gridMovie);
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
                        case SORT_ORDER_FAVORITE:
                            break;
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

    private void fetchPopularMovies() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                loading = true;
                String response = MovieApiHelper.getPopularMovies(page);
                final ArrayList<Movie> movieList = ResponseParser.parseMovieList(response);
                movies.addAll(movieList);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        emptyTextView.setVisibility(View.GONE);
                        movieAdapter.notifyDataSetChanged();
                        loading = false;
                        page++;
                    }
                });
            }
        };

        new Thread(runnable).start();
    }

    private void fetchRatedMovies() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                loading = true;
                String response = MovieApiHelper.getRatedMovies(page);
                final ArrayList<Movie> movieList = ResponseParser.parseMovieList(response);
                movies.addAll(movieList);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        emptyTextView.setVisibility(View.GONE);
                        movieAdapter.notifyDataSetChanged();
                        loading = false;
                        page++;
                    }
                });
            }
        };

        new Thread(runnable).start();
    }

    private void fetchFavoriteMovies() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                loading = true;
                String[] projection = {FavoriteProvider.COL_MOVIE_ID};
                Cursor favCursor = getContentResolver().query(FavoriteProvider.CONTENT_URI,
                        projection, null, null, "DESC");
                if(favCursor != null) {
                    favCursor.moveToFirst();
                    while (!favCursor.isAfterLast()) {
                        int movieId = favCursor.getInt(0);
                        String response = MovieApiHelper.getMovieDetails(movieId);
                        Movie movie = ResponseParser.parseMovieDetails(response);
                        movies.add(movie);
                        favCursor.moveToNext();
                    }
                    favCursor.close();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (movies.size() == 0){
                            //show message for empty grid
                            emptyTextView.setText(getString(R.string.no_favorite));
                            emptyTextView.setVisibility(View.VISIBLE);
                        }
                        else {
                            emptyTextView.setVisibility(View.GONE);
                            movieAdapter.notifyDataSetChanged();
                            loading = false;
                        }
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
                clearDetails();
                break;
            case R.id.action_sort_rated:
                sortOrder = SORT_ORDER_RATED;
                page = 1;
                movies.clear();
                movieGrid.invalidateViews();
                movieAdapter.notifyDataSetInvalidated();
                fetchRatedMovies();
                clearDetails();
                break;
            case R.id.action_sort_favorite:
                sortOrder = SORT_ORDER_FAVORITE;
                page = 1;
                movies.clear();
                movieGrid.invalidateViews();
                movieAdapter.notifyDataSetInvalidated();
                fetchFavoriteMovies();
                clearDetails();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void clearDetails() {
        if(isDualPane) {
            FragmentManager fragmentManager = getFragmentManager();
            DetailFragment detailFragment = (DetailFragment)fragmentManager.findFragmentById(
                    R.id.detailFragment);
            detailFragment.getView().setVisibility(View.GONE);
            detailFragment.setMenuVisibility(false);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
