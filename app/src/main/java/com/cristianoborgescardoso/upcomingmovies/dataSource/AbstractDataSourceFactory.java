package com.cristianoborgescardoso.upcomingmovies.dataSource;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.cristianoborgescardoso.upcomingmovies.api.MovieService;


public abstract class AbstractDataSourceFactory extends DataSource.Factory {
    static final String TAG = AbstractDataSourceFactory.class.getName();
    AbstractMovieDataSource moviesDataSource;
    final MutableLiveData<AbstractMovieDataSource> mutableLiveData;
    final MovieService movieService;

    AbstractDataSourceFactory(MovieService movieService) {
        this.movieService = movieService;
        mutableLiveData = new MutableLiveData<>();
    }

    public abstract DataSource create();

    public MutableLiveData<AbstractMovieDataSource> getMutableLiveData() {
        return mutableLiveData;
    }

    public void invalidateDataSource() {
        moviesDataSource.invalidate();
    }

    public MovieService getMovieService() {
        return movieService;
    }

}
