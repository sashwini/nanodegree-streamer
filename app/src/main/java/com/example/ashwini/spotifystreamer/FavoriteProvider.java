package com.example.ashwini.spotifystreamer;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by Ashwini on 3/12/2016.
 * Used below link as reference
 * http://www.vogella.com/tutorials/AndroidSQLite/article.html
 */
public class FavoriteProvider extends ContentProvider {

    private static final String DB_NAME = "favorites_db";
    private static final String FAV_MOVIES = "favorite_movies";
    public static final String COL_MOVIE_ID = "movie_id";
    private static final String SQL_CREATE_MAIN =
            "CREATE TABLE  " + FAV_MOVIES + " (_ID INTEGER PRIMARY KEY, " +
                    COL_MOVIE_ID + " INTEGER UNIQUE)";
    private static final String AUTHORITY = "com.example.ashwini.spotifystreamer";
    private static final String BASE_PATH = "favorites";
    private static final int BASE_MATHER_CODE = 100;
    private static final int ID_MATHER_CODE = 101;
    public static final Uri CONTENT_URI = Uri.parse("content://"+ AUTHORITY + "/" + BASE_PATH);

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH, BASE_MATHER_CODE);
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", ID_MATHER_CODE);
    }

    private MainDatabaseHelper mOpenHelper;
    private SQLiteDatabase db;

    public boolean onCreate() {
        mOpenHelper = new MainDatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    public Uri insert(Uri uri, ContentValues values) {
        Uri updatedUri = Uri.parse(BASE_PATH);
        int uriType = uriMatcher.match(uri);
        switch (uriType) {
            default:
            case BASE_MATHER_CODE:
                db = mOpenHelper.getWritableDatabase();
                long id = db.insert(FAV_MOVIES, null, values);
                updatedUri = Uri.parse(BASE_PATH + "/" + id);
                break;
            case ID_MATHER_CODE:
                //currently we are not supporting insert for particular movie
                break;
        }

        return updatedUri;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int uriType = uriMatcher.match(uri);
        int rowsUpdated;
        switch (uriType) {
            default:
            case BASE_MATHER_CODE:
                db = mOpenHelper.getWritableDatabase();
                rowsUpdated = db.update(FAV_MOVIES, values, null, null);
                break;
            case ID_MATHER_CODE:
                String movieId = uri.getLastPathSegment();
                if(TextUtils.isEmpty(selection)){
                    rowsUpdated = db.update(FAV_MOVIES, values, COL_MOVIE_ID + "=" + movieId, null);
                }
                else {
                    rowsUpdated = db.update(FAV_MOVIES, values,
                            COL_MOVIE_ID + "=" + movieId + "and" + selection, selectionArgs);
                }
                break;
        }

        return rowsUpdated;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = uriMatcher.match(uri);
        int rowsDeleted;
        switch (uriType) {
            default:
            case BASE_MATHER_CODE:
                db = mOpenHelper.getWritableDatabase();
                rowsDeleted = db.delete(FAV_MOVIES, selection, selectionArgs);
                break;
            case ID_MATHER_CODE:
                String movieId = uri.getLastPathSegment();
                if(TextUtils.isEmpty(selection)){
                    rowsDeleted = db.delete(FAV_MOVIES, COL_MOVIE_ID + "=" + movieId, null);
                }
                else {
                    rowsDeleted = db.delete(FAV_MOVIES,
                            COL_MOVIE_ID + "=" + movieId + "and" + selection, selectionArgs);
                }
                break;
        }

        return rowsDeleted;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        int uriType = uriMatcher.match(uri);
        String[] selectedColumns = {COL_MOVIE_ID};
        Cursor cursor;
        switch (uriType) {
            default:
            case ID_MATHER_CODE:
            case BASE_MATHER_CODE:
                //currently we will support only retrieval of all favorite movies
                db = mOpenHelper.getWritableDatabase();
                cursor = db.query(FAV_MOVIES, selectedColumns, selection, selectionArgs, null, null, null);
                break;
        }
        return cursor;
    }

    protected static final class MainDatabaseHelper extends SQLiteOpenHelper {

        MainDatabaseHelper(Context context) {
            super(context, DB_NAME, null, 1);
        }

        public void onCreate(SQLiteDatabase db) {
            //create the tables when db is created
            db.execSQL(SQL_CREATE_MAIN);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //This will contain the code to upgrade the db
        }
    }
}