package com.example.hivelearners2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class Splash_Activity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // After two seconds 1000ms = 1 Second
                if (!sharedPreferences.getBoolean("is_intro_done", false))
                    startActivity(new Intent(Splash_Activity.this, App_Intro.class));
                else startActivity(new Intent(Splash_Activity.this, SignIn_Activity.class));

                finish();

            }
        }, 2000);
    }
}