package com.project.udacity.santos.wesley.popularmovies.movielist;

import com.project.udacity.santos.wesley.popularmovies.model.Movie;

import java.util.ArrayList;

/**
 * Created by wesley on 14/01/17.
 */

public interface MovieListRequestDelegate {
    void onMovieListRequestPreExecute();
    void onMovieListRequestPostExecute(ArrayList<Movie> movieList);
}
