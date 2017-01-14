package com.project.udacity.santos.wesley.popularmovies.api;

import android.net.Uri;
import android.util.Log;

import com.project.udacity.santos.wesley.popularmovies.model.Movie;
import com.project.udacity.santos.wesley.popularmovies.utils.JSONUtils;
import com.project.udacity.santos.wesley.popularmovies.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wesley on 14/01/17.
 */

public class MovieDBApi {
    private static MovieDBApi instance;
    private final static String TAG = MovieDBApi.class.getSimpleName();
    private final static String API_KEY = "...";
    private final static String ENDPOINT = "https://api.themoviedb.org";
    private final static String IMAGE_EDPOINT = "http://image.tmdb.org/t/p/w185";
    private final static String IMAGE_BACKDROP_EDPOINT = "http://image.tmdb.org/t/p/w780";
    private final static String POPULARITY_PATH = "3/movie/popular";
    private final static String TOP_RATED_PATH = "3/movie/top_rated";

    public ArrayList<Movie> requestMoviesByPopularity() {
        URL requestUrl = buildPopularityRequestURL(POPULARITY_PATH);
        return requestMovieList(requestUrl);
    }

    public ArrayList<Movie> requestMoviesByRating() {
        URL requestUrl = buildPopularityRequestURL(TOP_RATED_PATH);
        return requestMovieList(requestUrl);
    }

    public String getImagePosterURLForMovie(Movie movie) {
        StringBuilder imageUrl = new StringBuilder();
        if(movie.getPosterPath() != null) {
            imageUrl.append(IMAGE_EDPOINT);
            imageUrl.append(movie.getPosterPath());
        }
        return imageUrl.toString();
    }

    public String getImageBackdropURLForMovie(Movie movie) {
        StringBuilder imageUrl = new StringBuilder();
        if(movie.getBackdropPath() != null) {
            imageUrl.append(IMAGE_BACKDROP_EDPOINT);
            imageUrl.append(movie.getBackdropPath());
        }
        return imageUrl.toString();
    }

    private ArrayList<Movie> requestMovieList(URL movieURL) {
        try {
            String stringRequest = NetworkUtils.getResponseFromHttpUrl(movieURL);
            return JSONUtils.parseJsonStringToMovieList(stringRequest);
        } catch (IOException | JSONException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    private URL buildPopularityRequestURL(String path) {
        Uri builtUri = Uri.parse(ENDPOINT)
                .buildUpon()
                .path(path)
                .appendQueryParameter("api_key", API_KEY)
                .build();

        URL requestURL = null;

        try {
            requestURL = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.e(TAG, e.getMessage());
        }

        Log.v(TAG, "Built URI " + requestURL);

        return requestURL;
    }


    public static MovieDBApi getInstance() {
        if(instance == null) {
            instance = new MovieDBApi();
        }
        return instance;
    }
}
