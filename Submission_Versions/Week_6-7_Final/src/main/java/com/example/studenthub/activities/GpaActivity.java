package com.example.studenthub.activities;

import com.example.studenthub.R;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GpaActivity extends AppCompatActivity {

    EditText etCat, etAssignment, etExam;
    Button btnCalculate;
    TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gradepointaverageestimator);

        etCat = findViewById(R.id.etCat);
        etAssignment = findViewById(R.id.etAssignment);
        etExam = findViewById(R.id.etExam);

        btnCalculate = findViewById(R.id.btnCalculate);
        txtResult = findViewById(R.id.txtResult);

        btnCalculate.setOnClickListener(v -> {

            String catText = etCat.getText().toString();
            String assignmentText = etAssignment.getText().toString();
            String examText = etExam.getText().toString();

            if (catText.isEmpty() ||
                    assignmentText.isEmpty() ||
                    examText.isEmpty()) {

                txtResult.setText("Please enter all marks.");
                return;
            }

            double cat = Double.parseDouble(catText);
            double assignment = Double.parseDouble(assignmentText);
            double exam = Double.parseDouble(examText);

            double total = cat + assignment + exam;

            String grade;
            double gpa;

            if (total >= 70) {
                grade = "A";
                gpa = 4.0;
            } else if (total >= 60) {
                grade = "B";
                gpa = 3.0;
            } else if (total >= 50) {
                grade = "C";
                gpa = 2.0;
            } else if (total >= 40) {
                grade = "D";
                gpa = 1.0;
            } else {
                grade = "E";
                gpa = 0.0;
            }

            txtResult.setText(
                    "TOTAL MARKS: " + total +
                            "\n\nGRADE: " + grade +
                            "\n\nESTIMATED GPA: " + gpa
            );
        });
    }
}