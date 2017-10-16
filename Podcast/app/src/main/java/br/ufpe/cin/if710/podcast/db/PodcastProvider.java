package br.ufpe.cin.if710.podcast.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class PodcastProvider extends ContentProvider {

    private PodcastDBHelper dbHelper;

    public PodcastProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String table = getTableName(uri);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(table,selection,selectionArgs);
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String table = getTableName(uri);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long value = db.insert(table,null,values);
        return Uri.withAppendedPath(PodcastProviderContract.EPISODE_LIST_URI, String.valueOf(value));
    }

    @Override
    public boolean onCreate() {
        dbHelper = dbHelper.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        String table = getTableName(uri);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.query(table,projection,selection,selectionArgs,null,null,sortOrder);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        String table = getTableName(uri);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.update(table,values,selection,selectionArgs);
    }

    public static String getTableName(Uri uri){
        String value = uri.getPath();
        value = value.replace("/","");
        return value;
    }
}
