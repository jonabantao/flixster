package com.codepath.flixster.ui.movielistscreen;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.flixster.R;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<MovieListItem> movies;

    int DEFAULT = 0;
    int POPULAR = 1;

    public class ListItemViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        TextView tvTitle;
        TextView tvOverview;

        public ListItemViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPoster = itemView.findViewById(R.id.movie_list_image_view);
            tvTitle = itemView.findViewById(R.id.movie_list_title_text_view);
            tvOverview = itemView.findViewById(R.id.movie_list_overview_text_view);
        }


        public void bind(MovieListItem movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            // movie_placeholder from popcorn.app
            Glide.with(context)
                    .load(getMovieImage(movie))
                    .placeholder(R.drawable.movie_placeholder)
                    .error(R.drawable.movie_placeholder)
                    .into(ivPoster);
        }

        private String getMovieImage(MovieListItem movie) {
            Resources resources = context.getResources();

            if (resources.getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                return movie.getPosterUrl();
            } else {
                return movie.getBackdropUrl();
            }
        }
    }

    public class BackdropViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBackdrop;
        
        public BackdropViewHolder(@NonNull View itemView) {
            super(itemView);
            
            ivBackdrop = itemView.findViewById(R.id.movie_list_popular_image_view);
        }

        public void bind(MovieListItem movie) {
            Glide.with(context)
                    .load(movie.getBackdropUrl())
                    .placeholder(R.drawable.movie_placeholder)
                    .error(R.drawable.movie_placeholder)
                    .centerCrop()
                    .into(ivBackdrop);
        }
    }

    public MovieListAdapter(Context context, List<MovieListItem> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public int getItemViewType(int position) {
        Double rating = movies.get(position).getRating();

        return rating.compareTo(7.5) > 0 ? POPULAR : DEFAULT;
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
            BackdropViewHolder viewHolder = (BackdropViewHolder) holder;
            viewHolder.bind(movie);
        } else {
            ListItemViewHolder viewHolder = (ListItemViewHolder) holder;
            viewHolder.bind(movie);
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
