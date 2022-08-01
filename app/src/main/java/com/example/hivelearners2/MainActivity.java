package com.example.hivelearners2;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    private MaterialButton signin_btn, signup_btn;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onStart() {
        super.onStart();

        if (firebaseAuth.getCurrentUser() != null) {
            if (firebaseAuth.getCurrentUser().isEmailVerified()) {
                startActivity(new Intent(MainActivity.this, Welcome_Activity.class));
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signin_btn = findViewById(R.id.main_signin_btn);
        signup_btn = findViewById(R.id.main_signup_btn);

        firebaseAuth = FirebaseAuth.getInstance();

        signin_btn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SignIn_Activity.class));
            finish();

        });

        signup_btn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, Signup_Activity.class));
            finish();
        });

        // Main
    }
}