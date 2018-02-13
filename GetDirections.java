package com.example.akhil.smartcityapp;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

/**
 * Created by akhil on 12-02-2018.
 */

public class GetDirections extends AsyncTask<String, String, PolylineOptions> {
DownloadUrl downloadUrl=new DownloadUrl();
String data;
  String url;
  String dist_api_key;
    @Override
    protected PolylineOptions doInBackground(String... strings) {
        dist_api_key="AIzaSyAab9xRJgI7bkVgxmboBq9HIEcc1Qy6eOI";
    url="https://maps.googleapis.com/maps/api/directions/json?origin="+strings[0]+"&destination="+strings[1]+"&key="+dist_api_key;


     data=downloadUrl.readUrl(url);
        Log.d("data",data);
        String directionlist[];
        DataParser parser=new DataParser();
        directionlist=parser.parseDirections(data);

PolylineOptions p=        displayDirection(directionlist);
Log.d("poly",p.toString());
        return p;
    }

    @Override
    protected void onPostExecute(PolylineOptions o) {
        super.onPostExecute(o);
    }

    public PolylineOptions displayDirection(String[] dir)
    {
        PolylineOptions options=new PolylineOptions();

        int count=dir.length;
        for (int i=0;i<count;i++)
        {
            options.color(Color.RED);

            options.width(20);

            options.addAll(PolyUtil.decode(dir[i]));

        }
        Log.d("options",options.toString());
        return options;

    }

}
