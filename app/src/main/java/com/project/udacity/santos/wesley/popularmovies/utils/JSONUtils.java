package com.project.udacity.santos.wesley.popularmovies.utils;

import com.project.udacity.santos.wesley.popularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wesley on 14/01/17.
 */

public class JSONUtils {

    public static ArrayList<Movie> parseJsonStringToMovieList(String json) throws JSONException {
        JSONObject jsonResponse = new JSONObject(json);
        JSONArray results = jsonResponse.getJSONArray("results");

        ArrayList<Movie> movieList = new ArrayList<>();

        for(int i = 0; i < results.length(); i++) {
            JSONObject movieJsonObject = results.getJSONObject(i);
            Movie movie = new Movie(movieJsonObject);
            movieList.add(movie);
        }

        return movieList;
    }
}
