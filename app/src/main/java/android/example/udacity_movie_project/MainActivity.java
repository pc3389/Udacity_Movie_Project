package android.example.udacity_movie_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.example.udacity_movie_project.model.Movie;
import android.example.udacity_movie_project.utils.JsonUtils;
import android.example.udacity_movie_project.utils.NetworkUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView moviePosterRecyclerview;
    private MovieAdapter movieAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String api_key;
    private List<Movie> movieList = new ArrayList<>();
    private String data;
    private TextView mainTopTv;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        api_key=this.getString(R.string.tmdb_api_key);

        moviePosterRecyclerview = findViewById(R.id.movie_poster_rc);

        layoutManager = new GridLayoutManager(this, 2);
        moviePosterRecyclerview.setLayoutManager(layoutManager);

        movieAdapter = new MovieAdapter(movieList);
        moviePosterRecyclerview.setAdapter(movieAdapter);


        URL url = NetworkUtils.buildPublicUrl(api_key);
        new FetchMovieData().execute(NetworkUtils.buildPublicUrl(api_key));
        mainTopTv = findViewById(R.id.main_top_tv);
        mainTopTv.setText("Public");
    }


    public class FetchMovieData extends AsyncTask<URL, Void, Object> {

        @Override
        protected String doInBackground(URL... urls) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(urls[0])
                    .build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object s) {
            super.onPostExecute(s);
            data = s.toString();
            try {
                movieList = JsonUtils.parseMovieJson(data);
                movieAdapter.updateUI(movieList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
            new FetchMovieData().execute(NetworkUtils.buildPublicUrl(api_key));
            mainTopTv.setText("Public");
            return true;
        }

        if (id == R.id.top_rated_menu) {
            new FetchMovieData().execute(NetworkUtils.buildTopRatedUrl(api_key));
            mainTopTv.setText("Top Rated");
            return true;
        }

        if(id == R.id.favorite_menu) {
            Intent intent = new Intent(context,FavoriteActivity.class);
            context.startActivity(intent);
            return true;
        }
        return false;
    }
}
