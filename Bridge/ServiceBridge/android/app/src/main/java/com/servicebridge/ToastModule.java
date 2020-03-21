package com.servicebridge;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.HashMap;
import java.util.Map;

import static com.facebook.react.bridge.UiThreadUtil.runOnUiThread;

public class ToastModule extends ReactContextBaseJavaModule {

    private static  final String LENGTH_SHORT = "LENGTH_SHORT";
    private static  final String LENGTH_LONG = "LENGTH_LONG";

    Messenger mService = null;
    boolean mIsBound;
    Messenger mMessenger = new Messenger(new IncomingHandler());

    public ToastModule(ReactApplicationContext context) { //constructor
        super(context);

//        new Thread(){
//            public void run(){
//                runOnUiThread(new Runnable() {
//                    public void run() {
//                        mMessenger = new Messenger(new IncomingHandler());
//                        // Toast.makeText(getReactApplicationContext(), "Status = " , Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//        }.start();
    }

    @Override
    public String getName() {
        return "ToastModule";
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put(LENGTH_SHORT, Toast.LENGTH_SHORT);
        constants.put(LENGTH_LONG, Toast.LENGTH_LONG);

        return constants;
    }

    @ReactMethod
    public void withMsg(String stringValue){ //react call this func
        Message msg = Message.obtain(null,
                21, this.hashCode(), Integer.parseInt(stringValue));
        msg.replyTo = mMessenger;
        try {
            mService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            try {
                WritableMap params = Arguments.createMap(); // add here the data you want to send
                params.putInt("arg2", msg.arg2); // <- example
                params.putInt("test", 3); // <- example
                getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                        .emit("onStop", params);
            } finally {

            }
            // Toast.makeText(getReactApplicationContext(), "RespRecebidaServidor--:"+msg.arg2, Toast.LENGTH_LONG).show();
        }
    }

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

                Toast.makeText(getReactApplicationContext(), "catch1--:"+e.toString(),
                        Toast.LENGTH_SHORT).show();
            }


        }

        public void onServiceDisconnected(ComponentName className) {

            mService = null;
            Toast.makeText(getReactApplicationContext(),"Handler-Disconnect---",
                    Toast.LENGTH_SHORT).show();
        }
    };

    @ReactMethod
    public void doBindService() {

        Intent intentForMcuService = new Intent();
        intentForMcuService.setComponent(new ComponentName("com.example.serviceback", "com.example.serviceback.NewService"));
        if(intentForMcuService!=null) {
            if (getReactApplicationContext().bindService(intentForMcuService, mConnection, Context.BIND_AUTO_CREATE
            )) {
                Toast.makeText(getReactApplicationContext(), "binded",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getReactApplicationContext(), "not binded",
                        Toast.LENGTH_SHORT).show();
            }

            mIsBound = true;
            Toast.makeText(getReactApplicationContext(), "binding-client",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            // Bring user to the market or let them choose an app?
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("market://details?id=" + "com.example.serviceback"));
            //startActivity(intent);
        }
    }
}
