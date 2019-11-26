package com.example.moviespopular;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.moviespopular.model.Movie;
import com.example.moviespopular.utilities.NetworkUtils;
import com.example.moviespopular.utilities.TheMovieDbJsonUtils;

import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;



public class MainActivity extends AppCompatActivity implements MoviesAdapter.MovieAdapterOnClickHandler {

    private RecyclerView mRecyclerView;
    private MoviesAdapter mMoviesAdapter;
    private Movie[] jsonMovieData;

    @BindView(R.id.tv_error_message)
    TextView mErrorMessage;
    @BindView(R.id.pb_loading_indicator)
    ProgressBar mLoadingIndicator;

    String query = "popular";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movies);

        ButterKnife.bind(this);


        int mNoOfColumns = calculateNoOfColums(getApplicationContext());

        //using GridLayout to displayed the thumbnails in main layout
        GridLayoutManager layoutManager = new GridLayoutManager(this, mNoOfColumns);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setAdapter(mMoviesAdapter);

        loadMovieData();
    }

    private void loadMovieData() {

        String theMoviewDbQueryType = query;
        showJsonDataResults();
        new FetchMovieTask().execute(theMoviewDbQueryType);
    }

    private void showJsonDataResults() {
        mErrorMessage.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessage.setVisibility(View.VISIBLE);
    }

    public class FetchMovieTask extends AsyncTask<String, Void, Movie[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected Movie[] doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }

            String sortBy = params[0];
            URL movieRequestUrl = NetworkUtils.buildUrl(sortBy);

            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);

                jsonMovieData
                        = TheMovieDbJsonUtils.getMovieInformationFromJson(MainActivity.this, jsonMovieResponse);

                return jsonMovieData;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Movie[] movies) {

            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movies != null) {
                showJsonDataResults();

                mMoviesAdapter = new MoviesAdapter(movies, MainActivity.this);
                mRecyclerView.setAdapter(mMoviesAdapter);
            } else {
                showErrorMessage();
            }
        }
    }

    //using spinner or settings menu to toggle the sort order of the movies by: most popular, highest rated.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemSelected = item.getItemId();

        if (menuItemSelected == R.id.action_popular) {
            query = "popular";
            loadMovieData();
            return true;
        }
        if (menuItemSelected == R.id.action_top_rate) {
            query = "top_rated";
            loadMovieData();
            return true;
        }
        if (menuItemSelected == R.id.action_about) {
            Intent startAboutActivity =  new Intent(this, AboutActivity.class);
            startActivity(startAboutActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static int calculateNoOfColums(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColums = (int) (dpWidth / 180);
        return noOfColums;
    }

    @Override
    public void onClick(int adapterPosition) {

        Context context = this;
        Class destinationClass = ActivityDetail.class;

        Intent intentToStartActivityDetail = new Intent(context, destinationClass);
        intentToStartActivityDetail.putExtra(Intent.EXTRA_TEXT, adapterPosition);
        intentToStartActivityDetail.putExtra("title", jsonMovieData[adapterPosition].getTitle());
        intentToStartActivityDetail.putExtra("poster", jsonMovieData[adapterPosition].getPoster());
        intentToStartActivityDetail.putExtra("rate", jsonMovieData[adapterPosition].getRate());
        intentToStartActivityDetail.putExtra("release", jsonMovieData[adapterPosition].getRelease());
        intentToStartActivityDetail.putExtra("overview", jsonMovieData[adapterPosition].getOverview());

        startActivity(intentToStartActivityDetail);

    }


}
