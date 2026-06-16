package com.example.studenthub.database;

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

    // =========================
    // INSERT
    // =========================
    public boolean insertData(String name, String email,
                              String course, String password) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("email", email);
        values.put("course", course);
        values.put("password", password);

        long result = db.insert("students", null, values);
        db.close();

        return result != -1;
    }

    // =========================
    // LOGIN
    // =========================
    public boolean checkLogin(String email, String password) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM students WHERE email=? AND password=?",
                new String[]{email, password});

        boolean exists = cursor.getCount() > 0;
        cursor.close();

        return exists;
    }

    // =========================
    // READ
    // =========================
    public Cursor getAllStudents() {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM students", null);
    }

    public Cursor getStudentByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM students WHERE email=?", new String[]{email});
    }

    // =========================
    // UPDATE
    // =========================
    public boolean updateStudent(int id, String name,
                                 String email, String course, String password) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("email", email);
        values.put("course", course);
        
        if (password != null && !password.isEmpty()) {
            values.put("password", password);
        }

        int result = db.update(
                "students",
                values,
                "id=?",
                new String[]{String.valueOf(id)}
        );

        db.close();

        return result > 0;
    }

    // =========================
    // DELETE
    // =========================
    public boolean deleteStudent(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete(
                "students",
                "id=?",
                new String[]{String.valueOf(id)}
        );

        db.close();

        return result > 0;
    }

    // =========================
    // CHANGE PASSWORD
    // =========================
    public boolean updatePassword(String email, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", newPassword);
        int result = db.update("students", values, "email=?", new String[]{email});
        db.close();
        return result > 0;
    }
}