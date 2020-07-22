package android.example.udacity_movie_project.utils;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtils {

    private static String baseUrl = "https://api.themoviedb.org";
    private static String imageBaseUrl = "http://image.tmdb.org/t/p";
    private static String imageSize = "w185";
    private static int version = 3;
    private static String movie = "movie";
    private static String popular = "popular";
    private static String topRated = "top_rated";
    private static String api_key = "api_key";
    private static String video = "video";


    public static URL buildPublicUrl(String key) {
        Uri builtUri =Uri.parse(baseUrl).buildUpon()
                .appendPath(String.valueOf(version))
                .appendPath(movie)
                .appendPath(popular)
                .appendQueryParameter(api_key,key)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildTopRatedUrl(String key) {
        Uri builtUri =Uri.parse(baseUrl).buildUpon()
                .appendPath(String.valueOf(version))
                .appendPath(movie)
                .appendPath(topRated)
                .appendQueryParameter(api_key,key)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String buildImageUrl(String imagePath) {
        Uri builtUri =Uri.parse(imageBaseUrl).buildUpon()
                .appendPath(imageSize)
                .appendPath(imagePath)
                .build();
        String url = null;
        try {
            url = new URL(builtUri.toString()).toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildVideoUrl(String key, String movidId) {
        Uri builtUri =Uri.parse(baseUrl).buildUpon()
                .appendPath(String.valueOf(version))
                .appendPath(movidId)
                .appendPath(video)
                .appendQueryParameter(api_key,key)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

}
