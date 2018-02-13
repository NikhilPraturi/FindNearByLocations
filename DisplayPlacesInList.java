package com.example.akhil.smartcityapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by akhil on 28-01-2018.
 */

public class DisplayPlacesInList extends AsyncTask<Object,String,List> {

DataParser dataParser=new DataParser();
    DownloadUrl downloadUrl=new DownloadUrl();
String data;
String placeName;
double rating;
    String lat;
    String lng;
String icon;
List ll=new ArrayList();
    List<HashMap<String,String>> nearByPlaceList=null;


    @Override
    protected void onPostExecute(List list) {
        super.onPostExecute(list);
    }

    @Override
    protected List doInBackground(Object... objects) {


        String url="https://maps.googleapis.com/maps/api/place/textsearch/json?query="+objects[0].toString()+"+in+"+objects[1].toString()+"&key=AIzaSyBfy6H8bVp6aQ-0Alai6fy_jZMViN4NxYc";

        data=downloadUrl.readUrl(url);
        Log.d("data",data);
        nearByPlaceList=dataParser.parse(data);
        for (int i=0;i<nearByPlaceList.size();i++)
        {
            HashMap<String,String> googlePlace=nearByPlaceList.get(i);
            placeName=googlePlace.get("placeName");
            rating=Double.parseDouble(googlePlace.get("rating"));
            icon=googlePlace.get("icon");
            lat=googlePlace.get("lat");
            lng=googlePlace.get("lng") ;

            ll.add(placeName);
            ll.add(rating);
            ll.add(icon);
            ll.add(lat+","+lng);


        }


        return ll;
    }
}
