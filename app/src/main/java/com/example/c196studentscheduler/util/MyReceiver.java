package com.example.c196studentscheduler.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.c196studentscheduler.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class MyReceiver extends BroadcastReceiver {

    static int notificationID;
    String date;
    String title;
    String notificationType;
    String contentText;
    String contentTitle;

    @Override
    public void onReceive(Context context, Intent intent) {
        notificationType = intent.getExtras().getString(Constants.NOTIFICATION_TYPE);

        if(notificationType.equals("Assessment")) {
            title = intent.getStringExtra(Constants.ASSESSMENT_NAME);
            contentText = title + " is due today!";
            contentTitle = "Assessment Due";
        } else if (notificationType.equals("course_start")) {
            title = intent.getStringExtra(Constants.COURSE_NAME);
            contentText = title + " starts today!";
            contentTitle = "Course Start";
        } else if (notificationType.equals("course_end")) {
            title = intent.getStringExtra(Constants.COURSE_NAME);
            contentText = title + " ends today";
            contentTitle = "End of Course";
        }
            Toast.makeText(context, "Notification", Toast.LENGTH_LONG).show();
            createNotificationChannel(context, Constants.CHANNEL_ID);
            Notification n = new NotificationCompat.Builder(context, Constants.CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentText(contentText)
                    .setContentTitle(contentTitle).build();

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(notificationID++, n);




        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        // throw new UnsupportedOperationException("Not yet implemented");
    }
    private void createNotificationChannel(Context context, String CHANNEL_ID) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, Constants.ANDROID_CHANNEL_NAME, importance);
            channel.setDescription(Constants.DESCRIPTION);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}

