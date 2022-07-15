package com.example.hivelearners2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class SignIn_Activity extends AppCompatActivity {

    EditText email_et, pass_et;
    MaterialButton signin_btn, forget_pass_btn;

    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email_et = findViewById(R.id.signin_email_et);
        pass_et = findViewById(R.id.signin_pass_et);
        signin_btn = findViewById(R.id.signin_btn);
        forget_pass_btn = findViewById(R.id.forget_pass_btn);
        sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);


        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(email_et.getText().toString())) {
                    email_et.setError("Invalid Email");
                    return;
                }
                if (TextUtils.isEmpty(pass_et.getText().toString())) {
                    pass_et.setError("Invalid Password");
                    return;
                }

                if (!sharedPreferences.contains(email_et.getText().toString().toLowerCase())) {
                    Toast.makeText(SignIn_Activity.this, "Email not exist", Toast.LENGTH_SHORT).show();
                } else {
                    String get_pass = sharedPreferences.getString(email_et.getText().toString().toLowerCase(), "");
                    if (pass_et.getText().toString().equals(get_pass)) {

                        sharedPreferences.edit().putString("login_account", email_et.getText().toString()).apply();
                        Toast.makeText(SignIn_Activity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignIn_Activity.this, Welcome_Activity.class));
                        finish();


                    } else {
                        Toast.makeText(SignIn_Activity.this, "Login failed: Wrong password or email", Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });

        forget_pass_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}