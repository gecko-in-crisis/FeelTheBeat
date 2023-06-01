package com.example.feelthebeat;

import android.graphics.Bitmap;

public class Info {

    private Bitmap image1, image2, image3;
    private String name, pass;
    private long id;

    // Constructor
    public Info(Bitmap image1, Bitmap image2, Bitmap image3, String name, String pass) {
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.name = name;
        this.pass=pass;

    }

    public Bitmap getImage1() {
        return image1;
    }

    public void setImage1(Bitmap image1) {
        this.image1 = image1;
    }
    public Bitmap getImage2() {
        return image2;
    }
    public void setImage2(Bitmap image2) {
        this.image2 = image2;
    }
    public Bitmap getImage3() {
        return image3;
    }
    public void setImage3(Bitmap image3) {
        this.image3 = image3;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPass() {
        return pass;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Info{" +
                "image1=" + image1 +
                ", image2="+ image2+
                ", image3="+ image3+
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
