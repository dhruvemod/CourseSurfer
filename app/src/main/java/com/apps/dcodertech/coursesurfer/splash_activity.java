package com.apps.dcodertech.coursesurfer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import yanzhikai.textpath.AsyncTextPathView;
import yanzhikai.textpath.painter.AsyncPathPainter;

public class splash_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activity);
        ImageView imageView=findViewById(R.id.imageView2);
        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fui_slide_in_right);
        Animation animationTwo= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.in_from_left);
        TextView moto=findViewById(R.id.textView3);
        //imageView.startAnimation(animation);
        TextView courseSurfer = findViewById(R.id.textView2);
        courseSurfer.startAnimation(animation);
        moto.startAnimation(animationTwo);
        Thread thread=new Thread(){
            @Override
            public void run(){
                try {
                    sleep(1000);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                    super.run();
                }catch (Exception e){

                }
            }
        };
        thread.start();
    }
}
