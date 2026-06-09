package com.example.studenthub;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etLoginEmail, etLoginPassword;
    Button btnLogin;
    TextView txtRegister;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPassword = findViewById(R.id.etLoginPassword);

        btnLogin = findViewById(R.id.btnLogin);
        txtRegister = findViewById(R.id.txtRegister);

        databaseHelper = new DatabaseHelper(this);

        txtRegister.setOnClickListener(v -> {

            startActivity(new Intent(
                    MainActivity.this,
                    RegisterActivity.class));
        });

        btnLogin.setOnClickListener(v -> {

            String email = etLoginEmail.getText().toString();
            String password = etLoginPassword.getText().toString();

            boolean checkLogin = databaseHelper.checkLogin(
                    email, password);

            if(checkLogin) {

                Toast.makeText(MainActivity.this,
                        "Login Successful",
                        Toast.LENGTH_SHORT).show();

                startActivity(new Intent(
                        MainActivity.this,
                        DashboardActivity.class));

            } else {

                Toast.makeText(MainActivity.this,
                        "Invalid Email or Password",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}