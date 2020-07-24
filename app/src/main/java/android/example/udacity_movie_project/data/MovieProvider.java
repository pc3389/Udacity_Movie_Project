package android.example.udacity_movie_project.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.example.udacity_movie_project.R;
import android.example.udacity_movie_project.data.MovieContract.MovieEntry;
import android.content.ContentUris;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MovieProvider extends ContentProvider {

    private static final int MOVIES = 100;

    private static final int MOVIE_ID = 101;

    public static final String LOG_TAG = MovieProvider.class.getSimpleName();

    private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        mUriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIES, MOVIES);

        mUriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIES + "/#", MOVIE_ID);
    }

    private MovieDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new MovieDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor;

        int match = mUriMatcher.match(uri);

        switch (match) {
            case MOVIES:
                cursor = db.query(MovieEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case MOVIE_ID:
                selection = MovieEntry.COLUMN_MOVIE_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                cursor = db.query(MovieEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        {
            final int match = mUriMatcher.match(uri);
            switch (match) {
                case MOVIES:
                    return MovieEntry.CONTENT_LIST_TYPE;
                case MOVIE_ID:
                    return MovieEntry.CONTENT_ITEM_TYPE;
                default:
                    throw new IllegalArgumentException("Unknown URI: " + uri + " with match " + match);
            }
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = mUriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                return insertMovie(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertMovie(Uri uri, ContentValues values){
        String releaseDate = values.getAsString(MovieEntry.COLUMN_MOVIE_RELEASE_DATE);
        String favorite = values.getAsString(MovieEntry.COLUMN_MOVIE_FAVORITE);
        String language = values.getAsString(MovieEntry.COLUMN_MOVIE_LANGUAGE);
        String poster = values.getAsString(MovieEntry.COLUMN_MOVIE_POSTER_PATH);
        String rating = values.getAsString(MovieEntry.COLUMN_MOVIE_RATING);
        String count = values.getAsString(MovieEntry.COLUMN_MOVIE_RATING_COUNT);
        String id = values.getAsString(MovieEntry.COLUMN_MOVIE_ID);
        String title = values.getAsString(MovieEntry.COLUMN_MOVIE_TITLE);
        String video = values.getAsString(MovieEntry.COLUMN_MOVIE_VIDEO_PATH);
        String overview = values.getAsString(MovieEntry.COLUMN_MOVIE_OVERVIEW);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        long insertRowId = db.insert(MovieEntry.TABLE_NAME, null, values);

        if (insertRowId == -1) {
            Toast.makeText(getContext(), R.string.save_error, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), R.string.save_success, Toast.LENGTH_SHORT).show();
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, insertRowId);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String
            selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[]
            selectionArgs) {
        return 0;
    }


}
