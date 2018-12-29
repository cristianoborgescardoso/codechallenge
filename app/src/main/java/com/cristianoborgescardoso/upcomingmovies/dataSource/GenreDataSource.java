package com.cristianoborgescardoso.upcomingmovies.dataSource;

import android.util.Log;

import com.cristianoborgescardoso.upcomingmovies.api.ApiConfig;
import com.cristianoborgescardoso.upcomingmovies.api.MovieService;
import com.cristianoborgescardoso.upcomingmovies.modelJson.Genre;
import com.cristianoborgescardoso.upcomingmovies.modelJson.GenreResponse;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class GenreDataSource {

    private static final String TAG = GenreDataSource.class.getName();

    private static final ConcurrentMap<Integer, String> genresMap = new ConcurrentHashMap<>();

    private static synchronized String getGenreById(Integer genreId) {
        return genresMap.containsKey(genreId) ? genresMap.get(genreId) : "";
    }

    public static synchronized String getGenreByIdList(List<Integer> genreIdList) {
        if (genreIdList.isEmpty()) {
            return "";
        }
        StringBuilder genresText = new StringBuilder();
        genresText.append(getGenreById(genreIdList.get(0)));
        for (int i = 1; i < genreIdList.size(); i++) {
            genresText.append(", ");
            genresText.append(getGenreById(genreIdList.get(i)));
        }
        return genresText.toString();
    }

    private static void updateGenresMap(List<Genre> genreList) {
        for (Genre genre : genreList) {
            genresMap.put(genre.getId(), genre.getName());
        }
    }

    public static void updateGenresMap(String[] genreArray) {

        try {
            for (String genre : genreArray) {
                genresMap.put(Integer.valueOf(genre.split(":")[0]), genre.split(":")[1]);
            }

        } catch (Exception ex) {

            Log.e(TAG, "Error while update genresMap",ex );
        }


    }

    public synchronized static void updateGenresFromServer(MovieService movieService) {
        Call<GenreResponse> genreResponseCall = movieService.getGenres(ApiConfig.API_KEY, ApiConfig.SYSTEM_LANGUAGE);
        genreResponseCall.enqueue(new Callback<GenreResponse>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {
                if (response.isSuccessful() && response.body()!= null && response.body().getGenres()!= null) {

                    GenreResponse genreResponse = response.body();
                    updateGenresMap(genreResponse.getGenres());
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<GenreResponse> call, Throwable t) {

            }
        });
    }

}
