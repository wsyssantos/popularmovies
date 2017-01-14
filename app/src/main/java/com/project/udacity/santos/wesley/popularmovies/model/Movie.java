package com.project.udacity.santos.wesley.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by wesley on 14/01/17.
 */

public class Movie implements Parcelable {
    private static final String TAG = "Movie";

    private String posterPath;
    private boolean adult;
    private String overview;
    private String releaseDate;
    private int id;
    private String originalTitle;
    private String originalLanguage;
    private String title;
    private String backdropPath;
    private double popularity;
    private int voteCount;
    private boolean video;
    private double voteAverage;

    public Movie(JSONObject jsonObject) {
        try {
            posterPath = jsonObject.getString("poster_path");
            adult = jsonObject.getBoolean("adult");
            overview = jsonObject.getString("overview");
            releaseDate = jsonObject.getString("release_date");
            id = jsonObject.getInt("id");
            originalTitle = jsonObject.getString("original_title");
            originalLanguage = jsonObject.getString("original_language");
            title = jsonObject.getString("title");
            backdropPath = jsonObject.getString("backdrop_path");
            popularity = jsonObject.getDouble("popularity");
            voteCount = jsonObject.getInt("vote_count");
            video = jsonObject.getBoolean("video");
            voteAverage = jsonObject.getDouble("vote_average");
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public Movie(Parcel parcel) {
        posterPath = parcel.readString();
        adult = parcel.readByte() != 0;
        overview = parcel.readString();
        releaseDate = parcel.readString();
        id = parcel.readInt();
        originalTitle = parcel.readString();
        originalLanguage = parcel.readString();
        title = parcel.readString();
        backdropPath = parcel.readString();
        popularity = parcel.readDouble();
        voteCount = parcel.readInt();
        video = parcel.readByte() != 0;
        voteAverage = parcel.readDouble();
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(posterPath);
        parcel.writeByte((byte)((adult) ? 1 : 0));
        parcel.writeString(overview);
        parcel.writeString(releaseDate);
        parcel.writeInt(id);
        parcel.writeString(originalTitle);
        parcel.writeString(originalLanguage);
        parcel.writeString(title);
        parcel.writeString(backdropPath);
        parcel.writeDouble(popularity);
        parcel.writeInt(voteCount);
        parcel.writeByte((byte)((video) ? 1 : 0));
        parcel.writeDouble(voteAverage);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public final static Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };
}
