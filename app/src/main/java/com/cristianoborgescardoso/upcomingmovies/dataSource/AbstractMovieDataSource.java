package com.cristianoborgescardoso.upcomingmovies.dataSource;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.cristianoborgescardoso.upcomingmovies.api.ApiConfig;
import com.cristianoborgescardoso.upcomingmovies.api.MovieService;
import com.cristianoborgescardoso.upcomingmovies.api.NetworkState;
import com.cristianoborgescardoso.upcomingmovies.modelJson.AbstractMovieResponse;
import com.cristianoborgescardoso.upcomingmovies.modelJson.Movie;
import com.cristianoborgescardoso.upcomingmovies.modelJson.MoviesResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public abstract class AbstractMovieDataSource extends PageKeyedDataSource<Long, Movie> {
    private static final String TAG = UpcomingMoviesDataSource.class.getName();
    private final MovieService movieService;
    private final MutableLiveData<NetworkState> networkStateMutableLiveData = new MutableLiveData<>();

    AbstractMovieDataSource(MovieService movieService) {
        this.movieService = movieService;
    }

    public MutableLiveData<NetworkState> getNetworkStateMutableLiveData() {
        return networkStateMutableLiveData;
    }

    @Override
    public void loadInitial(@NonNull PageKeyedDataSource.LoadInitialParams<Long> params, @NonNull final PageKeyedDataSource.LoadInitialCallback<Long, Movie> callback) {

        Log.d(TAG, "loadInitial: ");
        networkStateMutableLiveData.postValue(NetworkState.LOADING);

        Call<MoviesResponse> moviesResponseCall = (Call<MoviesResponse>) callOnLoad(movieService, ApiConfig.API_DEFAULT_PAGE_KEY);

        moviesResponseCall.enqueue(new Callback<MoviesResponse>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                Log.d(TAG, String.format("http call String: '%s'", call.request().url().toString()));
                if (response.isSuccessful() && response.code() == 200 && response.body() != null && response.body().getMovieResponses() != null) {
                    MoviesResponse moviesResponse = response.body();

                    printMovieDetails(moviesResponse.getMovieResponses());
                    networkStateMutableLiveData.postValue(NetworkState.LOADED);
                    callback.onResult(moviesResponse.getMovieResponses(), null, ApiConfig.API_DEFAULT_PAGE_KEY +1);
                } else {
                    Log.e(TAG, "onResponse error " + response.message());
                    networkStateMutableLiveData.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                String errorMessage = t.getMessage();
                Log.e(TAG, "onFailure: " + errorMessage);
                networkStateMutableLiveData.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
            }
        });
    }

    @Override
    @EverythingIsNonNull
    public void loadBefore(@NonNull PageKeyedDataSource.LoadParams<Long> params, @NonNull PageKeyedDataSource.LoadCallback<Long, Movie> callback) {

    }

    @Override
    @EverythingIsNonNull
    public void loadAfter(final PageKeyedDataSource.LoadParams<Long> params, final PageKeyedDataSource.LoadCallback<Long, Movie> callback) {
        networkStateMutableLiveData.postValue(NetworkState.LOADING);

        Call<MoviesResponse> moviesResponseCall = (Call<MoviesResponse>) callOnLoad(movieService, params.key);
        moviesResponseCall.enqueue(new Callback<MoviesResponse>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                Log.d(TAG, String.format("http call String: '%s'", call.request().url().toString()));
                if (response.isSuccessful() && response.code() == 200 && response.body() != null && response.body().getMovieResponses() != null) {
                    MoviesResponse moviesResponse = response.body();

                    printMovieDetails(moviesResponse.getMovieResponses());

                    networkStateMutableLiveData.postValue(NetworkState.LOADED);

                    Long nextKey = (params.key == moviesResponse.getTotalPages()) ? null : params.key + 1;
                    Log.d(TAG, String.format("page: %d of %d", params.key, moviesResponse.getTotalPages()));

                    callback.onResult(moviesResponse.getMovieResponses(), nextKey);
                } else {
                    Log.e(TAG, "onResponse error " + response.message());
                    networkStateMutableLiveData.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                String errorMessage = t.getMessage();
                Log.e(TAG, "onFailure: " + errorMessage);
                networkStateMutableLiveData.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
            }
        });
    }

    private void printMovieDetails(List<Movie> movies) {
        for (Movie movie : movies) {
            Log.d(TAG, String.format("Movie details %s - %s - %s - %s", movie.getTitle(), GenreDataSource.getGenreByIdList(movie.getGenreIds()), movie.getReleaseDate(), movie.getPosterPath()));
        }
    }

    protected abstract Call<? extends AbstractMovieResponse> callOnLoad(MovieService movieService, long pageNumber);
}
