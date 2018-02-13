package com.example.akhil.smartcityapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by akhil on 29-01-2018.
 */

public class ReadImagesFromUrl extends AsyncTask<String,String,Bitmap> {
    Bitmap bitmap;

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            URL url=new URL(strings[0]);
            HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();
            InputStream inputStream=httpURLConnection.getInputStream();
          bitmap= BitmapFactory.decodeStream(inputStream);



        } catch (Exception e) {
            e.printStackTrace();
        }


        return bitmap;
    }
}
