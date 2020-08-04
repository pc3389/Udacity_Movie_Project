package android.example.udacity_movie_project.utils;

import android.example.udacity_movie_project.model.Movie;
import android.example.udacity_movie_project.model.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static List<Movie> parseMovieJson(String jsonData) throws JSONException {

        JSONObject movieData = new JSONObject(jsonData);
        JSONArray result = movieData.getJSONArray("results");
        List<Movie> m = new ArrayList<>();
        for (int i = 0; i < result.length(); i++) {
            String movieId = result.getJSONObject(i).getString("id");
            String originalTitle = result.getJSONObject(i).getString("title");
            String posterPath = result.getJSONObject(i).getString("poster_path");
            String releaseDate = result.getJSONObject(i).getString("release_date");
            String language = result.getJSONObject(i).getString("original_language");
            String rating = String.valueOf(result.getJSONObject(i).getDouble("vote_average"));
            String ratingCount = String.valueOf(result.getJSONObject(i).getInt("vote_count"));
            String overview = result.getJSONObject(i).getString("overview");
            int favorite = 0;
            m.add(new Movie(movieId, originalTitle, posterPath, releaseDate, language, rating, ratingCount, overview, favorite));
        }
        return m;
    }

    public static List<Video> parseVideoJson(String videoJsonData) throws JSONException {

        JSONObject videoData = new JSONObject(videoJsonData);
        JSONArray result = videoData.getJSONArray("results");
        List<Video> videoKeyList = new ArrayList<>();
        for(int i = 0; i < result.length(); i++) {
            String type = result.getJSONObject(i).getString("type");
            String detail = result.getJSONObject(i).getString("name");
            String videoKey = result.getJSONObject(i).getString("key");
            videoKeyList.add(new Video(type, detail, videoKey));
        }
        return videoKeyList;
    }

}
