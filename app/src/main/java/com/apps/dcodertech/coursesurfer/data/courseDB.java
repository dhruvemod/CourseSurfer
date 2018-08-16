package com.apps.dcodertech.coursesurfer.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.apps.dcodertech.coursesurfer.Courses;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

public class courseDB extends SQLiteOpenHelper {
    public final static String DATABASE_NAME = "courses.db";
    public final static int DATABASE_VERSION = 1;
    public courseDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(coursesContract.CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    public void insert(Courses item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(coursesContract.courseEntry.COLUMN_NAME, item.getCourse_name());
        values.put(coursesContract.courseEntry.COLUMN_AUTHOR, item.getCourse_prof());
        values.put(coursesContract.courseEntry.COLUMN_COMPANY, item.getCourse_subject());
        values.put(coursesContract.courseEntry.COLUMN_PROVIDER, item.getCourse_provider());
        values.put(coursesContract.courseEntry.COLUMN_UNIVERSITY, item.getCourse_institution());
        values.put(coursesContract.courseEntry.COLUMN_CERTIFICATION, item.getCourse_certifications());
        values.put(coursesContract.courseEntry.COLUMN_WEEKS, item.getCourse_duration());
        values.put(coursesContract.courseEntry.COLUMN_HOURS, item.getCourse_hours());
        long id = db.insert(coursesContract.courseEntry.TABLE_NAME, null, values);
    }
    public Cursor readInfo(String input) {
        Log.i("check",input);
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                coursesContract.courseEntry._ID,
                coursesContract.courseEntry.COLUMN_NAME,
                coursesContract.courseEntry.COLUMN_AUTHOR,
                coursesContract.courseEntry.COLUMN_COMPANY,
                coursesContract.courseEntry.COLUMN_PROVIDER,
                coursesContract.courseEntry.COLUMN_UNIVERSITY,
                coursesContract.courseEntry.COLUMN_CERTIFICATION,
                coursesContract.courseEntry.COLUMN_WEEKS,
                coursesContract.courseEntry.COLUMN_HOURS
        };
        String whereClause = coursesContract.courseEntry.COLUMN_NAME+"= ?";
        String[] whereArgs = new String[] {input};
        Cursor cursor = db.query(
                coursesContract.courseEntry.TABLE_NAME,
                projection,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return cursor;
    }
    public Cursor readCourseInfo() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                coursesContract.courseEntry._ID,
                coursesContract.courseEntry.COLUMN_NAME,
                coursesContract.courseEntry.COLUMN_AUTHOR,
                coursesContract.courseEntry.COLUMN_COMPANY,
                coursesContract.courseEntry.COLUMN_PROVIDER,
                coursesContract.courseEntry.COLUMN_UNIVERSITY,
                coursesContract.courseEntry.COLUMN_CERTIFICATION,
                coursesContract.courseEntry.COLUMN_WEEKS,
                coursesContract.courseEntry.COLUMN_HOURS

        };
        Cursor cursor = db.query(
                coursesContract.courseEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        return cursor;
    }
    public void deleteData(String name){
        SQLiteDatabase database=getWritableDatabase();
        database.delete(coursesContract.courseEntry.TABLE_NAME,coursesContract.courseEntry.COLUMN_NAME+"=?",new String[]{name});


    }

}
