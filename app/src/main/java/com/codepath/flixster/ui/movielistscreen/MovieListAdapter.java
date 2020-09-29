package com.codepath.flixster.ui.movielistscreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.flixster.R;
import com.codepath.flixster.databinding.ItemMoviesListBackdropBinding;
import com.codepath.flixster.databinding.ItemMoviesListBinding;
import com.codepath.flixster.ui.moviedetails.MovieDetailsActivity;

import org.parceler.Parcels;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<MovieListItem> movies;

    int DEFAULT = 0;
    int POPULAR = 1;

    public class ListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemMoviesListBinding binding;

        public ListItemViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = ItemMoviesListBinding.bind(itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();

            if (pos == RecyclerView.NO_POSITION) {
                return;
            }

            MovieListItem movie = movies.get(pos);
            Intent intent = new Intent(context, MovieDetailsActivity.class);
            intent.putExtra(MovieListItem.class.getSimpleName(), Parcels.wrap(movie));

            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    (Activity) context,
                    Pair.create((View) binding.movieListImageView, "moviePoster"),
                    Pair.create((View) binding.movieListTitleTextView, "movieTitle"),
                    Pair.create((View) binding.movieListOverviewTextView, "movieOverview")
            );

            context.startActivity(intent, options.toBundle());
        }
    }

    public class BackdropViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemMoviesListBackdropBinding binding;

        public BackdropViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = ItemMoviesListBackdropBinding.bind(itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();

            if (pos == RecyclerView.NO_POSITION) {
                return;
            }

            MovieListItem movie = movies.get(pos);
            Intent intent = new Intent(context, MovieDetailsActivity.class);
            intent.putExtra(MovieListItem.class.getSimpleName(), Parcels.wrap(movie));
            context.startActivity(intent);
        }
    }

    public MovieListAdapter(Context context, List<MovieListItem> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public int getItemViewType(int position) {
        MovieListItem movie = movies.get(position);

        return movie.isPopular() ? POPULAR : DEFAULT;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;

        if (viewType == POPULAR) {
            view = inflater.inflate(R.layout.item_movies_list_backdrop, parent, false);
            return new BackdropViewHolder(view);
        } else {
            view = inflater.inflate(R.layout.item_movies_list, parent, false);
            return new ListItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MovieListItem movie = movies.get(position);

        if (holder.getItemViewType() == POPULAR) {
            ((BackdropViewHolder) holder).binding.setMovie(movie);
            ((BackdropViewHolder) holder).binding.executePendingBindings();
        } else {
            ((ListItemViewHolder) holder).binding.setMovie(movie);
            ((ListItemViewHolder) holder).binding.executePendingBindings();
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
