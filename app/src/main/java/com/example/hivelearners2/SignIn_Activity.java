package com.example.hivelearners2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;

public class SignIn_Activity extends AppCompatActivity {

    EditText email_et, pass_et;
    MaterialButton signin_btn, forget_pass_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email_et = findViewById(R.id.signin_email_et);
        pass_et = findViewById(R.id.signin_pass_et);
        signin_btn = findViewById(R.id.signin_btn);
        forget_pass_btn = findViewById(R.id.forget_pass_btn);

        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        forget_pass_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}