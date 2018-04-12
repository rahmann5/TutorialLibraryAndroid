package com.example.naziur.tutoriallibraryandroid.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import static com.example.naziur.tutoriallibraryandroid.database.FavoriteTutorialContract.FavoriteTutorialEntry;
/**
 * Created by Hamidur on 08/04/2018.
 */

public class TutorialDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Tutorials.db";

    public TutorialDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FavoriteTutorialContract.FavoriteTutorialEntry.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(FavoriteTutorialEntry.SQL_DELETE_ENTRIES);
        onCreate(db);
    }


    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean isFavorite (String tid) {
        SQLiteDatabase db = getWritableDatabase();
        String[] projection = {
                BaseColumns._ID
        };

        String selection = FavoriteTutorialEntry.COLUMN_NAME_TUTORIAL_ID + " = ?";
        String[] selectionArgs = { tid };

        Cursor cursor = db.query(
                FavoriteTutorialEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null                    // The sort order
        );

        return (cursor.getCount() > 0);
    }

    public Cursor getAllMyTutorial(){
        SQLiteDatabase db = getWritableDatabase();
        String[] projection = {
                BaseColumns._ID,
                FavoriteTutorialEntry.COLUMN_NAME_TUTORIAL_ID,
                FavoriteTutorialEntry.COLUMN_NAME_TUTORIAL_TITLE,
                FavoriteTutorialEntry.COLUMN_NAME_TUTORIAL_AUTHOR
        };

        Cursor cursor = db.query(
                FavoriteTutorialEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null                    // The sort order
        );

        return cursor;
    }

    public long insertFavTutorial (int tid, String title, String author) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FavoriteTutorialEntry.COLUMN_NAME_TUTORIAL_ID, tid);
        values.put(FavoriteTutorialEntry.COLUMN_NAME_TUTORIAL_TITLE, title);
        values.put(FavoriteTutorialEntry.COLUMN_NAME_TUTORIAL_AUTHOR, author);
        return db.insert(FavoriteTutorialEntry.TABLE_NAME, null, values);
    }

    public int removeFavTutorial (String tid) {
        SQLiteDatabase db = getWritableDatabase();
        String selection = FavoriteTutorialEntry.COLUMN_NAME_TUTORIAL_ID + " = ?";

        String[] selectionArgs = { tid };

        return db.delete(FavoriteTutorialEntry.TABLE_NAME, selection, selectionArgs);
    }
}
