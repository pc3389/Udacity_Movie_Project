package android.example.udacity_movie_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.example.udacity_movie_project.utils.NetworkUtils;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Intent intent = getIntent();
        String originaltext = intent.getStringExtra("originalTitle");
        String language = intent.getStringExtra("language");
        String releaseDate = intent.getStringExtra("releaseDate");
        String rating = intent.getStringExtra("rating");
        String ratingCount = intent.getStringExtra("ratingCount");
        String overview = intent.getStringExtra("overview");
        String posterUrl = intent.getStringExtra("posterUrl");

        TextView originalTitleTv = findViewById(R.id.original_title_tv);
        TextView languateTv = findViewById(R.id.language_tv);
        TextView releaseDateTv = findViewById(R.id.release_date_tv);
        TextView ratingTv = findViewById(R.id.rating_tv);
        TextView ratingCountTv = findViewById(R.id.rating_count_tv);
        TextView overviewTv = findViewById(R.id.overview_tv);
        ImageView posterImage = findViewById(R.id.thumbnail_iv);


        originalTitleTv.setText(originaltext);
        languateTv.setText(language);
        releaseDateTv.setText(releaseDate);
        ratingTv.setText(rating);
        ratingCountTv.setText(ratingCount);
        overviewTv.setText(overview);
        Picasso.get().load(NetworkUtils.buildImageUrl(posterUrl.substring(1))).into(posterImage);

    }
}
