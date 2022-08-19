package com.example.hivelearners2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // After two seconds 1000ms = 1 Second
                startActivity(new Intent(Splash_Activity.this, SignIn_Activity.class));
                finish();

            }
        }, 2000);
    }
}