package com.codepath.flixster.ui.movielistscreen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class MovieListItem {
    public String title;
    public String overview;
    public String posterUrl;
    public String backdropUrl;
    public double rating;

    public MovieListItem() {}

    public MovieListItem(JSONObject movieObject) throws JSONException {
        this.title = movieObject.getString("title");
        this.overview = movieObject.getString("overview");
        this.posterUrl = movieObject.getString("poster_path");
        this.backdropUrl = movieObject.getString("backdrop_path");
        this.rating = movieObject.getDouble("vote_average");
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
        return "https://image.tmdb.org/t/p/w780" + posterUrl;
    }

    public String getBackdropUrl() {
        return "https://image.tmdb.org/t/p/w780" + backdropUrl;
    }

    public double getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return String.format("%s: %s, URL: %s", title, overview, posterUrl);
    }
}
