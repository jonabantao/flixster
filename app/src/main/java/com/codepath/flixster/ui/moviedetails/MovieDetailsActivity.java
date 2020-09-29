package com.codepath.flixster.ui.moviedetails;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.codepath.flixster.R;
import com.codepath.flixster.ui.movielistscreen.MovieListActivity;
import com.codepath.flixster.ui.movielistscreen.MovieListItem;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.Locale;

import okhttp3.Headers;

public class MovieDetailsActivity extends YouTubeBaseActivity {

    private final String TAG = this.getClass().getSimpleName();

    TextView tvTitle;
    TextView tvOverview;
    RatingBar ratingBar;
    YouTubePlayerView ypvTrailer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        final MovieListItem movie = Parcels.unwrap((getIntent().getParcelableExtra(MovieListItem.class.getSimpleName())));

        tvTitle = findViewById(R.id.movie_details_title_text_view);
        tvOverview = findViewById(R.id.movie_details_overview_text_view);
        ratingBar = findViewById(R.id.movie_details_rating_bar);
        ypvTrailer = findViewById(R.id.movie_details_trailer_youtube_view);

        if (movie != null) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            ratingBar.setRating((float) movie.getRating() / 2);

            AsyncHttpClient client = new AsyncHttpClient();

            client.get(String.format(Locale.US, "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed", movie.getId()), new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Headers headers, JSON json) {
                    try {
                        String id = getYouTubeId(json.jsonObject);

                        if (id != null) {
                            initializeYouTubeView(id, movie.isPopular());
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "Failed during fetch", e);
                    }
                }

                @Override
                public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                    Log.e(TAG, "Failed", throwable);
                }
            });
        }
    }

    private String getYouTubeId(JSONObject response) throws JSONException {
        JSONArray results = response.getJSONArray("results");

        for (int i = 0; i < results.length(); i++) {
            JSONObject result = results.getJSONObject(i);

            if (result.getString("site").equals("YouTube")) {
                return result.getString("key");
            }
        }

        return null;
    }

    private void initializeYouTubeView(final String id, final boolean isMoviePopular) {
        ypvTrailer.initialize(getString(R.string.youtube_api_key), new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (isMoviePopular) {
                    youTubePlayer.loadVideo(id);
                } else {
                    youTubePlayer.cueVideo(id);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.e(TAG, "Error YouTube player");
            }
        });
    }
}