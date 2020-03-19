package com.example.serviceback;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.widget.Toast;

public class NewService extends Service {

    final android.os.Messenger mMessenger = new android.os.Messenger(new IncomingHandler());
    private int i=0;
    @SuppressLint("HandlerLeak")
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(android.os.Message msg) {
            i = msg.arg2;
            Toast.makeText(getApplicationContext(), "servidor Recebeu-:" + msg.arg2, Toast.LENGTH_LONG).show();

            try {
                msg.replyTo.send(Message.obtain(null, this.hashCode(), 2, 404));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        //my code
    }
}