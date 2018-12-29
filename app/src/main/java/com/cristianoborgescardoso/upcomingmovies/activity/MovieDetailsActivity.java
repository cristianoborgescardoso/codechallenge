package com.cristianoborgescardoso.upcomingmovies.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.cristianoborgescardoso.upcomingmovies.R;
import com.cristianoborgescardoso.upcomingmovies.api.ApiConfig;
import com.cristianoborgescardoso.upcomingmovies.dataSource.GenreDataSource;
import com.cristianoborgescardoso.upcomingmovies.modelJson.Dates;
import com.cristianoborgescardoso.upcomingmovies.modelJson.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity implements MovieDetailsObserver {

    private static final String TAG = MovieDetailsActivity.class.getName();

    private TextView tvTitle;
    private TextView tvGenres;
    private TextView tvReleaseDate;
    private TextView ivOverview;
    private ImageView ivPoster;
    private ImageView ivBackdrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ApiConfig.forceLocale(getBaseContext());//TODO: just for testing, do not use it in production

        setTitle(getResources().getString(R.string.title_activity_details));

        setContentView(R.layout.movie_details);

        //Enable action at back arrow in the toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        instantiateFields();
        try {
            fillGUI((Movie) getIntent().getExtras().getSerializable(Movie.class.getCanonicalName()));
        } catch (NullPointerException ex) {
            Log.d(TAG, "Error while try to fill GUI", ex);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void instantiateFields() {
        tvTitle = findViewById(R.id.movie_title);
        tvGenres = findViewById(R.id.movie_genres);
        tvReleaseDate = findViewById(R.id.movie_date);
        ivOverview = findViewById(R.id.movie_overview);
        ivPoster = findViewById(R.id.movie_poster);
        ivBackdrop =  findViewById(R.id.movie_backdrop);
    }

    private void fillGUI(Movie movie) {
        setMovieTitle(movie.getTitle());
        setMovieGenres(GenreDataSource.getGenreByIdList(movie.getGenreIds()));
        setMovieReleaseDate(Dates.getFormattedDate(movie.getReleaseDate()));
        setMovieOverview(movie.getOverview());
        setMoviePosterImage(movie.getPosterPath());
        setMovieBackdropImage(movie.getBackdropPath());
    }

    @Override
    public void setMovieTitle(String title) {
        bindText(tvTitle, title, getResources().getString(R.string.title_is_missing));
    }

    @Override
    public void setMovieOverview(String overview) {
        bindText(ivOverview, overview, getResources().getString(R.string.overview_is_missing));
    }

    @Override
    public void setMovieGenres(String genres) {
        bindText(tvGenres, genres, getResources().getString(R.string.genre_is_missing));
    }

    @Override
    public void setMovieReleaseDate(String releaseDate) {
        bindText(tvReleaseDate, releaseDate, getResources().getString(R.string.release_date_is_missing));
    }

    @Override
    public void setMovieBackdropImage(String imagePath) {
        bindImage(ivBackdrop, imagePath, ApiConfig.BackdropSize.w780);
    }

    @Override
    public void setMoviePosterImage(String imagePath) {
        bindImage(ivPoster, imagePath, ApiConfig.PosterSize.w342);
    }

    private void bindText(TextView textView, String text, String resourceMissingMessage) {
        try {
            if (text == null || text.trim().isEmpty()) {
                text = resourceMissingMessage;
            }
            textView.setText(text);
        } catch (Exception ex) {
            Log.e(TAG, String.format("Error While binding MovieDetail text: '%s'", text), ex);
        }
    }

    private void bindImage(ImageView imageView, String imageURL, ApiConfig.ImageSize imageSize) {
        try {
            if (imageURL == null) {
                imageView.setImageResource(R.drawable.ic_do_not_disturb_black_45dp);
            } else {
                Picasso.get().load(ApiConfig.getPosterBaseURL(imageSize) + imageURL).into(imageView);
            }

        } catch (Exception ex) {
            Log.e(TAG, "Error While binding MovieDetail image", ex);
        }
    }

}
