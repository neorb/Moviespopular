package com.example.moviespopular;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityDetail extends AppCompatActivity {

    @BindView(R.id.iv_detail_movie_poster)
    ImageView mMoviePosterDisplay;

    @BindView(R.id.tv_detail_title)
    TextView mMovieTitleDisplay;

    @BindView(R.id.tv_detail_rate)
    TextView mMovieRateDisplay;

    @BindView(R.id.tv_detail_release_date)
    TextView mMovieReleaseDisplay;

    @BindView(R.id.tv_plot_synopsis)
    TextView mMoviePlotSynopsisDisplay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        //Using the Butterknife library for view injection
        ButterKnife.bind(this);

        String poster = getIntent().getStringExtra("poster");
        String title = getIntent().getStringExtra("title");
        String rate = getIntent().getStringExtra("rate");
        String release = getIntent().getStringExtra("release");
        String overview = getIntent().getStringExtra("overview");

        mMovieTitleDisplay.setText(title);
        mMoviePlotSynopsisDisplay.setText(overview);
        mMovieRateDisplay.setText(rate);
        mMovieReleaseDisplay.setText(release);
        //use Picasso to easily load album art thumbnails into  views
        Picasso.get()
                .load(poster)
                .placeholder(R.drawable.movie)
                .error(R.drawable.cloud_off)
                .into(mMoviePosterDisplay);
    }
}
