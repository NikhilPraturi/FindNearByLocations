package com.example.akhil.smartcityapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class NearByList extends AppCompatActivity {
String loc,fav;
    ListView lv;
String ltlng[];
    String placearr[];
    double ratingarr[];
    String iconarr[];
    String placearr2[];
    double ratingarr2[];
    String iconarr2[];
   String ltlng2[];
    Context context;
    DisplayPlacesInList displayPlacesInList=new DisplayPlacesInList();
    ArrayList prgmName;
DisplayCurrentLocation displayCurrentLocation=new DisplayCurrentLocation();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_by_list);
        context=this;

        lv=(ListView) findViewById(R.id.listView);
       // lv.setAdapter(new CustomAdapter(this, prgmNameList,prgmImages));

        final Intent intent=getIntent();
        loc=intent.getStringExtra("loct");
fav=intent.getStringExtra("fav");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
Intent intent1=new Intent(NearByList.this,DisplayCurrentLocation.class);
intent1.putExtra("loct",loc);
intent1.putExtra("fav",fav);
startActivity(intent1);
            }
        });

        lv=(ListView) findViewById(R.id.listView);
     //   lv.setAdapter(new CustomAdapter(this, prgmNameList,prgmImages));

        AsyncTask as=new DisplayPlacesInList().execute(loc,fav);
        List ll= null;
        try {
            ll = (List)  as.get();
            placearr = new String[ll.size()];
            ratingarr = new double[ll.size()];
            iconarr = new String[ll.size()];
ltlng=new String[ll.size()];
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

Iterator i=ll.iterator();
int count=0;
try {
    while (i.hasNext()) {
        placearr[count] = (String) i.next();
        ratingarr[count] = (double) i.next();
        iconarr[count] = (String) i.next();
        ltlng[count]=(String)i.next();
        count++;
    }
    placearr2 = new String[count - 1];
    ratingarr2 = new double[count - 1];
    iconarr2 = new String[count - 1];
    ltlng2=new String[count-1];
    for (int k = 0; k < count - 1; k++) {
        placearr2[k] = placearr[k];
        ratingarr2[k] = ratingarr[k];
        iconarr2[k] = iconarr[k];
ltlng2[k]=ltlng[k];

    }
}
catch (Exception e)
{
    Log.e("error",e.getMessage());

}
try {
    lv.setAdapter(new CustomAdapter(this, placearr2, iconarr2, ratingarr2,loc,ltlng2));


}
catch (Exception e)
{
    Log.e("error",e.getMessage());
}
}

}
