package com.codepath.flixster.ui.moviedetails;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.codepath.flixster.R;
import com.codepath.flixster.ui.movielistscreen.MovieListItem;

import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity {

    private MovieListItem movie;

    TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        movie = Parcels.unwrap((getIntent().getParcelableExtra(MovieListItem.class.getSimpleName())));

        t = (TextView) findViewById(R.id.textView);
        t.setText(movie.getTitle());
    }
}