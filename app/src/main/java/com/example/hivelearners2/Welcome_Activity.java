package com.example.hivelearners2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class Welcome_Activity extends AppCompatActivity {


    TextView welcome_username_tv;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        welcome_username_tv = findViewById(R.id.welcome_username_tv);
        sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);

        String login_account = sharedPreferences.getString("login_account", "");
        String account_username = sharedPreferences.getString(login_account + "_name", "");
        welcome_username_tv.setText(account_username);

    }
}