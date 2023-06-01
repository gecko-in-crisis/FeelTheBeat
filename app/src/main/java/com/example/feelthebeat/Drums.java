package com.example.feelthebeat;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.io.ByteArrayOutputStream;

public class Drums extends AppCompatActivity {

    private SoundPool sp;
    private int sound1, sound2, sound3, sound4, sound5, sound6, sound7, sound8, sound9;

    HelperDB hlp=new HelperDB(this);
    SQLiteDatabase db;
    String name;

    ImageButton b1, b2, b3, b4, b5, b6, b7, b8, b9;
    int selectedImageButtonId = 0; // Track the selected ImageButton ID

    TextView tv, tvName;
    MyReceiver myReceiver;
    IntentFilter filter;

    private final static int REQUEST_IMAGE_CAPTURE = 1;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drums);

        tv=findViewById(R.id.tvCharge);
        tvName=findViewById(R.id.tvName);
        myReceiver=new MyReceiver(tv);
        tv.setVisibility(View.VISIBLE);
        filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(myReceiver, filter);

        name=getIntent().getExtras().getString("Name");
        tvName.setText("Ready to roll, "+name+"?");
        tv.setText("Hello "+name);

        b1=findViewById(R.id.button1);
        b2=findViewById(R.id.button2);
        b3=findViewById(R.id.button3);
        b4=findViewById(R.id.button4);
        b5=findViewById(R.id.button5);
        b6=findViewById(R.id.button6);
        b7=findViewById(R.id.button7);
        b8=findViewById(R.id.button8);
        b9=findViewById(R.id.button9);
        // Retrieve the image data from the result set
        db=hlp.getReadableDatabase();
        Bitmap imageId_1 = null;
        Bitmap imageId_2 = null;
        Bitmap imageId_3 = null;
        Cursor c= db.query(hlp.TABLE_NAME, null, null,
                null, null, null,null);
        c.moveToFirst();
        int n =c.getCount();
        for(int i=0; i<n;i++){
            if(c.getString(c.getColumnIndexOrThrow(hlp.NAME)).equals(name)){
                imageId_1=getImage(c.getBlob(c.getColumnIndex(hlp.PICTURE_1 )));
                imageId_2=getImage(c.getBlob(c.getColumnIndex(hlp.PICTURE_2 )));
                imageId_3=getImage(c.getBlob(c.getColumnIndex(hlp.PICTURE_3 )));
            }
            else
                c.moveToNext();
        }

        b1.setImageBitmap(imageId_1);
        b2.setImageBitmap(imageId_1);
        b3.setImageBitmap(imageId_1);
        b4.setImageBitmap(imageId_2);
        b5.setImageBitmap(imageId_2);
        b6.setImageBitmap(imageId_2);
        b7.setImageBitmap(imageId_3);
        b8.setImageBitmap(imageId_3);
        b9.setImageBitmap(imageId_3);

        sp=new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        sound1=sp.load(getApplicationContext(),R.raw.sound1,1);
        sound2=sp.load(getApplicationContext(),R.raw.sound2,1);
        sound3=sp.load(getApplicationContext(),R.raw.sound3,1);
        sound4=sp.load(getApplicationContext(),R.raw.sound4,1);
        sound5=sp.load(getApplicationContext(),R.raw.sound5,1);
        sound6=sp.load(getApplicationContext(),R.raw.sound00,1);
        sound7=sp.load(getApplicationContext(),R.raw.sound7,1);
        sound8=sp.load(getApplicationContext(),R.raw.sound8,1);
        sound9=sp.load(getApplicationContext(),R.raw.sound9,1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_home, menu);
        MenuItem item=menu.findItem(R.id.drums);
        item.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.home){
            Intent go=new Intent(this, Home.class);
            go.putExtra("Name", name);
            startActivity(go);
        }
        if(id==R.id.piano){
            Intent go=new Intent(Drums.this, Piano.class);
            go.putExtra("Name", name);
            startActivity(go);
        }
        if(id==R.id.fragmentHelp){
            HowFragment howFragment=new HowFragment();
            FragmentTransaction transaction=
                    getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.howFragment, howFragment, "howFragment");
            transaction.addToBackStack(null);
            transaction.commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private  Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public void playSound1(View view) {
        sp.play(sound1, 1.0f, 1.0f, 0,0,10f);
    }
    public void playSound2(View view) {
        sp.play(sound2, 1.0f, 1.0f, 0,0,10f);
    }
    public void playSound3(View view) {
        sp.play(sound3, 1.0f, 1.0f, 0,0,10f);
    }
    public void playSound4(View view) {
        sp.play(sound4, 1.0f, 1.0f, 0,0,10f);
    }
    public void playSound5(View view) {
        sp.play(sound5, 1.0f, 1.0f, 0,0,10f);
    }
    public void playSound6(View view) {
        sp.play(sound6, 1.0f, 1.0f, 0,0,10f);
    }
    public void playSound7(View view) {
        sp.play(sound7, 1.0f, 1.0f, 0,0,10f);
    }
    public void playSound8(View view) {
        sp.play(sound8, 1.0f, 1.0f, 0,0,10f);
    }
    public void playSound9(View view) {
        sp.play(sound9, 1.0f, 1.0f, 0,0,10f);
    }



    public void changePic(View view) {
        AlertDialog.Builder ask = new AlertDialog.Builder(Drums.this);
        ask.setMessage("Are you sure you want to replace the picture?").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Set the selectedImageButtonId based on the clicked ImageButton
                        if (view.getId() == R.id.changePic1) {
                            selectedImageButtonId = 1;
                        } else if (view.getId() == R.id.changePic2) {
                            selectedImageButtonId = 2;
                        } else if (view.getId() == R.id.changePic3) {
                            selectedImageButtonId = 3;
                        }
                        // Send an implicit intent for the camera
                        // Implement onActivityResult to get the captured image
                        Intent intent = new Intent();
                        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                        // Verify that the intent will resolve to an activity
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        ask.create().show();
    }

    // Convert from bitmap to byte array
    private byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                Toast.makeText(this, "The photo was taken", Toast.LENGTH_SHORT).show();
                if (imageBitmap != null) {
                    db = hlp.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    byte[] imageBytes = getBytes(imageBitmap);
                    String columnName;

                    // Determine the column name based on the selectedImageButtonId
                    switch (selectedImageButtonId) {
                        case 1:
                            columnName = HelperDB.PICTURE_1;
                            b1.setImageBitmap(imageBitmap);
                            b2.setImageBitmap(imageBitmap);
                            b3.setImageBitmap(imageBitmap);
                            break;
                        case 2:
                            columnName = HelperDB.PICTURE_2;
                            b4.setImageBitmap(imageBitmap);
                            b5.setImageBitmap(imageBitmap);
                            b6.setImageBitmap(imageBitmap);
                            break;
                        case 3:
                            columnName = HelperDB.PICTURE_3;
                            b7.setImageBitmap(imageBitmap);
                            b8.setImageBitmap(imageBitmap);
                            b9.setImageBitmap(imageBitmap);
                            break;
                        default:
                            return;
                    }

                    cv.put(columnName, imageBytes);
                    String[] oldData = new String[]{name};
                    db.update(HelperDB.TABLE_NAME, cv, HelperDB.NAME + "=?", oldData);
                }
            }
        }
    }



}