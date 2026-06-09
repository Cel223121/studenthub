package com.example.studenthub;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "StudentHub.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE students(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "email TEXT," +
                "course TEXT," +
                "password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS students");
        onCreate(db);
    }

    public boolean insertData(String name, String email,
                              String course, String password) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("email", email);
        values.put("course", course);
        values.put("password", password);

        long result = db.insert("students", null, values);

        return result != -1;
    }

    public boolean checkLogin(String email, String password) {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM students WHERE email=? AND password=?",
                new String[]{email, password});

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
}