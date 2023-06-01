package com.example.feelthebeat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

public class MyReceiver extends BroadcastReceiver {

    TextView tv;
    MyReceiver(TextView tv){
        this.tv=tv;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");
        int percentOfLevel = intent.getIntExtra("level", 0);
        if(percentOfLevel!=0)
            tv.setText(percentOfLevel+"%");
    }
}