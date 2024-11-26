package com.amosayomide.radio;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Extract title and message from the intent
        String title = intent.getStringExtra("title");
        String message = intent.getStringExtra("message");

        // Send the notification when the alarm goes off
        NotificationHelper.sendNotification(context, title, message);
    }
}
