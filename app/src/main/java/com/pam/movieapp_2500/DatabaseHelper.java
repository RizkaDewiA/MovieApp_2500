package com.pam.movieapp_2500;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "db_movie";
    private static final int DATABASE_VERSION= 2;

    private static final String TABLE_NAME = "table_favorite";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_OVERVIEW = "overview";
    private static final String COLUMN_IMAGE = "image";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //membuat tabel
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_TITLE + " TEXT," +
                        COLUMN_YEAR + " DATE," +
                        COLUMN_OVERVIEW + " TEXT," +
                        COLUMN_IMAGE + " TEXT" +
                        ")";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    //menampilkan
    public ArrayList<Movie> getAllFavorite() {
        ArrayList<Movie> listMovie = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_YEAR, COLUMN_OVERVIEW, COLUMN_IMAGE}, null, null, null, null, null);

        if (c != null && c.moveToFirst()) {
            do {
                Movie movie = new Movie();

                movie.setId(Integer.parseInt(c.getString(0)));
                movie.setTitle(c.getString(1));
                movie.setReleaseDate(c.getString(2));
                movie.setOverview(c.getString(3));
                movie.setPosterPath(c.getString(4));

                listMovie.add(movie);

            } while (c.moveToNext());
        }

        db.close();
        return listMovie;
    }

    //menambahkan
    public void addFavorite(Movie mv){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_TITLE,mv.getTitle());
        values.put(COLUMN_YEAR,mv.getReleaseDate());
        values.put(COLUMN_OVERVIEW,mv.getOverview());
        values.put(COLUMN_IMAGE,mv.getPosterPath());

        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    //untuk menghapus
    public void deleteFavorite(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, "id = " + id, null);
        db.close();
    }
}

