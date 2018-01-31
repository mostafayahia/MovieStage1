package tutorial.elmasry.moviestage1;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;

import tutorial.elmasry.moviestage1.model.MovieInfo;
import tutorial.elmasry.moviestage1.utilities.HelperUtils;
import tutorial.elmasry.moviestage1.utilities.NetworkUtils;
import tutorial.elmasry.moviestage1.utilities.TheMovieDBJsonUtils;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private MovieInfo[] mMovieInfoArray;
    private MovieAdapter mMovieAdapter;
    private RecyclerView mRecyclerView;
    private ProgressBar mLoadingIndicator;
    private TextView mErrorNoConnectionTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        mErrorNoConnectionTv = findViewById(R.id.tv_error_no_connection);

        mMovieAdapter = new MovieAdapter(this);

        mRecyclerView = findViewById(R.id.rv_poster);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setReverseLayout(false);

        mRecyclerView.setLayoutManager(gridLayoutManager);

        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setAdapter(mMovieAdapter);

        loadMoviesData();
    }

    private void loadMoviesData() {

        if (HelperUtils.isDeviceOnline(this)) {
            hideErrorNoConnectionView();
            new FetchMovieInfo().execute(NetworkUtils.SORT_POPULAR);
        } else {
            showErrorNoConnectionView();
        }

    }


    private class FetchMovieInfo extends AsyncTask<Integer, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Integer... integers) {

            int sortBy = integers[0];

            try {

                return NetworkUtils.getResponseFromHttpUrl(NetworkUtils.buildUrl(sortBy));

            } catch (IOException e) {

                e.printStackTrace();
                Log.e(LOG_TAG, "error in getting json response for movies");
                return null;

            }

        }

        @Override
        protected void onPostExecute(String jsonResponse) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            try {
                mMovieInfoArray = TheMovieDBJsonUtils.getMovieInfoArrayFromJson(jsonResponse);
                mMovieAdapter.setMovieInfoArray(mMovieInfoArray);
            } catch (JSONException e) {
                Log.e(LOG_TAG, "error in getting movie info array from json response");
                e.printStackTrace();
            }
        }
    }


    private void showErrorNoConnectionView() {
        mErrorNoConnectionTv.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    private void hideErrorNoConnectionView() {
        mErrorNoConnectionTv.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

}
