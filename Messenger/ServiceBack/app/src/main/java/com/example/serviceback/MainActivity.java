package com.example.serviceback;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    private EditText editTextInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, "I'm alive", Toast.LENGTH_LONG).show();

        Intent intent = getIntent();
        String rec = intent.getStringExtra("reciveApp");
        String pac = intent.getStringExtra("package");
        String css = intent.getStringExtra("class");

        if(rec!=null && !rec.equals("")) {
            startService(rec, pac, css);
        }
        else {
            stopService();
        }

        finish();
    }

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //toast "Broadcast received"
        }
    }

    public void startService(String input, String pac, String css){

        Intent serviceIntent = new Intent(this, ExampleService.class);
        serviceIntent.putExtra("inputExtra", input);
        serviceIntent.putExtra("package", pac);
        serviceIntent.putExtra("class", css);

        startService(serviceIntent);
    }

    public void stopService(){

        Intent serviceIntent = new Intent(this, ExampleService.class);
        stopService(serviceIntent);

    }
}
