package com.cristianoborgescardoso.upcomingmovies.modelJson;

import android.util.Log;

import com.cristianoborgescardoso.upcomingmovies.api.ApiConfig;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Dates implements Serializable {


    private static final long serialVersionUID = 851759427833218926L;
    private static final String TAG = Dates.class.getName();
    private static final DateFormat formatterFrom = new SimpleDateFormat(ApiConfig.DATE_SOURCE_FORMAT);
    private static final DateFormat formatterTo = SimpleDateFormat.getDateInstance(SimpleDateFormat.LONG, new Locale(ApiConfig.SYSTEM_LANGUAGE.split("-")[0], ApiConfig.SYSTEM_LANGUAGE.split("-")[1]));
    @SerializedName("maximum")
    @Expose
    private String maximum;
    @SerializedName("minimum")
    @Expose
    private String minimum;

    public static synchronized String getFormattedDate(String date) {
        try {
            //Log.d(TAG,String.format("ReleaseDate %s",formatterFrom.parse(date)));
            return formatterTo.format(formatterFrom.parse(date));
        } catch (ParseException e) {
            Log.e(TAG, "Error during date formatting", e);
        }
        return date;
    }

    public String getMaximum() {
        return maximum;
    }

    public void setMaximum(String maximum) {
        this.maximum = maximum;
    }

    public Dates withMaximum(String maximum) {
        this.maximum = maximum;
        return this;
    }

    public String getMinimum() {
        return minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("maximum", maximum)
                .append("minimum", minimum)
                .toString();
    }
}