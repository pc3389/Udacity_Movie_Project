package android.example.udacity_movie_project.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class MovieContract {
    public static final String CONTENT_AUTHORITY = "com.example.android.movies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIES = "movies";

    private MovieContract(){}
    public static final class MovieEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MOVIES);
        public static final String TABLE_NAME = "movies";

        public final static String COLUMN_MOVIE_ID = "movidId";
        public final static String COLUMN_MOVIE_TITLE = "title";
        public final static String COLUMN_MOVIE_POSTER = "poster";
        public final static String COLUMN_MOVIE_RELEASE_DATE = "releaseDate";
        public final static String COLUMN_MOVIE_LANGUAGE = "language";
        public final static String COLUMN_MOVIE_RATING = "rating";
        public final static String COLUMN_MOVIE_RATING_COUNT = "ratingcount";
        public final static String COLUMN_MOVIE_OVERVIEW = "overview";
    }
}
