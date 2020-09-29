package com.codepath.flixster.util;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.codepath.flixster.R;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class BindingAdapterUtils {
    @BindingAdapter({ "movieImageUrl" })
    public static void loadMovieImage(ImageView view, String url) {
        Glide.with(view.getContext())
                .load(url)
                .transform(new RoundedCornersTransformation(10, 0))
                .placeholder(R.drawable.movie_placeholder)
                .error(R.drawable.movie_placeholder)
                .into(view);
    }
}
