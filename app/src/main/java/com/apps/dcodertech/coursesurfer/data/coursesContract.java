package com.apps.dcodertech.coursesurfer.data;

import android.provider.BaseColumns;

public class coursesContract {
    public coursesContract(){

    }
    public class courseEntry implements BaseColumns{
        public static final String TABLE_NAME = "courseInfo";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_COMPANY = "company";
        public static final String COLUMN_PROVIDER = "provider";
        public static final String COLUMN_UNIVERSITY = "university";
        public static final String COLUMN_CERTIFICATION = "certification";
        public static final String COLUMN_WEEKS = "weeks";
        public static final String COLUMN_HOURS = "hours";
    }
    public static final String CREATE_TABLE = "CREATE TABLE " +
            courseEntry.TABLE_NAME + "(" +
            courseEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            courseEntry.COLUMN_NAME + " TEXT NOT NULL UNIQUE," +
            courseEntry.COLUMN_AUTHOR + " TEXT NOT NULL," +
            courseEntry.COLUMN_COMPANY + " TEXT NOT NULL," +
            courseEntry.COLUMN_PROVIDER + " TEXT NOT NULL" +
            courseEntry.COLUMN_UNIVERSITY + " TEXT NOT NULL" +
            courseEntry.COLUMN_CERTIFICATION + " TEXT NOT NULL" +
            courseEntry.COLUMN_WEEKS + " TEXT NOT NULL" +
            courseEntry.COLUMN_HOURS + " TEXT NOT NULL" +");";
}
