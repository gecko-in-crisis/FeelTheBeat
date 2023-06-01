package com.example.feelthebeat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {
    TextInputLayout etLogName, etLogPass;
    String name, pass;

    HelperDB hlp=new HelperDB(this);
    SQLiteDatabase db;

    MyReceiver myReceiver;
    IntentFilter filter;
    TextView tv;
    Intent service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tv=findViewById(R.id.tv);
        myReceiver=new MyReceiver(tv);
        tv.setVisibility(View.VISIBLE);
        filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(myReceiver, filter);

        etLogName=findViewById(R.id.etLogName);
        etLogPass=findViewById(R.id.etLogPass);
        service=new Intent(Login.this, MyService.class);
        stopService(service);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.back){
            Intent go=new Intent(this, MainActivity.class);
            startActivity(go);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void registration(View view) {
        Intent intReg = new Intent(this, Register.class);
        startActivity(intReg);
        finish();
    }

    public void Login(View view) {
        name=etLogName.getEditText().getText().toString();
        pass=etLogPass.getEditText().getText().toString();
        if(name.equals("")||pass.equals("")) {
            Toast.makeText(Login.this, "Empty data!", Toast.LENGTH_SHORT).show();
            return;
        }
        find();
    }
    @SuppressLint("Range")
    public void find(){
        db=hlp.getWritableDatabase();
        boolean userExists=false;
        Cursor c=db.query(HelperDB.TABLE_NAME, null, null,
                null, null, null, null);
        c.moveToFirst();
        int n=c.getCount();
        if(c.getColumnIndex(hlp.NAME)!=-1){
            for (int i=0; i<n; i++) {
                String nameC=c.getString(0);
                String passC=c.getString(1);
                if (nameC.equals(name)&&passC.equals(pass)) {
                    userExists = true;
                    Intent x = new Intent(this, Home.class);
                    x.putExtra("Name", name);
                    startActivity(x);
                    finish();
                }
                c.moveToNext();
            }
        }

        if(userExists==false){
            Toast.makeText(Login.this, "This user does not exist!", Toast.LENGTH_SHORT).show();
        }
    }
}