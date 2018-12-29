package com.cristianoborgescardoso.upcomingmovies.adapter;

import android.arch.paging.PagedListAdapter;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cristianoborgescardoso.upcomingmovies.R;
import com.cristianoborgescardoso.upcomingmovies.activity.MovieDetailsActivity;
import com.cristianoborgescardoso.upcomingmovies.api.ApiConfig;
import com.cristianoborgescardoso.upcomingmovies.dataSource.GenreDataSource;
import com.cristianoborgescardoso.upcomingmovies.modelJson.Dates;
import com.cristianoborgescardoso.upcomingmovies.modelJson.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainRecyclerViewAdapter extends PagedListAdapter<Movie, RecyclerView.ViewHolder> {
    private static final String TAG = MainRecyclerViewAdapter.class.getName();

    public MainRecyclerViewAdapter() {
        super(Movie.DIFF_CALL);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view;
        view = inflater.inflate(R.layout.movie_item, viewGroup, false);
        return new MainRecyclerViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        MainRecyclerViewHolder movieViewHolder = (MainRecyclerViewHolder) holder;
        Movie movie = getItem(i);
        if (movie != null) {
            movieViewHolder.bind(movie);
        }
//        else {
        // Null defines a placeholder item - PagedListAdapter automatically
        // invalidates this row when the actual object is loaded from the
        // database.
        //movieViewHolder.clear();
//        }
    }

    private void bindGenres(List<Integer> genresIds, TextView textView) {
        bindText(textView, GenreDataSource.getGenreByIdList(genresIds), textView.getResources().getString(R.string.genre_is_missing));
    }

    private void bindText(TextView textView, String text, String resourceMissingMessage) {
        try {
            if (text == null || text.trim().isEmpty()) {
                text = resourceMissingMessage;
            }
            textView.setText(text);
        } catch (Exception ex) {
            Log.e(TAG, String.format("Error While binding MovieItem text: '%s'", text), ex);
        }
    }

    private void bindImage(ImageView imageView, String imageURL, ApiConfig.ImageSize imageSize) {
        try {
            Picasso.get().load(ApiConfig.getPosterBaseURL(imageSize) + imageURL).error(R.drawable.ic_no_image_avaliable_45dp).placeholder(R.drawable.ic_place_holder_gray_24dp).into(imageView);
        } catch (Exception ex) {
            Log.e(TAG, "Error While binding MovieItem image", ex);
        }
    }

    class MainRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView tvMovieTitle;
        private final TextView tvMovieGenre;
        private final TextView tvMovieReleaseDate;
        private final ImageView ivMoviePoster;
        private final View itemView;
        private Movie movie;

        private MainRecyclerViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            tvMovieTitle = itemView.findViewById(R.id.tvMovieTitle);
            tvMovieGenre = itemView.findViewById(R.id.tvMovieGenre);
            tvMovieReleaseDate = itemView.findViewById(R.id.tvMovieReleaseDate);
            ivMoviePoster = itemView.findViewById(R.id.ivMoviePoster);
            itemView.setOnClickListener(this);
        }

        private void bind(Movie movie) {
            this.movie = movie;
            bindGenres(movie.getGenreIds(), tvMovieGenre);
            bindText(tvMovieTitle, movie.getTitle(), tvMovieTitle.getResources().getString(R.string.title_is_missing));
            bindText(tvMovieReleaseDate, Dates.getFormattedDate(movie.getReleaseDate()), tvMovieReleaseDate.getResources().getString(R.string.release_date_is_missing));
            bindImage(ivMoviePoster, movie.getPosterPath(), ApiConfig.PosterSize.w154);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), MovieDetailsActivity.class);
            intent.putExtra(Movie.class.getCanonicalName(), movie);
            v.getContext().startActivity(intent);
        }

        public Movie getMovie() {
            return movie;
        }
    }

}
