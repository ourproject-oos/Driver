package com.example.driver;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.driver.ui.driver.SlideshowFragment;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import static com.example.driver.JobService.CHANNEL_ID;

public class IntentService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");

        Intent notificationIntent = new Intent(getApplicationContext(), SlideshowFragment.class);
        PendingIntent pendingIntent= PendingIntent.getActivity(getApplication(),
                0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(getApplication(),CHANNEL_ID)
                .setContentTitle("Traffic Go")
                .setContentText(input)
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
