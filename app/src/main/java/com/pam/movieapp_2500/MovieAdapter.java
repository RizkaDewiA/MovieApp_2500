package com.pam.movieapp_2500;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ListViewHolder> {

    private final ArrayList<Movie> listMovie;
    private Movie movie;

    private OnItemClickCallback onItemClickCallback;

    public MovieAdapter(ArrayList<Movie> listMovie){
        this.listMovie = listMovie;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback){
        this.onItemClickCallback = onItemClickCallback;
    }


    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new ListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, final int position) {
        final Movie movie = listMovie.get(position);

        holder.txtTitle.setText(movie.getTitle());
        holder.txtYear.setText(movie.getReleaseDate());
        holder.txtOverview.setText(movie.getOverview());

        Glide.with(holder.itemView)
                .load("https://image.tmdb.org/t/p/w200/"+movie.getPosterPath())
                .into(holder.imgPoster);

        holder.btnDetail.setOnClickListener(view -> onItemClickCallback.onItemClicked(movie));
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle, txtYear, txtOverview;
        ImageView imgPoster;
        Button btnDetail;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtYear = itemView.findViewById(R.id.txtYear);
            txtOverview = itemView.findViewById(R.id.txtOverview);
            imgPoster = itemView.findViewById(R.id.imgPoster);
            btnDetail = itemView.findViewById(R.id.btnDetail);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Movie data);
    }
}
