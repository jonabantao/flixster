package com.codepath.flixster.ui.movielistscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.codepath.flixster.R;
import com.codepath.flixster.databinding.ActivityMoviesListBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MovieListActivity extends AppCompatActivity {

    private RecyclerView.Adapter<RecyclerView.ViewHolder> adapter;
    private ActivityMoviesListBinding binding;

    private final String TAG = this.getClass().getSimpleName();
    private List<MovieListItem> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movies_list);

        setupRecyclerView();

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(getString(R.string.api_url), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONObject response = json.jsonObject;

                try {
                    JSONArray results = response.getJSONArray("results");
                    movies.addAll(MovieListItem.fromJSONArray(results));
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.e(TAG, "Results failed", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(TAG, "Failed", throwable);
            }
        });
    }

    private void setupRecyclerView() {
        movies = new ArrayList<>();
        adapter = new MovieListAdapter(this, movies);

        RecyclerView recyclerView = binding.movieListRecyclerView;
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}