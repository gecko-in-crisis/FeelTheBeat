package com.example.feelthebeat;

import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class Piano extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    TextView tvBattery, tvName, tvPlay;
    IntentFilter intentBatteryFilter;
    MyReceiver myReceiver;
    private SoundPool soundPool;

    private int[] mSoundIds;
    private int csound, dsound, esound, fsound,
            gsound, asound, bsound, cssound, dssound, gssound, assound, fssound;
    private float LEFT_VOL=1.0f, RIGHT_VOL=1.0f, RATE = 1.0f;
    private int PRIORITY = 1,  LOOP = 0;

    Accelerometer accelerometer;
    Handler handler=new Handler();
    Switch aSwitch;
    boolean isPlaying=false;

    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piano);

        aSwitch=findViewById(R.id.switch2);
        aSwitch.setOnCheckedChangeListener(this);
        tvPlay=findViewById(R.id.tvPlay);

        tvBattery=findViewById(R.id.tv);
        tvName=findViewById(R.id.tvName);
        myReceiver=new MyReceiver(tvBattery);
        tvBattery.setVisibility(View.VISIBLE);
        intentBatteryFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(myReceiver, intentBatteryFilter);

        name=getIntent().getExtras().getString("Name");
        tvName.setText("Ready to play, "+name+"?");

        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
        mSoundIds = new int[7];
        mSoundIds[0] = soundPool.load(this, R.raw.b3, 1);
        mSoundIds[1] = soundPool.load(this, R.raw.a3, 1);
        mSoundIds[2] = soundPool.load(this, R.raw.g3, 1);
        mSoundIds[3] = soundPool.load(this, R.raw.f3, 1);
        mSoundIds[4] = soundPool.load(this, R.raw.e3, 1);
        mSoundIds[5] = soundPool.load(this, R.raw.d3, 1);
        mSoundIds[6] = soundPool.load(this, R.raw.c3, 1);

        csound = soundPool.load(getApplicationContext(),R.raw.c3,1);
        dsound = soundPool.load(getApplicationContext(),R.raw.d3,1);
        esound = soundPool.load(getApplicationContext(),R.raw.e3,1);
        fsound = soundPool.load(getApplicationContext(),R.raw.f3,1);
        gsound = soundPool.load(getApplicationContext(),R.raw.g3,1);
        asound = soundPool.load(getApplicationContext(),R.raw.a3,1);
        bsound = soundPool.load(getApplicationContext(),R.raw.b3,1);
        cssound = soundPool.load(getApplicationContext(),R.raw.c3_hash,1);
        assound = soundPool.load(getApplicationContext(),R.raw.a3_hash,1);
        fssound = soundPool.load(getApplicationContext(),R.raw.f3_hash,1);
        gssound = soundPool.load(getApplicationContext(),R.raw.g3_hash,1);
        dssound = soundPool.load(getApplicationContext(),R.raw.d3_hash,1);

        accelerometer = new Accelerometer(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if(isChecked){
            tvPlay.setVisibility(View.VISIBLE);
            onResume();
            accelerometer.setListener(new Accelerometer.Listener() {
                //on translation method of accelerometer
                @Override
                public void onTranslation(float x, float y, float z) {
                    if (y > 4.0f&&!isPlaying) {
                        playSoundsForward();
                    } else if (y < -4.0f && !isPlaying) {
                        playSoundsBackward();
                    }
                }
            });
        }

        else{
            tvPlay.setVisibility(View.INVISIBLE);
            isPlaying=false;
            onPause();
        }

    }

    private void playSoundsForward() {
        isPlaying = true;
        for (int i = mSoundIds.length - 1; i >= 0; i--) {
            final int soundsId = mSoundIds[i];
            final int id = i;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    soundPool.play(soundsId, LEFT_VOL, RIGHT_VOL, PRIORITY, LOOP, RATE);
                    if (id == 0)
                        isPlaying = false;
                }
            }, (mSoundIds.length - i - 1) * 200);
        }
    }

    private void playSoundsBackward() {
        isPlaying = true;
        for (int i = 0; i < mSoundIds.length; i++) {
            final int soundsId = mSoundIds[i];
            final int id = i;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    soundPool.play(soundsId, LEFT_VOL, RIGHT_VOL, PRIORITY, LOOP, RATE);
                    if (id == mSoundIds.length - 1)
                        isPlaying = false;
                }
            }, i * 200);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // this will send notification to
        // both the sensors to register
        accelerometer.register();
    }
    // create on pause method
    @Override
    protected void onPause() {
        super.onPause();
        // this will send notification in
        // both the sensors to unregister
        accelerometer.unregister();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        MenuItem item=menu.findItem(R.id.piano);
        item.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.drums){
            Intent go=new Intent(this, Drums.class);
            go.putExtra("Name", name);
            startActivity(go);
            finish();
        }
        if(id==R.id.home){
            Intent go=new Intent(this, Home.class);
            go.putExtra("Name", name);
            startActivity(go);
            finish();
        }
        if(id==R.id.fragmentHelp){
            HowFragmentPiano howFragmentPiano=new HowFragmentPiano();
            FragmentTransaction transaction=
                    getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.howFragmentP, howFragmentPiano, "howFragment");
            transaction.addToBackStack(null);
            transaction.commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void playC(View v){
        soundPool.play(csound,LEFT_VOL,RIGHT_VOL,PRIORITY,LOOP,RATE);
    }

    public void playD(View v){
        soundPool.play(dsound,LEFT_VOL,RIGHT_VOL,PRIORITY,LOOP,RATE);
    }

    public void playE(View v){
        soundPool.play(esound,LEFT_VOL,RIGHT_VOL,PRIORITY,LOOP,RATE);
    }

    public void playF(View v){
        soundPool.play(fsound,LEFT_VOL,RIGHT_VOL,PRIORITY,LOOP,RATE);
    }

    public void playG(View v){
        soundPool.play(gsound,LEFT_VOL,RIGHT_VOL,PRIORITY,LOOP,RATE);
    }

    public void playA(View v){
        soundPool.play(asound,LEFT_VOL,RIGHT_VOL,PRIORITY,LOOP,RATE);
    }

    public void playB(View v){
        soundPool.play(bsound,LEFT_VOL,RIGHT_VOL,PRIORITY,LOOP,RATE);
    }

    public void playCS(View v){
        soundPool.play(cssound,LEFT_VOL,RIGHT_VOL,PRIORITY,LOOP,RATE);
    }

    public void playDS(View v){
        soundPool.play(dssound,LEFT_VOL,RIGHT_VOL,PRIORITY,LOOP,RATE);
    }

    public void playFS(View v){
        soundPool.play(fssound,LEFT_VOL,RIGHT_VOL,PRIORITY,LOOP,RATE);
    }

    public void playGS(View v){
        soundPool.play(gssound,LEFT_VOL,RIGHT_VOL,PRIORITY,LOOP,RATE);
    }

    public void playAS(View v){
        soundPool.play(assound,LEFT_VOL,RIGHT_VOL,PRIORITY,LOOP,RATE);
    }


}