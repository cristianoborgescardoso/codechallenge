package com.cristianoborgescardoso.upcomingmovies.model;


import com.cristianoborgescardoso.upcomingmovies.api.ApiConfig;
import com.cristianoborgescardoso.upcomingmovies.api.MovieService;
import com.cristianoborgescardoso.upcomingmovies.dataSource.UpcomingMoviesDataSourceFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpcomingMoviesModel extends AbstractMovieModel {

    public UpcomingMoviesModel() {
        super(new UpcomingMoviesDataSourceFactory(new Retrofit.Builder().baseUrl(ApiConfig.API_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build().create(MovieService.class)));
    }
}