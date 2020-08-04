package android.example.udacity_movie_project;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    public static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        FavoriteViewHolder(View view) {
            super(view);
        }
    }


    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}