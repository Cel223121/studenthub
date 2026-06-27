package com.example.studenthub.events;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

public class GestureHandler
        extends GestureDetector.SimpleOnGestureListener {

    Context context;

    public GestureHandler(Context context) {
        this.context = context;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        EventLogger.logEvent("Single Tap Detected");
        Toast.makeText(
                context,
                "Single Tap Detected",
                Toast.LENGTH_SHORT
        ).show();

        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        EventLogger.logEvent("Long Press Detected");
        Toast.makeText(
                context,
                "Long Press Detected",
                Toast.LENGTH_SHORT
        ).show();
    }

    @Override
    public boolean onFling(
            MotionEvent e1,
            MotionEvent e2,
            float velocityX,
            float velocityY) {

        if (e2.getX() > e1.getX()) {
            EventLogger.logEvent("Swipe Right Detected");
            Toast.makeText(
                    context,
                    "Swipe Right",
                    Toast.LENGTH_SHORT
            ).show();

        } else {
            EventLogger.logEvent("Swipe Left Detected");
            Toast.makeText(
                    context,
                    "Swipe Left",
                    Toast.LENGTH_SHORT
            ).show();

        }

        return true;
    }
}