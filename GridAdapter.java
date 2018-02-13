package com.example.akhil.smartcityapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by akhil on 23-12-2017.
 */

public class GridAdapter extends BaseAdapter
{

    Context context;
    ArrayList<FavouritePlaces>favouritePlaces;
    View view;
     private LayoutInflater layoutInflater;
    public GridAdapter(Context ctxt, ArrayList<FavouritePlaces> favouritePlaces)
    {
context=ctxt;
this.favouritePlaces=favouritePlaces;

    }

    @Override
    public int getCount() {
        return favouritePlaces.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
if(view==null)
{
    view=layoutInflater.inflate(R.layout.gridimgs,viewGroup,false);

}
ImageView imageView=(ImageView)view.findViewById(R.id.imageView3);

TextView textView=(TextView)view.findViewById(R.id.titlename);
imageView.setImageResource(favouritePlaces.get(i).getImg());
textView.setText(favouritePlaces.get(i).getImgname());
return view;
    }
}
