package com.example.applicationimportserviceback;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editTextInput;
    private String packageName = "com.example.serviceback.ExampleService";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextInput = findViewById(R.id.edit_text_input);
    }

    public void startService(View V){

        launchCall();


        //String input = editTextInput.getText().toString();

        //Intent serviceIntent = new Intent(this, ExampleService.class);
        //serviceIntent.putExtra("inputExtra", input);

        //startService(serviceIntent);
    }

    private void launchCall() {

        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.example.serviceback", "com.example.serviceback.MainActivity"));
        if (intent != null) {
            // We found the activity now start the activity
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("reciveApp", editTextInput.getText().toString());
            try{
                //startService(new Intent("com.example.serviceback"));
                startActivity(intent);
            }
            catch (Exception err){
                err.printStackTrace();
            }

        } else {
            // Bring user to the market or let them choose an app?
            intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("market://details?id=" + "com.example.serviceback"));
            startActivity(intent);
        }
    }

    public void stopService(View v){

        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.example.serviceback", "com.example.serviceback.MainActivity"));
        if (intent != null) {
            // We found the activity now start the activity
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("reciveApp", "0");
            try{
                //startService(new Intent("com.example.serviceback"));
                startActivity(intent);
            }
            catch (Exception err){
                err.printStackTrace();
            }

        } else {
            // Bring user to the market or let them choose an app?
            intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("market://details?id=" + "com.example.serviceback"));
            startActivity(intent);
        }

    }
}
