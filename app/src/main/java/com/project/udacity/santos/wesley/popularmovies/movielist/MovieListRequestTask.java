package com.project.udacity.santos.wesley.popularmovies.movielist;

import android.os.AsyncTask;

import com.project.udacity.santos.wesley.popularmovies.api.MovieDBApi;
import com.project.udacity.santos.wesley.popularmovies.model.Movie;

import java.util.ArrayList;

/**
 * Created by wesley on 14/01/17.
 */

public class MovieListRequestTask extends AsyncTask<MovieListRequestTask.MovieOrderType, Void, ArrayList<Movie>> {

    private MovieDBApi movieDBApi = MovieDBApi.getInstance();
    private MovieListRequestDelegate delegate;

    public MovieListRequestTask(MovieListRequestDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        delegate.onMovieListRequestPreExecute();
    }

    @Override
    protected ArrayList<Movie> doInBackground(MovieOrderType... movieOrderTypes) {
        MovieOrderType movieOrderType = movieOrderTypes[0];
        ArrayList<Movie> movieList = null;
        if(movieOrderType == MovieOrderType.POPULARITY) {
            movieList = movieDBApi.requestMoviesByPopularity();
        } else {
            movieList = movieDBApi.requestMoviesByRating();
        }
        return movieList;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        super.onPostExecute(movies);
        delegate.onMovieListRequestPostExecute(movies);
    }

    public enum MovieOrderType {
        POPULARITY,
        RATING
    }
}
