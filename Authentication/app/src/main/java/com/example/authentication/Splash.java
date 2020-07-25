package com.example.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.github.ybq.android.spinkit.style.FoldingCube;

public class Splash extends AppCompatActivity {
    private ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

       mProgressBar = findViewById(R.id.progressbar_splash_screen);
        Sprite fading = new FadingCircle();
        mProgressBar.setIndeterminateDrawable(fading);
        mProgressBar.setVisibility(View.VISIBLE);

        int secondsDelayed = 1;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(getApplicationContext(),Login.class));

                finish();

            }
        },secondsDelayed * 2000);
    }


}
