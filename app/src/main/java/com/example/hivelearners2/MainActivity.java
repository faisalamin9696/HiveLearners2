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

public class MainActivity extends AppCompatActivity {

    EditText name_et, email_et, pass_et, confirm_pass_et;
    MaterialButton signup_btn, already_act_btn;
    private SharedPreferences sharedPreferences;

    //ok

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
        sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);

        if (!sharedPreferences.getString("login_account", "").equals("")) {
            startActivity(new Intent(MainActivity.this, Welcome_Activity.class));
            finish();
        }


        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(name_et.getText())) {
                    name_et.setError("Invalid Name");
                    return;
                }
                if (TextUtils.isEmpty(email_et.getText())) {
                    email_et.setError("Invalid Email");
                    return;
                }
                if (TextUtils.isEmpty(pass_et.getText())) {
                    pass_et.setError("Invalid Password");
                    return;
                }
                if (!(pass_et.getText().toString().equals(confirm_pass_et.getText().toString()))) {
                    Toast.makeText(MainActivity.this, "Password not matching", Toast.LENGTH_SHORT).show();
                    return;
                }
                create_user(name_et.getText().toString(), email_et.getText().toString().toLowerCase(), pass_et.getText().toString());


            }
        });

        already_act_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignIn_Activity.class));

            }
        });
    }

    public void create_user(String name, String email, String password) {
        sharedPreferences.edit().putString(email, password).apply();
        sharedPreferences.edit().putString(email + "_name", name).apply();
        Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MainActivity.this, SignIn_Activity.class));
        finish();
    }
}