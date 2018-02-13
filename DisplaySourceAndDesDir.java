package com.example.akhil.smartcityapp;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

public class DisplaySourceAndDesDir extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String src,des;
AsyncTask as;
    double latitude,longitude;
    Geocoder geocoder ;


   PolylineOptions polylineOptions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_source_and_des_dir);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
Intent i=getIntent();
src=i.getStringExtra("src");
des=i.getStringExtra("des");


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        as=new GetDirections().execute(src,des);
        Log.e("async",as.toString());
        try {
            polylineOptions = (PolylineOptions) as.get();
            Log.d("str",polylineOptions.toString());
         Log.d("color",   String.valueOf(polylineOptions.getColor()));

        }
        catch (Exception e)
        {
            Log.d("error",e.getMessage());
        }
        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
/*
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
*/
        MarkerOptions markerOptions = new MarkerOptions();
        List<Address> addressesList = null;

        try {
                Geocoder geocoder = new Geocoder(this);
                addressesList = geocoder.getFromLocationName(src, 5);
            } catch (Exception e) {
                Log.e("error", e.getMessage());
            }
        for (int i = 0; i < addressesList.size(); i++) {
            Address myAddress = addressesList.get(i);
            latitude=myAddress.getLatitude();
            longitude=myAddress.getLongitude();
            LatLng latLng = new LatLng(myAddress.getLatitude(), myAddress.getLongitude());
            markerOptions.position(latLng);
            markerOptions.title("your search result");
            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.zoomTo(20));

            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

        }
mMap.addPolyline(polylineOptions);

    }
}
