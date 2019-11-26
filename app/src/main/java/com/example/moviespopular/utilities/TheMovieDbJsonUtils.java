package com.example.moviespopular.utilities;

import android.content.Context;

import com.example.moviespopular.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TheMovieDbJsonUtils {

    public static Movie[] getMovieInformationFromJson(Context context, String json) throws JSONException{

        final String TMDB_BASE_URL = "http://image.tmdb.org/t/p/"; // The Base URL
        final String TMDB_POSTER_SIZE = "w342";//‘size’ the image thumbnail


        final String TMDB_RESULTS = "results";
        final String TMDB_POSTER_PATH = "poster_path";
        final String TMDB_TITLE = "title";
        final String TMDB_VOTE = "vote_average";
        final String TMDB_OVERVIEW = "overview";
        final String TMDB_RELEASE_DATE = "release_date";


        //Identify the fields in the JSON data
        JSONObject movieJson = new JSONObject(json);
        JSONArray movieArray = movieJson.getJSONArray(TMDB_RESULTS);

        Movie[] movieResults = new Movie[movieArray.length()];

        for (int i = 0; i < movieArray.length(); i++){
            String poster_path, title, vote_average, overview, release_date;

            Movie movie = new Movie();

            poster_path = movieArray.getJSONObject(i).optString(TMDB_POSTER_PATH);
            title = movieArray.getJSONObject(i).optString(TMDB_TITLE);
            release_date = movieArray.getJSONObject(i).optString(TMDB_RELEASE_DATE);
            vote_average = movieArray.getJSONObject(i).optString(TMDB_VOTE);
            overview =  movieArray.getJSONObject(i).optString(TMDB_OVERVIEW);

            movie.setPoster(TMDB_BASE_URL + TMDB_POSTER_SIZE + poster_path);
            movie.setTitle(title);
            movie.setRelease(release_date);
            movie.setRate(vote_average);
            movie.setOverview(overview);

            movieResults[i] = movie;
        }
        return movieResults;
    }
}
