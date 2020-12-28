package com.pam.movieapp_2500;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pam.movieapp_2500.ui.main.FavoriteFragment;

public class DetailActivity extends AppCompatActivity {

    TextView txtTitle, txtYear, txtOverview;
    ImageView imgPoster;
    Button btnFavorite;

    public static final String ITEM_EXTRA = "item_extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imgPoster = findViewById(R.id.imgPoster);
        txtTitle = findViewById(R.id.txtTitle);
        txtYear = findViewById(R.id.txtYear);
        txtOverview = findViewById(R.id.txtOverview);
        btnFavorite = findViewById(R.id.btnFavorite);

        Movie movie = getIntent().getParcelableExtra(ITEM_EXTRA);

        if (movie !=null){
            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w200/"+movie.getPosterPath())
                    .into(imgPoster);
            txtTitle.setText(movie.getTitle());
            txtYear.setText(movie.getReleaseDate());
            txtOverview.setText(movie.getOverview());
        }

        if (getSupportActionBar() !=null){
            getSupportActionBar().setTitle("Detail Movie");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = txtTitle.getText().toString();
                String year = txtYear.getText().toString();
                String overview = txtOverview.getText().toString();

                DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                Movie mv = new Movie();
                mv.setTitle(title);
                mv.setReleaseDate(year);
                mv.setOverview(overview);
                mv.setPosterPath("https://image.tmdb.org/t/p/w200/"+movie.getPosterPath());

                db.addFavorite(mv);

                Intent main = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(main);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}