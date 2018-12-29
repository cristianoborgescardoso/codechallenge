package com.cristianoborgescardoso.upcomingmovies.modelJson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractMovieResponse implements Serializable {

    private static final long serialVersionUID = -7509868526071789346L;
    @SerializedName("results")
    @Expose
    private List<Movie> movies;
    @SerializedName("page")
    @Expose
    private int page;
    @SerializedName("total_results")
    @Expose
    private int totalResults;
    @SerializedName("dates")
    @Expose
    private Dates dates;
    @SerializedName("total_pages")
    @Expose
    private int totalPages;

    public List<Movie> getMovieResponses() {
        return movies;
    }

    public void setMovieResponses(List<Movie> movies) {
        this.movies = movies;
    }

    public AbstractMovieResponse withResults(List<Movie> movies) {
        this.movies = movies;
        return this;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public AbstractMovieResponse withPage(int page) {
        this.page = page;
        return this;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public AbstractMovieResponse withTotalResults(int totalResults) {
        this.totalResults = totalResults;
        return this;
    }

    public Dates getDates() {
        return dates;
    }

    public void setDates(Dates dates) {
        this.dates = dates;
    }

    public AbstractMovieResponse withDates(Dates dates) {
        this.dates = dates;
        return this;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public AbstractMovieResponse withTotalPages(int totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("movies", movies)
                .append("page", page)
                .append("totalResults", totalResults)
                .append("dates", dates)
                .append("totalPages", totalPages)
                .toString();
    }
}
