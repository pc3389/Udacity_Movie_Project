package android.example.udacity_movie_project;

import android.content.Context;
import android.content.Intent;
import android.example.udacity_movie_project.model.Video;
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

import java.net.URL;
import java.util.List;


public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    Context context;
    private List<Video> videoData;

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        private ImageView videoImage;
        private TextView teaserOrTrailer;
        private TextView videoDetail;

        VideoViewHolder(View view) {
            super(view);
            videoImage = view.findViewById(R.id.video_iv);
            teaserOrTrailer = view.findViewById(R.id.video_trailer_tv);
            videoDetail = view.findViewById(R.id.video_title_tv);
        }
    }

    public void updateUI(List<Video> a) {
        videoData.clear();
        videoData.addAll(a);
        notifyDataSetChanged();
    }

    public VideoAdapter(List<Video> videoData) {
        this.videoData = videoData;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false);
        return new VideoAdapter.VideoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        holder.teaserOrTrailer.setText(videoData.get(position).getTeaserOrTrailer());
        holder.videoDetail.setText(videoData.get(position).getVideoDetail());
        final String videoKey = videoData.get(position).getVideoKey();
        URL videoThumbnailPath = NetworkUtils.buildVideoThumbnailUrl(videoKey);
        Picasso.get().load(String.valueOf(videoThumbnailPath)).into(holder.videoImage);
        context = holder.videoImage.getContext();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri videoYoutubePath = Uri.parse(String.valueOf(NetworkUtils.buildVideoYoutubePathUrl(videoKey)));
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(videoYoutubePath);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(videoData != null) {
            return videoData.size();
        }
        return 0;
    }
}
