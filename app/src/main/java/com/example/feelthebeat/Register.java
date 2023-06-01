package com.example.feelthebeat;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.google.android.material.textfield.TextInputLayout;


public class Register extends AppCompatActivity {
    TextInputLayout name, password;
    String save_name, save_pass;

    HelperDB hlp= new HelperDB(this);
    SQLiteDatabase db;


    int pictureCount = 0;
    Bitmap[] imageBitmaps = new Bitmap[3];
    ImageView[] imageViews = new ImageView[3];

    MyReceiver myReceiver;
    IntentFilter filter;
    TextView tv;
    Intent go;

    private final static int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db=hlp.getWritableDatabase();
        imageViews[0]=findViewById(R.id.btnTakePhoto);
        imageViews[1]=findViewById(R.id.btnTakePhoto2);
        imageViews[2]=findViewById(R.id.btnTakePhoto3);

        tv=findViewById(R.id.tv);
        myReceiver=new MyReceiver(tv);
        tv.setVisibility(View.VISIBLE);
        filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(myReceiver, filter);

        name=findViewById(R.id.et);
        password=findViewById(R.id.etPass);
        go=new Intent(Register.this, MyService.class);
        stopService(go);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA}, 1 );
    }

    public void btnTakePhoto_1(View view) {
       takePhoto(0);
    }

    public void btnTakePhoto_2(View view) {
        takePhoto(1);
    }

    public void btnTakePhoto_3(View view) {
        takePhoto(2);
    }

    private void takePhoto(int index) {
        try {
            // Check if the maximum number of pictures has been reached
            if (pictureCount >= 3) {
                Toast.makeText(this, "You have taken all of the pictures", Toast.LENGTH_SHORT);
            }
            // Send implicit intent for camera
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Verify that the intent will resolve to an activity
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE + index);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Failed to use camera. Make sure you have granted permission to the app.", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode >= REQUEST_IMAGE_CAPTURE && requestCode <= REQUEST_IMAGE_CAPTURE + 2 && resultCode == RESULT_OK) {
            int index = requestCode - REQUEST_IMAGE_CAPTURE;
            // Get the captured image
            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
            // Save the image to the array
            imageBitmaps[index] = imageBitmap;
            pictureCount++;
            // Show the captured image on the corresponding ImageView
            imageViews[index].setImageBitmap(imageBitmap);
            // Show a toast indicating that the photo was taken
            Toast.makeText(this, "Photo " + pictureCount + " was taken", Toast.LENGTH_SHORT).show();
        }
    }

    public void submit(View view) {
        save_name=name.getEditText().getText().toString();
        save_pass= password.getEditText().getText().toString();
        if(save_pass.equals("")||save_name.equals("")||imageBitmaps[0] == null || imageBitmaps[1] == null || imageBitmaps[2] == null){
            Toast.makeText(Register.this, "Empty data!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!validateUsername()||!validatePassword())
            return;
        if(checkIfUserNameExists(save_name)){
            Toast.makeText(Register.this, "This username already exsits", Toast.LENGTH_SHORT).show();
            return;
        }
        insertUserToSQLite();
        name.getEditText().setText("");
        password.getEditText().setText("");
        Intent x=new Intent(this, Home.class);
        x.putExtra("Name", save_name);
        startActivity(x);
        finish();
    }

    private boolean validatePassword() {
        String val = password.getEditText().getText().toString().trim();
        String checkPassword = "^(?=.*[a-zA-Z])(?=\\S+$).{4,}$";
        if (val.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(checkPassword)) {
            password.setError("Password should contain at least 4 characters, with no white spaces!");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUsername() {
        String val = name.getEditText().getText().toString().trim();
        String checkspaces = "\\S+"; // This pattern matches any non-whitespace character
        if (val.isEmpty()) {
            name.setError("Field cannot be empty");
            return false;
        } else if (val.length() > 20) {
            name.setError("Username is too large!");
            return false;
        } else if (!val.matches(checkspaces)) {
            name.setError("No white spaces are allowed!");
            return false;
        } else {
            name.setError(null);
            name.setErrorEnabled(false);
            return true;
        }
    }

    @SuppressLint("Range")
    private boolean checkIfUserNameExists(String username){
        db = hlp.getWritableDatabase();
        Cursor c = db.query(HelperDB.TABLE_NAME, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                if (c.getString(c.getColumnIndex(HelperDB.NAME)).equals(username))
                    return true;
            } while (c.moveToNext());
        }
        return false;
    }

    private void insertUserToSQLite(){
        Info c= new Info(imageBitmaps[0], imageBitmaps[1], imageBitmaps[2], save_name, save_pass);
        // insert to DB
        hlp.insert(c);
        Toast.makeText(this,"USER SAVED",Toast.LENGTH_SHORT).show();
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



    public void goLog(View view) {
        Intent go=new Intent(this, Login.class);
        startActivity(go);
        finish();
    }

}