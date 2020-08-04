package android.example.udacity_movie_project.utils;

import android.net.Uri;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkUtils {

    private static String baseUrl = "https://api.themoviedb.org";
    private static String imageBaseUrl = "http://image.tmdb.org/t/p";
    private static String youtubeUrl = "https://www.youtube.com/watch";
    private static String thumbnailBaseUrl = "http://img.youtube.com/vi";
    private static String thumbnailMediumQuality = "mqdefault.jpg";
    private static String imageSize = "w185";
    private static int version = 3;
    private static String movie = "movie";
    private static String popular = "popular";
    private static String topRated = "top_rated";
    private static String api_key = "api_key";
    private static String video = "videos";


    public static URL buildPublicUrl(String key) {
        Uri builtUri = Uri.parse(baseUrl).buildUpon()
                .appendPath(String.valueOf(version))
                .appendPath(movie)
                .appendPath(popular)
                .appendQueryParameter(api_key, key)
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
        Uri builtUri = Uri.parse(baseUrl).buildUpon()
                .appendPath(String.valueOf(version))
                .appendPath(movie)
                .appendPath(topRated)
                .appendQueryParameter(api_key, key)
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
        Uri builtUri = Uri.parse(imageBaseUrl).buildUpon()
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

    public static URL buildVideoUrlFromJson(String key, String movieId) {
        Uri builtUri = Uri.parse(baseUrl).buildUpon()
                .appendPath(String.valueOf(version))
                .appendPath(movie)
                .appendPath(movieId)
                .appendPath(video)
                .appendQueryParameter(api_key, key)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildVideoYoutubePathUrl(String videoKey) {
        Uri builtUri = Uri.parse(youtubeUrl).buildUpon()
                .appendQueryParameter("v", videoKey)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildVideoThumbnailUrl(String videoKey) {
        Uri builtUri = Uri.parse(thumbnailBaseUrl).buildUpon()
                .appendPath(videoKey)
                .appendPath(thumbnailMediumQuality)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL... urls) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(urls[0])
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
