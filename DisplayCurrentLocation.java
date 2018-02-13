package com.example.akhil.smartcityapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class DisplayCurrentLocation extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private Marker currentLocationMarker;
    public static final int PERMISSION_REQUEST_LOCATION_CODE=99;
String loc,fav;
int PROXIMITY_RADIUS=10000;
double latitude,longitude;
    Geocoder geocoder ;

    public DisplayCurrentLocation getInstance()
{
return DisplayCurrentLocation.this;
}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_current_location);
       geocoder= new Geocoder(this);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            checkLocationPemission();
        }
 // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            checkLocationPemission();
        }
try {
    Intent i = getIntent();
    loc = i.getStringExtra("loct");
fav=i.getStringExtra("fav");
        }
catch (Exception e)
{
    Log.e("location:",e.getMessage());
}
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

public boolean checkLocationPemission() {
    if (ContextCompat.checkSelfPermission(this,  Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_LOCATION_CODE);
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_LOCATION_CODE);

        }
return false;
    }
    else {
        return true;
    }
}
@Override
public void onRequestPermissionsResult(int requestCode,@NonNull String[] permissions, @NonNull int[] grantResults)
{
switch (requestCode){
    case PERMISSION_REQUEST_LOCATION_CODE:
        if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                if (client==null)
                {
                    buildGoogleApiClient();
                }
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            Toast.makeText(this,"permission denied",Toast.LENGTH_LONG).show();
        }
        return;
}
}
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
        {

          //  buildGoogleApiClient();

            mMap.setMyLocationEnabled(true);
 }


        if(loc!=null) {
            displayLocationOnMap();

        }
}
        public void displayLocationOnMap() {
            Object datTransfer[] = new Object[2];
            GetNearByPlacesData getNearByPlacesData = new GetNearByPlacesData();
            String url;


            List<Address> addressesList = null;
            MarkerOptions markerOptions = new MarkerOptions();
            if (!loc.equals("")) {
                try {
                    Geocoder geocoder = new Geocoder(this);
                    addressesList = geocoder.getFromLocationName(loc, 5);
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
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                }
            }

            if (fav != null) {
                switch (fav) {

                    case "atm":
                        mMap.clear();
                        String atm = "atm";
                        url = getUrl(latitude, longitude, atm);
                        datTransfer[0] = mMap;
                        datTransfer[1] = url;
                        getNearByPlacesData.execute(datTransfer);
                        Toast.makeText(DisplayCurrentLocation.this, "showing nearby atm's", Toast.LENGTH_LONG).show();
                        break;

                    case "bank":
                        mMap.clear();
                        String bank = "bank";
                        url = getUrl(latitude, longitude, bank);
                        datTransfer[0] = mMap;
                        datTransfer[1] = url;
                        getNearByPlacesData.execute(datTransfer);
                        Toast.makeText(DisplayCurrentLocation.this, "showing nearby bank's", Toast.LENGTH_LONG).show();
                        break;

                    case "restaurant":
                        mMap.clear();
                        String restaurant = "restaurant";
                        url = getUrl(latitude, longitude, restaurant);
                        datTransfer[0] = mMap;
                        datTransfer[1] = url;
                        getNearByPlacesData.execute(datTransfer);
                        Toast.makeText(DisplayCurrentLocation.this, "showing nearby restaurant's", Toast.LENGTH_LONG).show();
                        break;
                    case "university":
                        mMap.clear();
                        String university = "university";
                        url = getUrl(latitude, longitude, university);
                        datTransfer[0] = mMap;
                        datTransfer[1] = url;
                        getNearByPlacesData.execute(datTransfer);
                        Toast.makeText(DisplayCurrentLocation.this, "showing nearby universities", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Log.d("msg", "not selected favourite places");
                }
            }
        }
        private String getUrl(double latitude,double longitude,String nearbyPlace)
        {
StringBuilder googlelacebuilder=new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
googlelacebuilder.append("location="+latitude+","+longitude);
googlelacebuilder.append("&radius="+PROXIMITY_RADIUS);
googlelacebuilder.append("&type="+nearbyPlace);
googlelacebuilder.append("&sensor=true");
googlelacebuilder.append("&key="+"AIzaSyBfy6H8bVp6aQ-0Alai6fy_jZMViN4NxYc");
return googlelacebuilder.toString();
        }


       protected synchronized  void buildGoogleApiClient()
       {
           client=new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
           client.connect();
       }

    @Override
    public void onLocationChanged(Location location) {
lastLocation=location;
if(currentLocationMarker!=null)
{
    currentLocationMarker.remove();
}
LatLng latLng=new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude());
MarkerOptions markerOptions=new MarkerOptions();
markerOptions.position(latLng);
markerOptions.title("current location");
markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
currentLocationMarker=mMap.addMarker(markerOptions);
mMap.moveCamera(CameraUpdateFactory.zoomBy(50));
if(client!=null)
{
    LocationServices.FusedLocationApi.removeLocationUpdates(client,this);
}
if(loc!=null) {
    displayLocationOnMap();
}
}

    @Override
    public void onConnected(@Nullable Bundle bundle) {
locationRequest=new LocationRequest();
locationRequest.setInterval(1000);
locationRequest.setFastestInterval(1000);
locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
    LocationServices.FusedLocationApi.requestLocationUpdates(client,locationRequest,this);

}
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
