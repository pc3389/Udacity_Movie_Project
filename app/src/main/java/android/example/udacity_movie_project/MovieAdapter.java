package android.example.udacity_movie_project;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.example.udacity_movie_project.data.MovieContract;
import android.example.udacity_movie_project.utils.NetworkUtils;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private Cursor dataCursor;

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView posterImage;
        private TextView originaltitle;

        MovieViewHolder(View view) {
            super(view);
            posterImage = view.findViewById(R.id.movie_poster_iv);
            originaltitle = view.findViewById(R.id.original_title_rc);
        }
    }

    public MovieAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_poster_item, parent, false);

        return new MovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        dataCursor.moveToPosition(position);
        final int num = position;
        final int movieId = dataCursor.getInt(dataCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID));
        final String originalTitle = dataCursor.getString(dataCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE));

        final String posterUrl = dataCursor.getString(dataCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH));
        Picasso.get().load(NetworkUtils.buildImageUrl(posterUrl.substring(1))).into(holder.posterImage);
        holder.originaltitle.setText(originalTitle);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieDetailActivity.class);
                Uri currentMovieUri = ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI, movieId);
                intent.setData(currentMovieUri);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (dataCursor != null) {
            return dataCursor.getCount();
        }
        return 0;
    }

    public Cursor swapCursor(Cursor cursor) {
        if (dataCursor == cursor) {
            return null;
        }
        Cursor prevCursor = dataCursor;
        dataCursor = cursor;
        if (cursor != null) {
            notifyDataSetChanged();
        }
        return prevCursor;
    }

}
