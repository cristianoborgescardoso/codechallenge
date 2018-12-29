package com.cristianoborgescardoso.upcomingmovies.dataSource;

import com.cristianoborgescardoso.upcomingmovies.api.ApiConfig;
import com.cristianoborgescardoso.upcomingmovies.api.MovieService;
import com.cristianoborgescardoso.upcomingmovies.modelJson.AbstractMovieResponse;

import retrofit2.Call;

public class UpcomingMoviesDataSource extends AbstractMovieDataSource {

    public UpcomingMoviesDataSource(MovieService movieService) {
        super(movieService);
    }

    @Override
    public Call<? extends AbstractMovieResponse> callOnLoad(MovieService movieService, long pageNumber) {
        return movieService.getUpcomingMovies(ApiConfig.API_KEY,ApiConfig.SYSTEM_LANGUAGE,ApiConfig.SYSTEM_REGION,pageNumber);
        //return movieService.getMoviesInTheater(ApiConfig.API_KEY,ApiConfig.SYSTEM_LANGUAGE,ApiConfig.SYSTEM_REGION,pageNumber);
    }
}

