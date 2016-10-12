package com.example.yasi27.final2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import junit.framework.Test;

/**
 * Created by yasi27 on 10.10.2016.
 */
public class Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Reminder set!", Toast.LENGTH_LONG).show();
    }
}
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public void onCreate() {
//        Intent intent = new Intent(this, appointment.class);
//        long[] pattern = {0, 300, 0};
//        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
//                .setContentTitle("Doctor appointment")
//                .setContentText("Dont forget your doctor appointment today!")
//                .setVibrate(pattern)
//                .setAutoCancel(true);
//
//        mBuilder.setContentIntent(pi);
//        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
//        mBuilder.setAutoCancel(true);
//        NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
//        mNotificationManager.notify(1, mBuilder.build());
//    }
//}
