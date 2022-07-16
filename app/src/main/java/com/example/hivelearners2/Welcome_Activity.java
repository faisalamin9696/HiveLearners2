package com.example.hivelearners2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class Welcome_Activity extends AppCompatActivity {


    TextView welcome_username_tv;
    SharedPreferences sharedPreferences;
    MaterialButton logout_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        welcome_username_tv = findViewById(R.id.welcome_username_tv);
        sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
        logout_btn = findViewById(R.id.logout_btn);

        String login_account = sharedPreferences.getString("login_account", "");
        String account_username = sharedPreferences.getString(login_account + "_name", "");
        welcome_username_tv.setText(account_username);

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().remove("login_account").apply();
                startActivity(new Intent(Welcome_Activity.this, MainActivity.class));
                Toast.makeText(Welcome_Activity.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                finish();


            }
        });

    }
}