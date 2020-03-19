package com.example.serviceback;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.serviceback.App.CHANNEL_ID;

public class ExampleService extends Service {
    String input = "";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        input = intent.getStringExtra("inputExtra");

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Example Service")
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(pendingIntent)
                .build();

        if(input.equals("2")){
            Intent i = new Intent("edu.cs4730.bcr.mystaticevent");
            i.setPackage("com.example.applicationimportserviceback"); //in API 26, it must be explicit now.
            i.putExtra("teste", "resp");
            //since it's registered as a global (in the manifest), use sendBroadCast
            //LocalBroadcastManager.getInstance(getContext()).sendBroadcast(i);
            sendBroadcast(i);
        }

       // startForeground(1, notification);

        return START_NOT_STICKY;
    }

    public String testService(){
        Toast.makeText(this, "ServiceReturn", Toast.LENGTH_LONG).show();
        return "VIVO";
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
