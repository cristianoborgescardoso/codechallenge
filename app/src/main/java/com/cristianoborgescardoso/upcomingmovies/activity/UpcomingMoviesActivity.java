package com.cristianoborgescardoso.upcomingmovies.activity;


import com.cristianoborgescardoso.upcomingmovies.R;
import com.cristianoborgescardoso.upcomingmovies.model.UpcomingMoviesModel;

public class UpcomingMoviesActivity extends AbstractMovieListActivity {
    private static final String TAG = UpcomingMoviesActivity.class.getName();

    public UpcomingMoviesActivity() {
        super(TAG, UpcomingMoviesModel.class, R.string.app_name);

    }
}
