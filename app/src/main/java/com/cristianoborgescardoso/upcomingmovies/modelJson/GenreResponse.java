package com.cristianoborgescardoso.upcomingmovies.modelJson;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

public class GenreResponse implements Serializable {

    private static final long serialVersionUID = -3885767802933744930L;
    @SerializedName("genres")
    @Expose
    private List<Genre> genres;

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public GenreResponse withGenres(List<Genre> genres) {
        this.genres = genres;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("genres", genres)
                .toString();
    }
}
