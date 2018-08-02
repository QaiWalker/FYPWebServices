package com.rp.qai.fypwebservices;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "fypwebservices.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_POEM = "poem";
    private static final String COLUMN_FIRSTID = "poemTable_id";
    private static final String COLUMN_POEMID = "poem_id";
    private static final String COLUMN_TITLE = "title";

    private static final String TABLE_POINTS = "points";
    private static final String COLUMN_SECONDID = "pointsTable_id";
    private static final String COLUMN_LAT = "latitude";
    private static final String COLUMN_LNG = "longitude";
    private static final String COLUMN_RAD = "radius";

    private static final String CREATE_TABLE_POEM ="CREATE TABLE " + TABLE_POEM + "("
            + COLUMN_FIRSTID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_POEMID + " INTEGER, "
            + COLUMN_TITLE + " TEXT )";

    private static final String CREATE_TABLE_POINTS ="CREATE TABLE " + TABLE_POINTS + "("
            + COLUMN_SECONDID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_LAT + " REAL, "
            + COLUMN_LNG + " REAL, "
            + COLUMN_RAD + " INTEGER, "
            + COLUMN_POEMID + " INTEGER, FOREIGN KEY ("
            + COLUMN_POEMID + ")" + " REFERENCES " + TABLE_POEM + "(" + COLUMN_POEMID + "))";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_POEM);
        db.execSQL(CREATE_TABLE_POINTS);
        Log.i("Info", "Records inserted");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POEM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POINTS);
        onCreate(db);
    }

    public ArrayList<Poem> getAllPoems() {
        ArrayList<Poem> poems = new ArrayList<Poem>();

        String selectQuery = "SELECT " + COLUMN_FIRSTID + ","
                + COLUMN_TITLE + " FROM " + TABLE_POEM;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                Poem poem = new Poem(id, title);
                poems.add(poem);
                Log.d("Data", poem.toString());
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return poems;
    }
    public ArrayList<Point> getAllPointById() {
        ArrayList<Point> points = new ArrayList<Point>();

        String selectQuery = "SELECT " + COLUMN_SECONDID + ","
                + COLUMN_LAT + ","
                + COLUMN_LNG + ","
                + COLUMN_RAD
                + " FROM " + TABLE_POINTS + " WHERE poem_id=" + COLUMN_POEMID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                double latitude = cursor.getDouble(1);
                double longitude = cursor.getDouble(2);
                int radius = cursor.getInt(3);
                Point point = new Point(id, latitude, longitude, radius);
                points.add(point);
                Log.d("Data", point.toString());
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return points;
    }


}
