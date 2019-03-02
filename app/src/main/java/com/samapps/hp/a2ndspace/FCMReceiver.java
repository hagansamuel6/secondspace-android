package com.samapps.hp.a2ndspace;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import androidx.core.app.NotificationCompat;

public class FCMReceiver extends FirebaseMessagingService {
    private static final String TAG = "FCMReceiver";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getData().size() > 0){
            Log.d(TAG, "message payload: " + remoteMessage.getData());
        }

        if (remoteMessage.getNotification() != null ){
            Log.d(TAG, "Message Notification body " + remoteMessage.getNotification().getBody());
        }

        String message = remoteMessage.getNotification().getBody();
        sendNotification(message);
    }

    private void sendNotification(String message) {
        Intent intent = new Intent(this, IncomingMessage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Notification", message);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "cid")
                .setSmallIcon(R.drawable.ic_home)
                .setContentTitle("FCM message")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }
}
