package com.cristianoborgescardoso.upcomingmovies.dataSource;

import android.arch.paging.DataSource;
import android.util.Log;

import com.cristianoborgescardoso.upcomingmovies.api.MovieService;

public class UpcomingMoviesDataSourceFactory extends AbstractDataSourceFactory {

    public UpcomingMoviesDataSourceFactory(MovieService movieService) {
        super(movieService);
    }

    @Override
    public DataSource create() {
        Log.d(TAG, "Creating Data Source...: ");
        moviesDataSource = new UpcomingMoviesDataSource(movieService);
        mutableLiveData.postValue(moviesDataSource);
        return moviesDataSource;
    }
}
