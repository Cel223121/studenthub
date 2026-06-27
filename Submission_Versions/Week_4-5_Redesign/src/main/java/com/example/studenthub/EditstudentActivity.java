package com.example.studenthub.activities;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studenthub.database.DatabaseHelper;
import com.example.studenthub.R;

public class EditStudentActivity extends AppCompatActivity {

    EditText edtName, edtEmail, edtCourse, edtRegNo;
    Button btnUpdate;

    DatabaseHelper db;
    int studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);

        db = new DatabaseHelper(this);

        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtCourse = findViewById(R.id.edtCourse);
        edtRegNo = findViewById(R.id.edtRegNo);
        btnUpdate = findViewById(R.id.btnUpdate);

        // GET DATA FROM ADAPTER
        studentId = getIntent().getIntExtra("id", -1);
        edtName.setText(getIntent().getStringExtra("name"));
        edtEmail.setText(getIntent().getStringExtra("email"));
        edtCourse.setText(getIntent().getStringExtra("course"));
        edtRegNo.setText(getIntent().getStringExtra("regNo"));

        btnUpdate.setOnClickListener(v -> {

            String name = edtName.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String course = edtCourse.getText().toString().trim();
            String regNo = edtRegNo.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || course.isEmpty() || regNo.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            SQLiteDatabase database = db.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("email", email);
            values.put("course", course);
            values.put("regNo", regNo);

            int result = database.update(
                    "students",
                    values,
                    "id=?",
                    new String[]{String.valueOf(studentId)}
            );

            database.close();

            if (result > 0) {
                Toast.makeText(this, "Student Updated", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}