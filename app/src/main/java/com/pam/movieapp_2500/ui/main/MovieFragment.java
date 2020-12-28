package com.pam.movieapp_2500.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pam.movieapp_2500.ApiClient;
import com.pam.movieapp_2500.ApiService;
import com.pam.movieapp_2500.DetailActivity;
import com.pam.movieapp_2500.MainActivity;
import com.pam.movieapp_2500.Movie;
import com.pam.movieapp_2500.MovieAdapter;
import com.pam.movieapp_2500.MovieResponse;
import com.pam.movieapp_2500.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieFragment extends Fragment {

    private ArrayList<Movie> listMovie;
    private RecyclerView rvMovie;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        rvMovie = view.findViewById(R.id.rv_movie);
        rvMovie.setHasFixedSize(true);
        rvMovie.setLayoutManager(new LinearLayoutManager(getContext()));

        ApiService service = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<MovieResponse> call = service.getPlayingMovie("d58845c9d53da3e216a0a21300e1e90a");
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                listMovie = response.body().getResults();

                MovieAdapter movieAdapter = new MovieAdapter(listMovie);
                rvMovie.setAdapter(movieAdapter);

                movieAdapter.setOnItemClickCallback(data -> {
                    Intent moveIntent = new Intent(getActivity(), DetailActivity.class);
                    moveIntent.putExtra(DetailActivity.ITEM_EXTRA, data);
                    startActivity(moveIntent);
                });
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });

        return view;
    }
}