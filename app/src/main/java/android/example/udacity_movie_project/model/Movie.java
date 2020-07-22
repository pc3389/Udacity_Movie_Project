package android.example.udacity_movie_project.model;

import java.util.List;

public class Movie {

    private String movidId;
    private String originalTitle;
    private String posterUrl;
    private String releaseDate;
    private String language;
    private String rating;
    private String ratingCount;
    private String overview;



    public Movie(String movidId, String originalTitle, String posterUrl, String releaseDate, String language, String rating, String ratingCount, String overview) {
        this.movidId = movidId;
        this.originalTitle = originalTitle;
        this.posterUrl = posterUrl;
        this.releaseDate = releaseDate;
        this.language = language;
        this.rating = rating;
        this.ratingCount = ratingCount;
        this.overview = overview;
    }

    public String getMovidId() {
        return movidId;
    }

    public void setMovidId(String movidId) {
        this.movidId = movidId;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(String ratingCount) {
        this.ratingCount = ratingCount;
    }
}
