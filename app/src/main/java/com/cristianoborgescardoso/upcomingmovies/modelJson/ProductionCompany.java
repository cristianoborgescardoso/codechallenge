package com.cristianoborgescardoso.upcomingmovies.modelJson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class ProductionCompany implements Serializable {

    private static final long serialVersionUID = -3554594478405364654L;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("logo_path")
    @Expose
    private Object logoPath;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("origin_country")
    @Expose
    private String originCountry;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProductionCompany withId(Integer id) {
        this.id = id;
        return this;
    }

    public Object getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(Object logoPath) {
        this.logoPath = logoPath;
    }

    public ProductionCompany withLogoPath(Object logoPath) {
        this.logoPath = logoPath;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductionCompany withName(String name) {
        this.name = name;
        return this;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public ProductionCompany withOriginCountry(String originCountry) {
        this.originCountry = originCountry;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("logoPath", logoPath)
                .append("name", name)
                .append("originCountry", originCountry)
                .toString();
    }
}