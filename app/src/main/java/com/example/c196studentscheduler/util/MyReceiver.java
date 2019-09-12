package com.example.c196studentscheduler.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;

import com.example.c196studentscheduler.R;

import static android.content.Context.NOTIFICATION_SERVICE;
/**
 * Chris Richardson
 * C196
 * Student ID #000895452
 */
public class MyReceiver extends BroadcastReceiver {

    static int notificationID;
    String startChannelName = "START CHANNEL";
    String startChannelId = "com.example.c196studentscheduler.Start";
    String title;
    String notificationType;
    String contentText;
    String contentTitle;

    /**
     *
     * @param context
     * @param intent
     *
     * Set the title body text based on the notification type
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        notificationType = intent.getExtras().getString(Constants.NOTIFICATION_TYPE);

        if(notificationType.equals("Assessment")) {
            title = intent.getStringExtra(Constants.ASSESSMENT_NAME);
            contentText = title + " is due today!";
            contentTitle = "Assessment Due!";
        } else if (notificationType.equals("course_start")) {
            title = intent.getStringExtra(Constants.COURSE_NAME);
            contentText = title + " starts today!";
            contentTitle = "Course Start";
        } else if (notificationType.equals("course_end")) {
            title = intent.getStringExtra(Constants.COURSE_NAME);
            contentText = title + " ends today";
            contentTitle = "End of Course";
        }
        //create the notification channel
        createNotificationChannel(context, startChannelId, startChannelName);
        Notification n = new NotificationCompat.Builder(context, startChannelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText(contentText)
                .setContentTitle(contentTitle).build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID++, n);

    }
    private void createNotificationChannel(Context context, String CHANNEL_ID, String name) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(Constants.DESCRIPTION);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}

