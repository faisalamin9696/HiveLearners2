package com.example.hivelearners2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Transition;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    EditText name_et, email_et, pass_et, confirm_pass_et;
    MaterialButton signup_btn, already_act_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name_et = findViewById(R.id.signup_name_et);
        email_et = findViewById(R.id.signup_email_et);
        pass_et = findViewById(R.id.signup_pass_et);
        confirm_pass_et = findViewById(R.id.signup_confirm_pass_et);
        signup_btn = findViewById(R.id.signup_btn);
        already_act_btn = findViewById(R.id.already_act_btn);


        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        already_act_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}