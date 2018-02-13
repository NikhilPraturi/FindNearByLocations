package com.example.akhil.smartcityapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    MaterialSearchView materialSearchView;
    String loc,fav;
GridView gridView;
    GridAdapter gridAdapter;
int imgs[]={R.drawable.restaurant,R.drawable.bank,R.drawable.university,R.drawable.atm};
String imgnames[]={"restaurant","bank","university","atm"};
WebView webView;
DisplayCurrentLocation displayCurrentLocation;
@RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
getSupportActionBar().setTitle("search");
toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
materialSearchView=(MaterialSearchView)findViewById(R.id.search_view);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
try {
    Intent intent = new Intent(MainActivity.this, DisplayCurrentLocation.class);
    intent.putExtra("loct", loc);
   // intent.putExtra("fav",fav);
  //  intent.putExtra("fav",)
    startActivity(intent);

}
catch (Exception e)
{
    Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
}
            }
        });
        webView=(WebView)findViewById(R.id.web);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.loadUrl("file:///android_asset/views/home.html");
        gridView=(GridView)findViewById(R.id.grid);
        gridAdapter=new GridAdapter(this,getFavouritePlaces());
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Intent intent;
                switch (i){
                    case 0:intent=new Intent(MainActivity.this,NearByList.class);
                    fav=imgnames[i];
                    intent.putExtra("loct",loc);
                    intent.putExtra("fav",fav);
                startActivity(intent);
                break;
                    case 1:intent=new Intent(MainActivity.this,NearByList.class);
                        fav=imgnames[i];
                        intent.putExtra("loct",loc);
                        intent.putExtra("fav",fav);
                        startActivity(intent);
                        break;
                        case 2:intent=new Intent(MainActivity.this,NearByList.class);
                        fav=imgnames[i];
                            intent.putExtra("loct",loc);
                            intent.putExtra("fav",fav);
                            startActivity(intent);
                        break;
                    case 3:intent=new Intent(MainActivity.this,NearByList.class);
                        fav=imgnames[i];
                        intent.putExtra("loct",loc);
                        intent.putExtra("fav",fav);
                        startActivity(intent);
                        break;
                        case 4:intent=new Intent(MainActivity.this,NearByList.class);
                        fav=imgnames[i];
                            intent.putExtra("loct",loc);
                            intent.putExtra("fav",fav); startActivity(intent);

                        break;
                    case 5:intent=new Intent(MainActivity.this,NearByList.class);
                        fav=imgnames[i];
                        intent.putExtra("loct",loc);
                        intent.putExtra("fav",fav);      startActivity(intent);
                        break;

                }

            }
        });
searchViewCode();

    }
    public void searchViewCode()
    {
materialSearchView.setSuggestions(getResources().getStringArray(R.array.suggestions));
materialSearchView.setEllipsize(true);
materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
    @Override
    public boolean onQueryTextSubmit(String query) {

     loc=query;
        Toast.makeText(getApplicationContext(),query,Toast.LENGTH_LONG).show();
    return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;

    }
});
    materialSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
        @Override
        public void onSearchViewShown() {

        }

        @Override
        public void onSearchViewClosed() {

        }
    });
    }

   public ArrayList<FavouritePlaces> getFavouritePlaces()
   {
       ArrayList<FavouritePlaces> favouritePlaces=new ArrayList<>();
       for (int i=0;i<imgnames.length;i++)
       {
           favouritePlaces.add(new FavouritePlaces(imgs[i],imgnames[i]));
       }
       return favouritePlaces;
   }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item=menu.findItem(R.id.action_search);
materialSearchView.setMenuItem(item);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId())
{
    case R.id.action_search:
        return true;
        default:
            return super.onOptionsItemSelected(item);

}


    }
    @Override
    public void onBackPressed()
    {
        if (materialSearchView.isSearchOpen())
        {
            materialSearchView.closeSearch();
        }
        else {
            super.onBackPressed();
        }
    }
}
