package com.example.cropconnoisseur.Notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.navigation.NavDeepLinkBuilder;

import com.example.cropconnoisseur.HomeActivity;
import com.example.cropconnoisseur.ModelActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessaging extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMessaging";

    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    final String NOTIFICATION_CHANNEL_ID = "MY_CHANNEL_ID";


    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                String refreshToken = task.getResult();

                if(firebaseUser != null){
                    updateToken(refreshToken);
                }
            }
        });


    }

    private void updateToken(String refreshToken) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token = new Token(refreshToken);
        reference.child(firebaseUser.getUid()).setValue(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String sented = remoteMessage.getData().get("sented");

        if(firebaseUser != null && sented.equals(firebaseUser.getUid())){

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                sendOreoNotification(remoteMessage);
            }else {
                sendNotification(remoteMessage);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendOreoNotification(RemoteMessage remoteMessage){

        String user = remoteMessage.getData().get("user");
        String icon = remoteMessage.getData().get("icon");
        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");

        RemoteMessage.Notification notification = remoteMessage.getNotification();

        String[] s = body.trim().split("\\r?\\n");
        Log.d(TAG, "sendOreoNotification: " + s[1]);

        String[] temp = s[1].split(":");
        Log.d(TAG, "sendOreoNotification: " + temp[1]);

        int j =Integer.parseInt(user.replaceAll( "[\\D]", ""));
        Intent intent = new Intent(this, ModelActivity.class);
        intent.putExtra("cropDisease",temp[1].trim());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,j,intent,PendingIntent.FLAG_ONE_SHOT);


        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        OreoNotification oreoNotification = new OreoNotification(this);
        Notification.Builder builder = oreoNotification.getOreoNotification(title, body,pendingIntent,
                defaultSound,icon);

        int i = 0;
        if(j > 0){
            i = j;
        }

        oreoNotification.getManager().notify(i,builder.build());

    }

    private void sendNotification(RemoteMessage remoteMessage) {

        String user = remoteMessage.getData().get("user");
        String icon = remoteMessage.getData().get("icon");
        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");

        RemoteMessage.Notification notification = remoteMessage.getNotification();

        String[] s = body.trim().split("\\r?\\n");
        Log.d(TAG, "sendOreoNotification: " + s[1]);

        String[] temp = s[1].split(":");
        Log.d(TAG, "sendOreoNotification: " + temp[1]);

        int j =Integer.parseInt(user.replaceAll( "[\\D]", ""));
        Intent intent = new Intent(this, ModelActivity.class);
        intent.putExtra("cropDisease",temp[1].trim());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,j,intent,PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(Integer.parseInt(icon))
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setAutoCancel(true)
                .setSound(defaultSound)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        int i = 0;
        if(j > 0){
            i = j;
        }

        notificationManager.notify(i,builder.build());

    }
}
