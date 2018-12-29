package com.cristianoborgescardoso.upcomingmovies.activity;

import android.animation.ObjectAnimator;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cristianoborgescardoso.upcomingmovies.R;
import com.cristianoborgescardoso.upcomingmovies.adapter.MainRecyclerViewAdapter;
import com.cristianoborgescardoso.upcomingmovies.api.ApiConfig;
import com.cristianoborgescardoso.upcomingmovies.api.NetworkState;
import com.cristianoborgescardoso.upcomingmovies.model.AbstractMovieModel;
import com.cristianoborgescardoso.upcomingmovies.modelJson.Movie;




public abstract class AbstractMovieListActivity extends AppCompatActivity implements NetworkStateLiveDataObserver {

    private final String TAG;
    private AbstractMovieModel moviewModel;
    private MainRecyclerViewAdapter adapter;
    private RecyclerView rvMain;
    private View viewNoData;
    private ProgressBar pbConnecting;
    private Button btTryAgain;
    private TextView tvMessage;
    private final Class<? extends AbstractMovieModel> movieModelClass;

    public AbstractMovieListActivity(String TAG, Class<? extends AbstractMovieModel> movieModelClass) {
        this.TAG = TAG;
        this.movieModelClass = movieModelClass;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ApiConfig.updateApiConfigStringXML(getBaseContext()); // getBaseContext() is necessary to ApiConfig.forceLocale(Context context)

        setTitle(getResources().getString(R.string.app_name));

        setContentView(R.layout.activity_main);

        rvMain = findViewById(R.id.rvMain);
        viewNoData = findViewById(R.id.network_filed);
        pbConnecting = findViewById(R.id.pb_connecting);
        btTryAgain = findViewById(R.id.bt_try_again);
        tvMessage = findViewById(R.id.tv_message);

        rvMain.setLayoutManager(new LinearLayoutManager(this));

        loadInitial();

        moviewModel = ViewModelProviders.of(this).get(movieModelClass);

        adapter = new MainRecyclerViewAdapter();

        moviewModel.getMoviesPagedList().observe(this, new Observer<PagedList<Movie>>() {
            @Override
            public void onChanged(@Nullable PagedList<Movie> moviePagedList) {
                Log.d(TAG, String.format("onChanged: moviePagedList.size(): '%s'", (moviePagedList==null)? "null":moviePagedList.size()));
                adapter.submitList(moviePagedList);
            }
        });

        moviewModel.getNetworkStateLiveData().observe(this, new Observer<NetworkState>() {
            @Override
            public void onChanged(@Nullable NetworkState networkState) {
                Log.d(TAG, "onChanged: network state changed");
                switch (networkState.getStatus()) {
                    case SUCCESS: {
                        hideNoInternetPanel();
                        break;
                    }
                    case FAILED: {
                        displayNoInternetPanel();
                        break;
                    }
                    default: {
                        break;
                    }
                }
            }
        });
        rvMain.setAdapter(adapter);

        rvMain.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
    }

    @Override
    public void hideNoInternetPanel() {
        viewNoData.setVisibility(View.GONE);
        rvMain.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayNoInternetPanel() {
        stopAnimation();
        viewNoData.setVisibility(View.VISIBLE);
        rvMain.setVisibility(View.INVISIBLE);
        tvMessage.setVisibility(View.VISIBLE);
        btTryAgain.setVisibility(View.VISIBLE);
    }

    private void startAnimation() {
        btTryAgain.setEnabled(false);
        pbConnecting.setVisibility(View.VISIBLE);
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(pbConnecting, "progress", 100, 0);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.start();
    }

    private void stopAnimation() {
        btTryAgain.setEnabled(true);
        Toast.makeText(pbConnecting.getContext(), getResources().getString(R.string.failed_to_access_the_server), Toast.LENGTH_SHORT).show();
        pbConnecting.clearAnimation();
        pbConnecting.setVisibility(View.INVISIBLE);
    }

    private void loadInitial() {
        viewNoData.setVisibility(View.VISIBLE);
        tvMessage.setVisibility(View.INVISIBLE);
        btTryAgain.setVisibility(View.INVISIBLE);
        startAnimation();
        rvMain.setVisibility(View.INVISIBLE);
    }

    public void tryToConnectAgain(View view) {
        Toast.makeText(view.getContext(), getResources().getString(R.string.trying_to_connect), Toast.LENGTH_SHORT).show();
        startAnimation();
        moviewModel.getAbstractDataSourceFactory().invalidateDataSource();
    }


}
