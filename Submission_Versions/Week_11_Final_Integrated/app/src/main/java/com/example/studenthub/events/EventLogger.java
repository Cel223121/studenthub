package com.example.studenthub.events;

import android.util.Log;

public class EventLogger {

    private static final String TAG = "StudentHubEvents";

    public static void logEvent(String event) {

        Log.d(TAG, event);

    }

}