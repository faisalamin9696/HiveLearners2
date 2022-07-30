package com.example.hivelearners2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn_Activity extends AppCompatActivity {

    private EditText email_et, pass_et;
    private MaterialButton signin_btn, forget_pass_btn;
    private SharedPreferences sharedPreferences;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private AlertDialog alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        firebaseAuth = FirebaseAuth.getInstance();
        email_et = findViewById(R.id.signin_email_et);
        pass_et = findViewById(R.id.signin_pass_et);
        signin_btn = findViewById(R.id.signin_btn);
        forget_pass_btn = findViewById(R.id.forget_pass_btn);
        sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);


        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_et.getText().toString().toLowerCase().trim();
                String password = pass_et.getText().toString();
                if (TextUtils.isEmpty(email_et.getText().toString())) {
                    email_et.setError("Invalid Email");
                    return;
                }
                if (TextUtils.isEmpty(pass_et.getText().toString())) {
                    pass_et.setError("Invalid Password");
                    return;
                }

                progressDialog.setMessage("Please wait...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (progressDialog.isShowing())
                            progressDialog.cancel();

                        alertDialog.setTitle("Successful");
                        alertDialog.setMessage("You are successfully sign in");
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", (dialog, which) -> {
                            dialog.cancel();
                            startActivity(new Intent(SignIn_Activity.this, Welcome_Activity.class));
                            finish();

                        });
                        alertDialog.show();
                    } else {
                        if (progressDialog.isShowing())
                            progressDialog.cancel();

                        alertDialog.setTitle("Failed");
                        alertDialog.setMessage(task.getException().getMessage());
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", (dialog, which) -> {
                            dialog.cancel();

                        });
                        alertDialog.show();
                    }


                });
            }
        });

        forget_pass_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}