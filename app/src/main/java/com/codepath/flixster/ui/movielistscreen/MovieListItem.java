package com.codepath.flixster.ui.movielistscreen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieListItem {
    private int id;
    private String title;
    private String overview;
    private String posterUrl;

    public MovieListItem(JSONObject movieObject) throws JSONException {
        this.id = movieObject.getInt("id");
        this.title = movieObject.getString("title");
        this.overview = movieObject.getString("overview");
        this.posterUrl = movieObject.getString("poster_path");
    }

    public static List<MovieListItem> fromJSONArray(JSONArray moviesArray) throws JSONException {
        List<MovieListItem> movies = new ArrayList<>();

        for (int i = 0; i < moviesArray.length(); i++) {
            movies.add(new MovieListItem(moviesArray.getJSONObject(i)));
        }

        return movies;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterUrl() {
        return "https://image.tmdb.org/t/p/original/" + posterUrl;
    }

    @Override
    public String toString() {
        return String.format("%s: %s, URL: %s", title, overview, posterUrl);
    }
}
