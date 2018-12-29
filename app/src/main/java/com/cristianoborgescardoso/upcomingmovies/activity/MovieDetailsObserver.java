package com.cristianoborgescardoso.upcomingmovies.activity;


 interface MovieDetailsObserver {
    void setMovieTitle(String title);

    void setMovieOverview(String overview);

    void setMovieGenres(String genres);

    void setMovieReleaseDate(String releaseDate);

    void setMovieBackdropImage(String imagePath);

    void setMoviePosterImage(String imagePath);
}
