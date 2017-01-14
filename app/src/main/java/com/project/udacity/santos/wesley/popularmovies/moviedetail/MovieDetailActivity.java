package com.project.udacity.santos.wesley.popularmovies.moviedetail;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.udacity.santos.wesley.popularmovies.R;
import com.project.udacity.santos.wesley.popularmovies.api.MovieDBApi;
import com.project.udacity.santos.wesley.popularmovies.model.Movie;
import com.project.udacity.santos.wesley.popularmovies.utils.FormatUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import static com.project.udacity.santos.wesley.popularmovies.R.id.movieBackdropImage;

public class MovieDetailActivity extends AppCompatActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout appBarLayout;
    private ImageView movieBackdropImage;
    private Toolbar toolbar;
    private ImageView moviePoster;
    private TextView movieTitle;
    private TextView movieRating;
    private TextView movieReleaseDate;
    private TextView movieSynopsis;

    private Movie movie;
    private MovieDBApi movieDBApi = MovieDBApi.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        bindViews();
        configureToolBar();
        loadMovieFromIntent();
    }

    private void loadMovieFromIntent() {
        if(getIntent().hasExtra("movie")) {
            movie = getIntent().getParcelableExtra("movie");
            configureViewContent();
        }
    }

    private void bindViews() {
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        movieBackdropImage = (ImageView) findViewById(R.id.movieBackdropImage);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        moviePoster = (ImageView) findViewById(R.id.iv_movie_poster);
        movieTitle = (TextView) findViewById(R.id.tv_movie_title);
        movieRating = (TextView) findViewById(R.id.tv_movie_rating);
        movieReleaseDate = (TextView) findViewById(R.id.tv_movie_release_date);
        movieSynopsis = (TextView) findViewById(R.id.tv_movie_synopsis);
    }

    private void configureViewContent() {
        getSupportActionBar().setTitle(movie.getTitle());
        movieTitle.setText(movie.getTitle());
        movieRating.setText(FormatUtils.formatDoubleToOneDecimal(movie.getVoteAverage()));
        movieReleaseDate.setText(movie.getReleaseDate());
        movieSynopsis.setText(movie.getOverview());

        Picasso.with(this)
                .load(movieDBApi.getImagePosterURLForMovie(movie))
                .error(R.drawable.no_image)
                .placeholder(R.drawable.no_image)
                .into(moviePoster);

        Picasso.with(this)
                .load(movieDBApi.getImageBackdropURLForMovie(movie))
                .into(movieBackdropImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        Bitmap bitmap = ((BitmapDrawable) movieBackdropImage.getDrawable()).getBitmap();
                        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                            public void onGenerated(Palette palette) {
                                applyPalette(palette);
                            }
                        });
                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    private void configureToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void applyPalette(Palette palette) {
        int primaryDark = getResources().getColor(R.color.colorPrimaryDark);
        int primary = getResources().getColor(R.color.colorPrimary);
        collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(primary));
        collapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkMutedColor(primaryDark));
    }
}
