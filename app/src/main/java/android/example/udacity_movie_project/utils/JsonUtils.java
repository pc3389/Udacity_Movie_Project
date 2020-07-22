package android.example.udacity_movie_project.utils;

import android.example.udacity_movie_project.model.Movie;

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
            String movidId = result.getJSONObject(i).getString("id");
            String originalTitle = result.getJSONObject(i).getString("title");
            String posterPath = result.getJSONObject(i).getString("poster_path");
            String releaseDate = result.getJSONObject(i).getString("release_date");
            String language = result.getJSONObject(i).getString("original_language");
            String rating = String.valueOf(result.getJSONObject(i).getDouble("vote_average"));
            String ratingCount = String.valueOf(result.getJSONObject(i).getInt("vote_count"));
            String overview = result.getJSONObject(i).getString("overview");
            m.add(new Movie(movidId, originalTitle, posterPath, releaseDate, language, rating, ratingCount, overview));
        }
        return m;
    }

}
