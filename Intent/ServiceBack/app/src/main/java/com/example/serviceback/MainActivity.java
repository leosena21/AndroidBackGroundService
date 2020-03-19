package com.example.serviceback;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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
        if(rec!=null) {
            if (rec.equals("1")) {
                startService(rec);
            } else {
                stopService();
            }
        }

        finish();
    }

    public void startService(String input){

        Intent serviceIntent = new Intent(this, ExampleService.class);
        serviceIntent.putExtra("inputExtra", input);

        startService(serviceIntent);
    }

    public void stopService(){

        Intent serviceIntent = new Intent(this, ExampleService.class);
        stopService(serviceIntent);

    }
}
