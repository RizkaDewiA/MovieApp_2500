package com.pam.movieapp_2500;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ListViewHolder> {

    private final ArrayList<Movie> listMovie;
    private Movie movie;

    private Context context;

    public FavoriteAdapter(ArrayList<Movie> listMovie, Context context){
        this.listMovie = listMovie;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_favorite, parent, false);
        return new FavoriteAdapter.ListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        movie = listMovie.get(position);

        holder.txtTitle.setText(movie.getTitle());
        holder.txtYear.setText(movie.getReleaseDate());
        holder.txtOverview.setText(movie.getOverview());

        Glide.with(holder.itemView)
                .load(movie.getPosterPath())
                .into(holder.imgPoster);

        //untuk testing link apa sudah terhubung
        Log.d("Testing Adapter", "Testing Link"+movie.getPosterPath());

        holder.btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movie = listMovie.get(position);

                DatabaseHelper db = new DatabaseHelper(context);
                db.deleteFavorite(String.valueOf(movie.getId()));

                notifyItemRemoved(position);
                listMovie.remove(position);

                Toast.makeText(context, "data yang terhapus : " + movie.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle, txtYear, txtOverview;
        ImageView imgPoster;
        Button btnHapus;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtYear = itemView.findViewById(R.id.txtYear);
            txtOverview = itemView.findViewById(R.id.txtOverview);
            imgPoster = itemView.findViewById(R.id.imgPoster);
            btnHapus = itemView.findViewById(R.id.btnHapus);
        }
    }
}
