package com.example.serviceback;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
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
        String pack = intent.getStringExtra("package");
        String css = intent.getStringExtra("class");

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
            launchCall(pack, css);
        }

        startForeground(1, notification);

        return START_NOT_STICKY;
    }

    private void launchCall(String pack, String css) {

        Intent intent = new Intent();
        intent.setComponent(new ComponentName(pack, css));
        if (intent != null) {
            // We found the activity now start the activity
            intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("reciveApp", "OK");
            try{
                startActivity(intent);
            }
            catch (Exception err){
                err.printStackTrace();
            }

        } else {
            // Bring user to the market or let them choose an app?
            intent = new Intent(Intent.ACTION_VIEW);
            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("market://details?id=" + "com.example.serviceback"));
            startActivity(intent);
        }
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
