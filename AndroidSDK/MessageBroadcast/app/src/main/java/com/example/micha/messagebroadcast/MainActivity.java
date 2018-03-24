package com.example.micha.messagebroadcast;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String intent_string ="com.micha.android.MESSAGE1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //
    public void send(View v){
        EditText e = findViewById(R.id.message);
        Intent i = new Intent(intent_string);
        i.putExtra("message", e.getText().toString());
        sendBroadcast(i);
        Toast.makeText(getApplicationContext(), "Wiadomość została wysłana!", Toast.LENGTH_LONG).show();
    }
}
