package com.cristianoborgescardoso.upcomingmovies.modelJson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class ProductionCountry implements Serializable {

    private static final long serialVersionUID = -8969541951550301692L;
    @SerializedName("iso_3166_1")
    @Expose
    private String iso31661;
    @SerializedName("name")
    @Expose
    private String name;

    public String getIso31661() {
        return iso31661;
    }

    public void setIso31661(String iso31661) {
        this.iso31661 = iso31661;
    }

    public ProductionCountry withIso31661(String iso31661) {
        this.iso31661 = iso31661;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductionCountry withName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("iso31661", iso31661)
                .append("name", name)
                .toString();
    }
}