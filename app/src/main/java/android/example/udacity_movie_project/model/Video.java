package android.example.udacity_movie_project.model;

public class Video {
    private String teaserOrTrailer;
    private String videoDetail;
    private String videoKey;

    public Video(String teaserOrTrailer, String videoDetail, String videoKey) {
        this.teaserOrTrailer = teaserOrTrailer;
        this.videoDetail = videoDetail;
        this.videoKey = videoKey;
    }

    public String getTeaserOrTrailer() {
        return teaserOrTrailer;
    }

    public void setTeaserOrTrailer(String teaserOrTrailer) {
        this.teaserOrTrailer = teaserOrTrailer;
    }

    public String getVideoDetail() {
        return videoDetail;
    }

    public void setVideoDetail(String videoDetail) {
        this.videoDetail = videoDetail;
    }

    public String getVideoKey() {
        return videoKey;
    }

    public void setVideoKey(String videoKey) {
        this.videoKey = videoKey;
    }
}
