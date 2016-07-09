package com.example.razon30.movietest;

/**
 * Created by razon30 on 08-07-16.
 */

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;

import br.com.goncalves.pugnotification.notification.PugNotification;

public class AlarmService extends IntentService {
    private NotificationManager alarmNotificationManager;

    public AlarmService() {
        super("AlarmService");
    }

    @Override
    public void onHandleIntent(Intent intent) {
        sendNotification("Wake Up! Wake Up!");
    }

    private void sendNotification(String msg) {

        String subtopic = "You have set an alarm to watch a movie";

        Intent notificationIntent = new Intent(this, HomeActivity.class);
        notificationIntent.putExtra("subtopic", subtopic);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(500);

        PugNotification.with(this)
                .load()
                .click(contentIntent)
                .title("Total Movie")
                .message(subtopic)
                .bigTextStyle(subtopic)
                .smallIcon(R.mipmap.ic_launcher)
                .largeIcon(R.mipmap.ic_launcher)
                .flags(Notification.DEFAULT_ALL)
                .color(R.color.colorPrimary)
                .autoCancel(false)
                .simple()
                .build();
    }
}
