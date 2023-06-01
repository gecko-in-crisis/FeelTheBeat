package com.example.feelthebeat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AboutApp extends AppCompatActivity {
    Intent service;
    MyReceiver myReceiver;
    IntentFilter filter;
    TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        tv=findViewById(R.id.tv);
        myReceiver=new MyReceiver(tv);
        tv.setVisibility(View.VISIBLE);
        filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(myReceiver, filter);
        service=new Intent(AboutApp.this, MyService.class);
        stopService(service);
    }

    public void goMain(View view) {
        Intent go=new Intent(this, MainActivity.class);
        startActivity(go);
        finish();
    }
}