package com.project.udacity.santos.wesley.popularmovies.movielist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.udacity.santos.wesley.popularmovies.R;
import com.project.udacity.santos.wesley.popularmovies.api.MovieDBApi;
import com.project.udacity.santos.wesley.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

/**
 * Created by wesley on 14/01/17.
 */

public class MovieListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView imageViewMoviePoster;
    private TextView textViewMovieTitle;
    private MovieDBApi movieDBApi = MovieDBApi.getInstance();
    private Movie movie;
    private MovieListOnItemClickListener itemClickListener;

    public MovieListViewHolder(View itemView, MovieListOnItemClickListener listener) {
        super(itemView);
        itemClickListener = listener;

        imageViewMoviePoster = (ImageView) itemView.findViewById(R.id.iv_movie_poster);
        textViewMovieTitle = (TextView) itemView.findViewById(R.id.tv_movie_title);

        itemView.setOnClickListener(this);
    }

    public void bind(Movie movie) {
        this.movie = movie;

        textViewMovieTitle.setText(movie.getTitle());

        Picasso.with(itemView.getContext())
                .load(movieDBApi.getImagePosterURLForMovie(movie))
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(imageViewMoviePoster);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onMovieItemClick(movie);
    }
}
