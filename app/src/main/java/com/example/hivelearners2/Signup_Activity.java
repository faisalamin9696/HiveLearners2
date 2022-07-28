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
import com.google.firebase.auth.FirebaseAuth;

public class Signup_Activity extends AppCompatActivity {

    private EditText name_et, email_et, pass_et, confirm_pass_et;
    private MaterialButton signup_btn, already_act_btn;
    private SharedPreferences sharedPreferences;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name_et = findViewById(R.id.signup_name_et);
        email_et = findViewById(R.id.signup_email_et);
        pass_et = findViewById(R.id.signup_pass_et);
        confirm_pass_et = findViewById(R.id.signup_confirm_pass_et);
        signup_btn = findViewById(R.id.signup_btn);
        already_act_btn = findViewById(R.id.already_act_btn);
        sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);

        firebaseAuth = FirebaseAuth.getInstance();

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
                    Toast.makeText(Signup_Activity.this, "Password not matching", Toast.LENGTH_SHORT).show();
                    return;
                }


            }
        });

        already_act_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Signup_Activity.this, SignIn_Activity.class));

            }
        });
    }


}
