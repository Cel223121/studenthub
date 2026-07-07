package com.example.studenthub.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "StudentHub.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "CREATE TABLE students(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "name TEXT," +
                        "email TEXT UNIQUE," +
                        "course TEXT," +
                        "year INTEGER," +
                        "phone TEXT," +
                        "password TEXT)"
        );

        db.execSQL(
                "CREATE TABLE attendance(" +
                        "attendanceId INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "studentId INTEGER," +
                        "date TEXT," +
                        "status TEXT," +
                        "FOREIGN KEY(studentId) REFERENCES students(id))"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS attendance");
        db.execSQL("DROP TABLE IF EXISTS students");

        onCreate(db);
    }

    // =========================
    // INSERT
    // =========================
    public boolean insertData(String name, String email,
                              String course, int year, String phone, String password) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("email", email);
        values.put("course", course);
        values.put("year", year);
        values.put("phone", phone);
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
                                 String email, String course, int year, String phone, String password) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("email", email);
        values.put("course", course);
        values.put("year", year);
        values.put("phone", phone);
        
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

    // =========================
    // SAVE ATTENDANCE
    // =========================
    public boolean saveAttendance(
            int studentId,
            String date,
            String status) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("studentId", studentId);
        values.put("date", date);
        values.put("status", status);

        long result = db.insert(
                "attendance",
                null,
                values
        );

        return result != -1;
    }

    // =========================
    // GET ATTENDANCE
    // =========================
    public Cursor getAttendanceRecords() {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(
                "SELECT * FROM attendance",
                null
        );
    }

    public Cursor getAllAttendance() {

        SQLiteDatabase db =
                this.getReadableDatabase();

        // Left Join to show attendance even if student details are missing
        return db.rawQuery(
                "SELECT s.name, a.date, a.status, a.studentId FROM attendance a " +
                        "LEFT JOIN students s ON s.id = a.studentId " +
                        "ORDER BY a.attendanceId DESC",
                null);
    }
}