package android.example.udacity_movie_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.example.udacity_movie_project.data.MovieContract;
import android.example.udacity_movie_project.model.Video;
import android.example.udacity_movie_project.utils.JsonUtils;
import android.example.udacity_movie_project.utils.NetworkUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieDetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerView videoRecyclerview;
    private RecyclerView.LayoutManager layoutManager;
    private VideoAdapter videoAdapter;
    private List<Video> videoList = new ArrayList<>();
    ToggleButton toggleButton;
    private URL videoPathUrl;
    private Uri mCurrentMovieUri;
    private static final int DETAIL_LOADER = 1;

    TextView originalTitleTv;
    TextView languateTv;
    TextView releaseDateTv;
    TextView ratingTv;
    TextView ratingCountTv;
    TextView overviewTv;
    ImageView posterImage;
    Context context;

    LoaderManager loaderManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        context = this;

        Intent intent = getIntent();
        mCurrentMovieUri =intent.getData();

        originalTitleTv = findViewById(R.id.original_title_tv);
        languateTv = findViewById(R.id.language_tv);
        releaseDateTv = findViewById(R.id.release_date_tv);
        ratingTv = findViewById(R.id.rating_tv);
        ratingCountTv = findViewById(R.id.rating_count_tv);
        overviewTv = findViewById(R.id.overview_tv);
        posterImage = findViewById(R.id.thumbnail_iv);

        videoRecyclerview = findViewById(R.id.video_rc);

        layoutManager = new LinearLayoutManager(this);
        videoRecyclerview.setLayoutManager(layoutManager);

        videoAdapter = new VideoAdapter(videoList);
        videoRecyclerview.setAdapter(videoAdapter);


        loaderManager = LoaderManager.getInstance(this);
        Loader<String> loader = loaderManager.getLoader(DETAIL_LOADER);
        if(loader == null) {
            loaderManager.initLoader(DETAIL_LOADER, null, this);
        } else {
            loaderManager.restartLoader(DETAIL_LOADER, null, this);
        }
        toggleButton = (ToggleButton) findViewById(R.id.favorite_button);
        toggleButton.setChecked(false);
        toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_grey_heart));
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_red_heart));
                else
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_grey_heart));
            }
        });

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

        return new CursorLoader(this,
                mCurrentMovieUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        if (cursor.moveToFirst()) {
            int movieId = cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID));
            String originalTitle = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE));
            String releaseDate = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE));
            String language = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_LANGUAGE));
            float rating = cursor.getFloat(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_RATING));
            int ratingCount = cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_RATING_COUNT));
            String overview = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_OVERVIEW));
            String posterUrl = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH));

            originalTitleTv.setText(originalTitle);
            languateTv.setText(language);
            releaseDateTv.setText(releaseDate);
            ratingTv.setText(String.valueOf(rating));
            ratingCountTv.setText(String.valueOf(ratingCount));
            overviewTv.setText(overview);
            Picasso.get().load(NetworkUtils.buildImageUrl(posterUrl.substring(1))).into(posterImage);

            videoPathUrl = NetworkUtils.buildVideoUrlFromJson(MainActivity.api_key, String.valueOf(movieId));
            new FetchVideoDataLoader(context, videoPathUrl);
        }
        loaderManager.destroyLoader(DETAIL_LOADER);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    public class FetchVideoDataLoader extends AsyncTaskLoader<Cursor> {

        private URL url;

        FetchVideoDataLoader(Context context, URL url) {
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
            String data = NetworkUtils.getResponseFromHttpUrl(url);
            try {
                videoList = JsonUtils.parseVideoJson(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    videoAdapter.updateUI(videoList);
                }
            });
            return null;
        }
    }
}
