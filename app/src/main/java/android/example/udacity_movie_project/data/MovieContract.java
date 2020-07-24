package android.example.udacity_movie_project.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class MovieContract {
    public static final String CONTENT_AUTHORITY = "com.example.android.udacity_movie_project";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIES = "movies";

    private MovieContract(){}

    public static final class MovieEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIES)
                .build();

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;

        public static final String TABLE_NAME = "movies";

        public final static String COLUMN_MOVIE_ID = "movid_id";
        public final static String COLUMN_MOVIE_TITLE = "title";
        public final static String COLUMN_MOVIE_POSTER_PATH = "poster";
        public final static String COLUMN_MOVIE_RELEASE_DATE = "releaseDate";
        public final static String COLUMN_MOVIE_LANGUAGE = "language";
        public final static String COLUMN_MOVIE_RATING = "rating";
        public final static String COLUMN_MOVIE_RATING_COUNT = "ratingcount";
        public final static String COLUMN_MOVIE_OVERVIEW = "overview";
        public final static String COLUMN_MOVIE_FAVORITE = "favorite";
        public final static String COLUMN_MOVIE_VIDEO_PATH = "video";
    }
}
