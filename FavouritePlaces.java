package com.example.akhil.smartcityapp;

/**
 * Created by akhil on 25-12-2017.
 */

public class FavouritePlaces {

    private int img;

    private String imgname;

    public FavouritePlaces(int img,String imgname)
    {
        this.img=img;
        this.imgname=imgname;
    }


    public String getImgname() {
        return imgname;
    }

    public void setImgname(String imgname) {
        this.imgname = imgname;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
