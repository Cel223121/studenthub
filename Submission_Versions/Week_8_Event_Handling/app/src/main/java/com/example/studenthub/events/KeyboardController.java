package com.example.studenthub.events;

import android.content.Context;
import android.widget.Toast;

public class KeyboardController {

    private Context context;

    public KeyboardController(Context context) {
        this.context = context;
    }

    public boolean validateInput(String text, String fieldName) {

        if (text == null || text.trim().isEmpty()) {
            Toast.makeText(context,
                    fieldName + " cannot be empty",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public boolean validateEmail(String email) {

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            Toast.makeText(context,
                    "Invalid Email Address",
                    Toast.LENGTH_SHORT).show();

            return false;
        }

        return true;
    }

    public void onKeyPressed(String key) {

        Toast.makeText(
                context,
                "Key Pressed: " + key,
                Toast.LENGTH_SHORT
        ).show();
    }
}