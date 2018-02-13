package com.example.akhil.smartcityapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.concurrent.ExecutionException;


/**
 * Created by akhil on 28-01-2018.
 */

public class CustomAdapter extends BaseAdapter {
    Resources res ;
    AsyncTask asyncTask;
    String [] result;
    Context context;
   String [] imageId;
    double[] ratings;
ImageLoader imageLoader;
int loader;
 Bitmap bitmap;
 String loc;
 String[] ltlg;

    private static LayoutInflater inflater=null;
    public CustomAdapter(NearByList nearByList, String[] prgmNameList, String[] prgmImages,double ratings[],String loc,String ltlng[]) {
        // TODO Auto-generated constructor stub
        this.loc=loc;
        result=prgmNameList;
        loader=R.drawable.atm;
        context=nearByList;
        imageId=prgmImages;
        this.ltlg=ltlng;
        this.ratings=ratings;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
imageLoader=ImageLoader.getInstance();
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
        RatingBar ratingBar;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        final View rowView;


        rowView = inflater.inflate(R.layout.nearby_list_template, null);
        holder.tv=(TextView) rowView.findViewById(R.id.textView2);
        holder.img=(ImageView) rowView.findViewById(R.id.imageView2);
       holder.ratingBar=(RatingBar)rowView.findViewById(R.id.ratingBar);
        holder.tv.setText(result[position]);
asyncTask=new ReadImagesFromUrl().execute(imageId[position]);
        try {
            bitmap=(Bitmap) asyncTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        holder.ratingBar.setRating((float) ratings[position]);
       holder.img.setImageBitmap(bitmap);
        rowView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_LONG).show();

                Intent i=new Intent(context,DisplaySourceAndDesDir.class);
                i.putExtra("src",loc);
                i.putExtra("des",ltlg[position]);
                rowView.getContext().startActivity(i );
            }
        });
        return rowView;
    }

}
