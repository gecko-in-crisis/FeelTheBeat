package com.example.feelthebeat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tvBattery;
    IntentFilter intentBatteryFilter;
    MyReceiver myReceiver;
    Intent go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        go = new Intent(MainActivity.this, MyService.class);
        startService(go);

        tvBattery=findViewById(R.id.tv);
        myReceiver=new MyReceiver(tvBattery);
        tvBattery.setVisibility(View.VISIBLE);
        intentBatteryFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(myReceiver, intentBatteryFilter);
    }

    public void openReg(View view) {
        Intent intReg=new Intent(this, Register.class);
        startActivity(intReg);
        finish();
    }

    public void openPageLog(View view) {
        Intent intLog=new Intent(this, Login.class);
        startActivity(intLog);
        finish();
    }

    public void openLearn(View view) {
        Intent go=new Intent(this, AboutApp.class);
        startActivity(go);
        finish();
    }
}