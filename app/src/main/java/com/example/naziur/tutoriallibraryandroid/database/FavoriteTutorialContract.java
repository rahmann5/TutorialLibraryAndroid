package com.example.naziur.tutoriallibraryandroid.database;

import android.provider.BaseColumns;

/**
 * Created by Hamidur on 08/04/2018.
 */

public final class FavoriteTutorialContract {
    private FavoriteTutorialContract() {}

    public static class FavoriteTutorialEntry implements BaseColumns {
        public static final String TABLE_NAME = "favorite";
        public static final String COLUMN_NAME_TUTORIAL_ID = "tid";
        public static final String COLUMN_NAME_TUTORIAL_TITLE = "title";
        public static final String COLUMN_NAME_TUTORIAL_AUTHOR = "author";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        COLUMN_NAME_TUTORIAL_ID + " INTEGER," +
                        COLUMN_NAME_TUTORIAL_TITLE + " TEXT," +
                        COLUMN_NAME_TUTORIAL_AUTHOR + " TEXT)";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
