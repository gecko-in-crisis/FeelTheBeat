package com.example.feelthebeat;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class Home extends AppCompatActivity {
    String name;

    MyReceiver myReceiver;
    IntentFilter filter;
    TextView tv, tvName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tv=findViewById(R.id.tv);
        tvName=findViewById(R.id.tvName);
        myReceiver=new MyReceiver(tv);
        tv.setVisibility(View.VISIBLE);
        filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(myReceiver, filter);
        name=getIntent().getExtras().getString("Name");
        tvName.setText("Welcome, "+name+"!");
    }


    public void openDrums(View view) {
        Intent x=new Intent(this, Drums.class);
        x.putExtra("Name", name);
        startActivity(x);
        finish();
    }

    public void openPiano(View view) {
        Intent intent=new Intent(this, Piano.class);
        intent.putExtra("Name", name);
        startActivity(intent);
        finish();
    }
}