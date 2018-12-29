package com.cristianoborgescardoso.upcomingmovies.modelJson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

public class MovieMediaResponse implements Serializable {

    private static final long serialVersionUID = 2016707725606096389L;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<MovieMedia> results;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MovieMediaResponse withId(Integer id) {
        this.id = id;
        return this;
    }

    public List<MovieMedia> getMovieMedia() {
        return results;
    }

    public void setMovieMedia(List<MovieMedia> results) {
        this.results = results;
    }

    public MovieMediaResponse withResults(List<MovieMedia> results) {
        this.results = results;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("results", results)
                .toString();
    }
}