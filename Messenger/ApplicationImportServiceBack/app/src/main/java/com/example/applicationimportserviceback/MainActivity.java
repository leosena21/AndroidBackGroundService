package com.example.applicationimportserviceback;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editTextInput;
    MyReceiver mReceiver;
    Messenger mService = null;
    boolean mIsBound;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextInput = findViewById(R.id.edit_text_input);

        mReceiver = new MyReceiver();
    }

    public void startService(View V){
        doBindService();
    }

    public void stopService(View V){
        withMsg();
    }

    private void withMsg() {
        Message msg = Message.obtain(null,
                21, this.hashCode(), Integer.parseInt(editTextInput.getText().toString()));
        msg.replyTo = mMessenger;
        try {
            mService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    ///--------------------------------------------

    // Bring user to the market or let them choose an app?
//    intent = new Intent(Intent.ACTION_VIEW);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.setData(Uri.parse("market://details?id=" + "com.example.serviceback"));
//    startActivity(intent);


    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            Toast.makeText(getApplicationContext(), "RespRecebidaServidor--:"+msg.arg2, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Target we publish for clients to send messages to IncomingHandler.
     */
    final Messenger mMessenger = new Messenger(new IncomingHandler());

    private ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder service) {
//            Toast.makeText(getApplicationContext(), "sn:"+service.toString(),
//                    Toast.LENGTH_SHORT).show();

            mService = new Messenger(service);

            try {

                // Give it some value as an example.
                Message msg = Message.obtain(null,
                        1, this.hashCode(), 1);
                msg.replyTo = mMessenger;
                mService.send(msg);
            } catch (RemoteException e) {

                Toast.makeText(getApplicationContext(), "catch1--:"+e.toString(),
                        Toast.LENGTH_SHORT).show();
            }


        }

        public void onServiceDisconnected(ComponentName className) {

            mService = null;
            Toast.makeText(getApplicationContext(),"Handler-Disconnect---",
                    Toast.LENGTH_SHORT).show();
        }
    };


    public   void doBindService() {

        Intent intentForMcuService = new Intent();
        intentForMcuService.setComponent(new ComponentName("com.example.serviceback", "com.example.serviceback.NewService"));
        if(intentForMcuService!=null) {
            if (getApplicationContext().bindService(intentForMcuService, mConnection, Context.BIND_AUTO_CREATE
            )) {
                Toast.makeText(getApplicationContext(), "binded",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "not binded",
                        Toast.LENGTH_SHORT).show();
            }

            mIsBound = true;
            Toast.makeText(getApplicationContext(), "binding-client",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            // Bring user to the market or let them choose an app?
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("market://details?id=" + "com.example.serviceback"));
            startActivity(intent);
        }
    }
}
