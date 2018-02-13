package com.example.akhil.smartcityapp;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;

/**
 * Created by akhil on 31-12-2017.
 */

public class GetNearByPlacesData extends AsyncTask<Object,String,String> {

GoogleMap mMap;
    String googlePlacesData;
    String url;

    @Override
    protected String doInBackground(Object... objects) {

     mMap=(GoogleMap)objects[0];
     url=(String)objects[1];
        Log.d("getNearBy",url);
DownloadUrl downloadUrl=new DownloadUrl();
googlePlacesData=downloadUrl.readUrl(url);
Log.d("googlePlacesData",googlePlacesData);
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s)
    {
        super.onPostExecute(s);
    List<HashMap<String,String>> nearByPlaceList=null;
    DataParser dataParser=new DataParser();
    nearByPlaceList=dataParser.parse(s);
    showNearByPlaces(nearByPlaceList);
    }
    private void showNearByPlaces(List<HashMap<String,String>> nearbyPlacesList)
    {
        for (int i=0;i<nearbyPlacesList.size();i++)
        {
            MarkerOptions markerOptions=new MarkerOptions();
            HashMap<String,String> googlePlace=nearbyPlacesList.get(i);
            String placeName=googlePlace.get("placeName");
            String vicinity=googlePlace.get("vicinity");
            double lat=Double.parseDouble(googlePlace.get("lat"));
            Log.d("lat",String.valueOf(lat));
            double lng=Double.parseDouble(googlePlace.get("lng"));
            Log.d("lng",String.valueOf(lng));
            LatLng latLng=new LatLng(lat,lng);
            markerOptions.position(latLng);
            markerOptions.title(placeName+": "+vicinity);
            Log.d("placename",placeName);
            Log.d("vicinity",vicinity);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));

        }
    }
}
