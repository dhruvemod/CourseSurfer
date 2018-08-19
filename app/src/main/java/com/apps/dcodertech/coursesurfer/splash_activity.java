package com.apps.dcodertech.coursesurfer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import yanzhikai.textpath.AsyncTextPathView;
import yanzhikai.textpath.painter.AsyncPathPainter;

import static java.lang.Thread.sleep;

public class splash_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activity);
        final String PREFS_NAME = "MyPrefsFile";

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);


        ImageView imageView=findViewById(R.id.imageView2);
        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fui_slide_in_right);
        Animation animationTwo= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.in_from_left);
        TextView moto=findViewById(R.id.textView3);
        //imageView.startAnimation(animation);
        TextView courseSurfer = findViewById(R.id.textView2);
        courseSurfer.startAnimation(animation);
        moto.startAnimation(animationTwo);

        if (settings.getBoolean("my_first_time", true)) {

            //the app is being launched for first time, do something
           Intent intent=new Intent(getApplicationContext(),YourActivity.class);
            startActivity(intent);
            finish();
            // first time task

            // record the fact that the app has been started at least once
            settings.edit().putBoolean("my_first_time", false).commit();

        }
        else{
        Thread thread=new Thread(){
            @Override
            public void run(){
                try {
                    try{sleep(1000);}catch (Exception e){}
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                    super.run();
                }catch (Exception e){

                }
            }
        };
        thread.start();
    }}
}
