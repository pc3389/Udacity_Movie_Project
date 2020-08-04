package android.example.udacity_movie_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

import android.database.Cursor;
import android.example.udacity_movie_project.data.MovieContract;
import android.example.udacity_movie_project.model.Movie;
import android.example.udacity_movie_project.utils.JsonUtils;
import android.example.udacity_movie_project.utils.NetworkUtils;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    private RecyclerView moviePosterRecyclerview;
    private MovieAdapter movieAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public static String api_key;
    private List<Movie> movieList = new ArrayList<>();
    private String data;
    private TextView mainTopTv;
    private Context context;
    private URL mUrl;
    private static final int Movie_Loader = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        api_key = this.getString(R.string.tmdb_api_key);

        moviePosterRecyclerview = findViewById(R.id.movie_poster_rc);

        layoutManager = new GridLayoutManager(this, 2);
        moviePosterRecyclerview.setLayoutManager(layoutManager);

        movieAdapter = new MovieAdapter(context);
        moviePosterRecyclerview.setAdapter(movieAdapter);

        getContentResolver().delete(MovieContract.MovieEntry.CONTENT_URI, null, null);
        mUrl = NetworkUtils.buildPublicUrl(api_key);

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.initLoader(Movie_Loader,null, this);

        mainTopTv = findViewById(R.id.main_top_tv);
        mainTopTv.setText("Public");
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        String[] projection ={
                MovieContract.MovieEntry.COLUMN_MOVIE_ID,
                MovieContract.MovieEntry.COLUMN_MOVIE_TITLE,
                MovieContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH,
                MovieContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE,
                MovieContract.MovieEntry.COLUMN_MOVIE_LANGUAGE,
                MovieContract.MovieEntry.COLUMN_MOVIE_RATING,
                MovieContract.MovieEntry.COLUMN_MOVIE_RATING_COUNT,
                MovieContract.MovieEntry.COLUMN_MOVIE_OVERVIEW,
                MovieContract.MovieEntry.COLUMN_MOVIE_FAVORITE,
                MovieContract.MovieEntry.COLUMN_MOVIE_VIDEO_PATH,
        };

        new FetchMovieDataLoader(this, mUrl);

        return new CursorLoader(this,
                MovieContract.MovieEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        movieAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        movieAdapter.swapCursor(null);
    }

    public class FetchMovieDataLoader extends AsyncTaskLoader<Cursor> {
        private URL url;

        FetchMovieDataLoader(Context context, URL url) {
            super(context);
            this.url = url;
            forceLoad();
        }

        @Nullable
        @Override
        public Cursor loadInBackground() {
            if(url == null) {
                return null;
            }
            data = NetworkUtils.getResponseFromHttpUrl(url);

            try {
                movieList = JsonUtils.parseMovieJson(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for(int i = 0; i < movieList.size(); i++) {
                insertMovie(movieList.get(i));
            }
            return null;
        }


    }

    private void insertMovie(Movie movie) {
        ContentValues values = new ContentValues();
        values.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, movie.getMovidId());
        values.put(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE, movie.getOriginalTitle());
        values.put(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH, movie.getPosterUrl());
        values.put(MovieContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE, movie.getReleaseDate());
        values.put(MovieContract.MovieEntry.COLUMN_MOVIE_LANGUAGE, movie.getLanguage());
        values.put(MovieContract.MovieEntry.COLUMN_MOVIE_RATING, movie.getRating());
        values.put(MovieContract.MovieEntry.COLUMN_MOVIE_RATING_COUNT, movie.getRatingCount());
        values.put(MovieContract.MovieEntry.COLUMN_MOVIE_OVERVIEW, movie.getOverview());
        values.put(MovieContract.MovieEntry.COLUMN_MOVIE_FAVORITE, movie.getFavorite());
        values.put(MovieContract.MovieEntry.COLUMN_MOVIE_VIDEO_PATH, NetworkUtils.buildVideoUrlFromJson(api_key, movie.getMovidId()).toString());

        Uri newUri = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, values);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.public_menu) {
            getContentResolver().delete(MovieContract.MovieEntry.CONTENT_URI, null, null);
            mUrl = NetworkUtils.buildPublicUrl(api_key);
            LoaderManager loaderManager = LoaderManager.getInstance(this);
            loaderManager.restartLoader(Movie_Loader,null, this);
            mainTopTv.setText("Public");
            return true;
        }

        if (id == R.id.top_rated_menu) {
            getContentResolver().delete(MovieContract.MovieEntry.CONTENT_URI, null, null);
            mUrl = NetworkUtils.buildTopRatedUrl(api_key);
            LoaderManager loaderManager = LoaderManager.getInstance(this);
            loaderManager.restartLoader(Movie_Loader,null, this);
            mainTopTv.setText("Top Rated");
            return true;
        }

        if (id == R.id.favorite_menu) {
            Intent intent = new Intent(context, FavoriteActivity.class);
            context.startActivity(intent);
            return true;
        }
        return false;
    }
}

