package com.kotlarz_marlene_dogservicescheduler.Utilities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.kotlarz_marlene_dogservicescheduler.R;

public class AppointmentAddReceiver extends BroadcastReceiver {

    public static final String EXTRA_NOTIFICATION_ID =
            "com.kotlarz_marlene_dogservicescheduler.Utilities.EXTRA_NOTIFICATION_ID";
    public static final String EXTRA_NOTIFICATION_CUSTOMER =
            "com.kotlarz_marlene_dogservicescheduler.Utilities.EXTRA_NOTIFICATION_CUSTOMER";
    public static final String EXTRA_NOTIFICATION_PET =
            "com.kotlarz_marlene_dogservicescheduler.Utilities.EXTRA_NOTIFICATION_PET";
    public static final String EXTRA_NOTIFICATION_DATE =
            "com.kotlarz_marlene_dogservicescheduler.Utilities.EXTRA_NOTIFICATION_DATE";
    public static final String EXTRA_NOTIFICATION_TIME =
            "com.kotlarz_marlene_dogservicescheduler.Utilities.EXTRA_NOTIFICATION_TIME";

    public static final String CHANNEL_ID = "CHANNEL_ID";

    @Override
    public void onReceive(Context context, Intent intent) {

        int notificationId = intent.getIntExtra(EXTRA_NOTIFICATION_ID, 0);
        String customerName = intent.getStringExtra(EXTRA_NOTIFICATION_CUSTOMER);
        String pet = intent.getStringExtra(EXTRA_NOTIFICATION_PET);
        String date = intent.getStringExtra(EXTRA_NOTIFICATION_DATE);
        String time = intent.getStringExtra(EXTRA_NOTIFICATION_TIME);

        // Notification Manager
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // For API 26 and above
            CharSequence channelName = "My Notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channelName, importance);
            notificationManager.createNotificationChannel(channel);
        }
        // Prepare Notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_alarm)
                .setContentTitle("Next pet appointment due! Date: "+ date + " / Time: " + time )
                .setContentText("Customer: " + customerName + " / Pet: " + pet);

        // Notify
        notificationManager.notify(notificationId, builder.build());

    }
}