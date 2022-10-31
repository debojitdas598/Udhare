package com.example.udhar_e;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
//this class is the splash screen
public class Splash extends AppCompatActivity {

    ImageView logo;
    TextView logoname;
    Animation top,bottom;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        logo = findViewById(R.id.logo);
        logoname = findViewById(R.id.loadtext);

        //sets animation
        top = AnimationUtils.loadAnimation(this,R.anim.top);
        bottom = AnimationUtils.loadAnimation(this,R.anim.bottom);
        logo.setAnimation(top);           //top logo animation
        logoname.setAnimation(bottom);      //bottom logo animation
        top.setDuration(1500);
        bottom.setDuration(1500);

        //delay in splash screen = 2.2s
        //animation duration == 1.5s
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Splash.this,ViewData.class);
                startActivity(i);
            }
        },2200);
    }
}