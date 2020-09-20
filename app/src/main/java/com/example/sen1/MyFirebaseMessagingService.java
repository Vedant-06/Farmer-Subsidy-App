package com.example.sen1;

import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;



public class MyFirebaseMessagingService extends FirebaseMessagingService
{
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage)
    {
        super.onMessageReceived(remoteMessage);
        shownotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
    }

    private void shownotification(String Title, String Message)
    {
        NotificationCompat.Builder b=new NotificationCompat.Builder(this,"MyNotification1")
                .setContentTitle(Title)
                .setSmallIcon(R.mipmap.farmer)
                .setAutoCancel(true)
                .setContentText(Message);
        NotificationManagerCompat manager=NotificationManagerCompat.from(this);
        manager.notify(999,b.build());

    }

}
