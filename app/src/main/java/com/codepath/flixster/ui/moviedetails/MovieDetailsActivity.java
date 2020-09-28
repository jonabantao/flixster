package com.codepath.flixster.ui.moviedetails;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.flixster.R;
import com.codepath.flixster.ui.movielistscreen.MovieListActivity;
import com.codepath.flixster.ui.movielistscreen.MovieListItem;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.parceler.Parcels;

public class MovieDetailsActivity extends YouTubeBaseActivity {

    TextView tvTitle;
    TextView tvOverview;
    RatingBar ratingBar;
    YouTubePlayerView ypvTrailer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        MovieListItem movie = Parcels.unwrap((getIntent().getParcelableExtra(MovieListItem.class.getSimpleName())));

        tvTitle = findViewById(R.id.movie_details_title_text_view);
        tvOverview = findViewById(R.id.movie_details_overview_text_view);
        ratingBar = findViewById(R.id.movie_details_rating_bar);
        ypvTrailer = findViewById(R.id.movie_details_trailer_youtube_view);

        if (movie != null) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            ratingBar.setRating((float) movie.getRating() / 2);


            final String id = "tKodtNFpzBA";

            ypvTrailer.initialize(getString(R.string.youtube_api_key), new YouTubePlayer.OnInitializedListener() {

                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                    youTubePlayer.loadVideo(id);
                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                    Log.e(MovieListActivity.class.getSimpleName(), "Error YouTube player");
                }
            });
        }
    }
}