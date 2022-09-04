package com.example.hivelearners2;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

public class Profile_Activity extends AppCompatActivity {

    private TextView username_tv, email_tv;
    private ImageView profile_image;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        username_tv = findViewById(R.id.profile_username_tv);
        email_tv = findViewById(R.id.profile_email_tv);
        profile_image = findViewById(R.id.profile_image_iv);

        username_tv.setText(firebaseAuth.getCurrentUser().getDisplayName());
        email_tv.setText(firebaseAuth.getCurrentUser().getEmail());

        try {
            Glide.with(Profile_Activity.this).load(firebaseAuth.getCurrentUser().getPhotoUrl()).into(profile_image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}