package com.cristianoborgescardoso.upcomingmovies.model;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.util.Log;

import com.cristianoborgescardoso.upcomingmovies.api.NetworkState;
import com.cristianoborgescardoso.upcomingmovies.dataSource.AbstractDataSourceFactory;
import com.cristianoborgescardoso.upcomingmovies.dataSource.AbstractMovieDataSource;
import com.cristianoborgescardoso.upcomingmovies.dataSource.GenreDataSource;
import com.cristianoborgescardoso.upcomingmovies.modelJson.Movie;


public abstract class AbstractMovieModel extends ViewModel {
    private static final String TAG = UpcomingMoviesModel.class.getName();
    private final LiveData<PagedList<Movie>> moviesPagedList;
    private final LiveData<NetworkState> networkStateLiveData;
    private final AbstractDataSourceFactory abstractDataSourceFactory;
    private NetworkState mNetworkState;

    AbstractMovieModel(AbstractDataSourceFactory abstractDataSourceFactory) {
        this.abstractDataSourceFactory = abstractDataSourceFactory;
        //updateGenres
        GenreDataSource.updateGenresFromServer(abstractDataSourceFactory.getMovieService());

        networkStateLiveData = Transformations.switchMap(abstractDataSourceFactory.getMutableLiveData(), new Function<AbstractMovieDataSource, LiveData<NetworkState>>() {
            @Override
            public LiveData<NetworkState> apply(AbstractMovieDataSource source) {
                Log.d(TAG, "apply: network change");
                return source.getNetworkStateMutableLiveData();
            }
        });

        PagedList.Config pageConfig = (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10)
                .setPageSize(20).build();

        moviesPagedList = new LivePagedListBuilder<Long, Movie>(abstractDataSourceFactory, pageConfig).build();
    }

    public LiveData<PagedList<Movie>> getMoviesPagedList() {
        Log.d(TAG, "getMoviesPagedList: ");
        return moviesPagedList;
    }

    public LiveData<NetworkState> getNetworkStateLiveData() {
        return networkStateLiveData;
    }

    public AbstractDataSourceFactory getAbstractDataSourceFactory() {
        return abstractDataSourceFactory;
    }
    public void setNetworkState(NetworkState networkState) {
        mNetworkState = networkState;
    }

}
