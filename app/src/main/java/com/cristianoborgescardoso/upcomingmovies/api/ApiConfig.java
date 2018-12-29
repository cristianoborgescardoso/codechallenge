package com.cristianoborgescardoso.upcomingmovies.api;

import android.content.Context;
import android.content.res.Configuration;

import com.cristianoborgescardoso.upcomingmovies.R;
import com.cristianoborgescardoso.upcomingmovies.dataSource.GenreDataSource;

import java.util.Locale;

public class ApiConfig {
    public static final int API_DEFAULT_PAGE_KEY = 1;
    private static final String TAG = ApiConfig.class.getName();
    public static String API_BASE_URL = "https://api.themoviedb.org/3/";
    public static String API_KEY = "1f54bd990f1cdfb230adb312546d765d";
    public static String SYSTEM_REGION = "BR";
    public static String SYSTEM_LANGUAGE = "pt-BR";
    public static String DATE_SOURCE_FORMAT = "yyyy-MM-dd";
    private static String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";//https://developers.themoviedb.org/3/getting-started/images

    public static String getBackdropBaseURL(ImageSize backdropSize) {
        return IMAGE_BASE_URL + backdropSize.toString() + "/";
    }

    public static String getLogoBaseURL(ImageSize logoSize) {
        return IMAGE_BASE_URL + logoSize.toString() + "/";
    }

    public static String getPosterBaseURL(ImageSize posterSize) {
        return IMAGE_BASE_URL + posterSize.toString() + "/";
    }

    public static synchronized void updateApiConfigStringXML(Context context) {
        API_BASE_URL = context.getString(R.string.api_base_url);
        API_KEY = context.getString(R.string.api_key);
        DATE_SOURCE_FORMAT = context.getString(R.string.date_source_format);
        IMAGE_BASE_URL = context.getString(R.string.image_base_url);

        if (context.getResources().getBoolean(R.bool.use_default_language_and_region)) {
            SYSTEM_REGION = context.getString(R.string.default_region);
            SYSTEM_LANGUAGE = context.getString(R.string.default_language);
            forceLocale(context);

        } else {
            ApiConfig.SYSTEM_REGION = Locale.getDefault().getCountry();
            ApiConfig.SYSTEM_LANGUAGE = String.format("%s-%s", Locale.getDefault().getLanguage(), Locale.getDefault().getCountry());
        }

        //must be called after SYSTEM_REGION be configurated
        GenreDataSource.updateGenresMap(context.getResources().getStringArray(R.array.genres_array));
    }

    //from: https://stackoverflow.com/questions/4985805/set-locale-programmatically
    public static void forceLocale(Context context) {
        if (context.getResources().getBoolean(R.bool.use_default_language_and_region)) {
            Locale locale = new Locale(SYSTEM_LANGUAGE.split("-")[0]);
            Locale.setDefault(locale);
            Configuration config = context.getResources().getConfiguration();
            config.locale = locale;
            context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        }
    }

    public enum BackdropSize implements ImageSize {
        w300,
        w780,
        w1280,
        original
    }

    public enum LogoSize implements ImageSize {
        w45,
        w92,
        w154,
        w185,
        w300,
        w500,
        original
    }

    public enum PosterSize implements ImageSize {
        w92,
        w154,
        w185,
        w342,
        w500,
        w780,
        original
    }

    public interface ImageSize {

    }


}
