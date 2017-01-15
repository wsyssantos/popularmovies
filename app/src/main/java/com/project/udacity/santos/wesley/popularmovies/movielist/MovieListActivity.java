package com.project.udacity.santos.wesley.popularmovies.movielist;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.project.udacity.santos.wesley.popularmovies.R;
import com.project.udacity.santos.wesley.popularmovies.model.Movie;
import com.project.udacity.santos.wesley.popularmovies.moviedetail.MovieDetailActivity;
import com.project.udacity.santos.wesley.popularmovies.utils.NetworkUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListActivity extends AppCompatActivity implements MovieListRequestDelegate, MovieListOnItemClickListener {

    private static final String TAG = MovieListActivity.class.getSimpleName();

    @BindView(R.id.rv_movie_list)
    RecyclerView recyclerViewMovieList;
    @BindView(R.id.pb_movie_list)
    ProgressBar progressBarMovieList;

    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Movie> movieList;
    private MovieListAdapter movieListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);

        layoutManager = new StaggeredGridLayoutManager(columntCount(), StaggeredGridLayoutManager.VERTICAL);
        recyclerViewMovieList.setLayoutManager(layoutManager);
        recyclerViewMovieList.setHasFixedSize(true);
        movieListAdapter = new MovieListAdapter(this);
        recyclerViewMovieList.setAdapter(movieListAdapter);

        if(savedInstanceState == null || !savedInstanceState.containsKey("movieList")) {
            loadMovieList(MovieListRequestTask.MovieOrderType.POPULARITY);
        } else {
            movieList = savedInstanceState.getParcelableArrayList("movieList");
            fillRecyclerView();
        }
    }

    private void showProgressBar() {
        progressBarMovieList.setVisibility(View.VISIBLE);
        recyclerViewMovieList.setVisibility(View.INVISIBLE);
    }

    private void showRecyclerView() {
        recyclerViewMovieList.setVisibility(View.VISIBLE);
        progressBarMovieList.setVisibility(View.INVISIBLE);
    }

    private int columntCount() {
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return 3;
        }
        return 2;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        if(movieList != null) {
            outState.putParcelableArrayList("movieList", movieList);
            Log.i(TAG, "movieListAdded");
        }
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(movieList != null) {
            outState.putParcelableArrayList("movieList", movieList);
            Log.i(TAG, "movieListAdded");
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_order_popularity :
                loadMovieList(MovieListRequestTask.MovieOrderType.POPULARITY);
                return true;
            case R.id.action_order_rating :
                loadMovieList(MovieListRequestTask.MovieOrderType.RATING);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadMovieList(MovieListRequestTask.MovieOrderType type) {
        movieList = new ArrayList<>();
        if(NetworkUtils.hasInternetConnection(this)) {
            fillRecyclerView();
            MovieListRequestTask task = new MovieListRequestTask(this);
            task.execute(type);
        } else {
            showInternetConnectionErrorMessage();
        }
    }

    private void showInternetConnectionErrorMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.no_internet_connection_error_message)
                .setTitle(R.string.no_internet_connection_error_title);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onMovieListRequestPreExecute() {
        showProgressBar();
    }

    @Override
    public void onMovieListRequestPostExecute(ArrayList<Movie> movies) {
        showRecyclerView();
        movieList = movies;
        fillRecyclerView();
    }

    private void fillRecyclerView() {
        movieListAdapter.setMovieList(movieList);
    }

    @Override
    public void onMovieItemClick(Movie movie) {
        Intent detailIntent = new Intent(MovieListActivity.this, MovieDetailActivity.class);
        detailIntent.putExtra("movie", movie);
        startActivity(detailIntent);
    }
}