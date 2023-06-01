package com.example.feelthebeat;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class HelperDB extends SQLiteOpenHelper{
    public static final String DB_NAME = "pic.db";
    public static final String TABLE_NAME = "info_Table";

    public static final String NAME = "Name";
    public static final String PASS="Pass";
    public static final String PICTURE_1 = "Picture1";
    public static final String PICTURE_2 = "Picture2";
    public static final String PICTURE_3 = "Picture3";
    private SQLiteDatabase database;
    long id;

    public HelperDB(Context context) {
        super(context, DB_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String st = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" (";
        st += NAME+" TEXT, "+PASS+ " TEXT, "+PICTURE_1+ " BLOB, "+PICTURE_2+ " BLOB, "+PICTURE_3 +" BLOB);";
        sqLiteDatabase.execSQL(st);

    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public Info insert(Info info)
    {
        database = getWritableDatabase(); // get access to write the database
        ContentValues values = new ContentValues();
        // stored as Binary Large OBject ->  BLOB
        values.put(NAME, info.getName());

        values.put(PASS, info.getPass());

        values.put(PICTURE_1, getBytes(info.getImage1()));

        values.put(PICTURE_2, getBytes(info.getImage2()));

        values.put(PICTURE_3, getBytes(info.getImage3()));

        id= database.insert(TABLE_NAME, null, values);
        info.setId(id);
        return info;
    }

    // get the user back with the id
    // also possible to return only the id
    // convert from bitmap to byte array
    private  byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

}
