package android.example.udacity_movie_project;

import android.content.Context;
import android.content.Intent;
import android.example.udacity_movie_project.model.Movie;
import android.example.udacity_movie_project.utils.NetworkUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private List<Movie> movieData;

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView posterImage;
        private TextView originaltitle;

        MovieViewHolder(View view) {
            super(view);
            posterImage = view.findViewById(R.id.movie_poster_iv);
            originaltitle = view.findViewById(R.id.original_title_rc);
        }

    }

    public void updateUI(List<Movie> a) {
        movieData.clear();
        movieData.addAll(a);
        notifyDataSetChanged();
    }



    public MovieAdapter(List<Movie> movieData) {
        this.movieData = movieData;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_poster_item, parent, false);
        return new MovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Picasso.get().load(NetworkUtils.buildImageUrl(movieData.get(position).getPosterUrl().substring(1))).into(holder.posterImage);
        holder.originaltitle.setText(movieData.get(position).getOriginalTitle());
        context = holder.posterImage.getContext();
        final String originalTitle = movieData.get(position).getOriginalTitle();
        final String releaseDate = movieData.get(position).getReleaseDate();
        final String language = movieData.get(position).getLanguage();
        final String rating = movieData.get(position).getRating();
        final String ratingCount = movieData.get(position).getRatingCount();
        final String overview = movieData.get(position).getOverview();
        final String posterUrl = movieData.get(position).getPosterUrl();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MovieDetailActivity.class);
                intent.putExtra("originalTitle",originalTitle);
                intent.putExtra("language",language);
                intent.putExtra("releaseDate",releaseDate);
                intent.putExtra("rating",rating);
                intent.putExtra("ratingCount",ratingCount);
                intent.putExtra("overview",overview);
                intent.putExtra("posterUrl",posterUrl);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieData.size();
    }

}
