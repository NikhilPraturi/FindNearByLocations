package com.example.akhil.smartcityapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Splash extends AppCompatActivity {
ImageView imageView;
TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        try {
            imageView = (ImageView) findViewById(R.id.imageView);
            textView = (TextView) findViewById(R.id.textView);
            Animation animation = AnimationUtils.loadAnimation(Splash.this, R.anim.fade);
           final Animation animation1 = AnimationUtils.loadAnimation(Splash.this, R.anim.txtfade);
            imageView.startAnimation(animation);
            Thread.sleep(3000);

            textView.startAnimation(animation1);

            Thread t = new Thread() {
                @Override
                public void run() {
                    try {


                        Intent i = new Intent(Splash.this, OTPRegistration.class);
                        Thread.sleep(7000);

                        startActivity(i);
                        finish();
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(Splash.this,e.getMessage(),Toast.LENGTH_LONG).show();

                    }
                    }
            };
            t.start();


        }
        catch (Exception e)
        {
            Toast.makeText(Splash.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

}
