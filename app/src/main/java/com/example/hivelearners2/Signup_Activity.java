package com.example.hivelearners2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Signup_Activity extends AppCompatActivity {

    private EditText name_et, email_et, pass_et, confirm_pass_et;
    private MaterialButton signup_btn, already_act_btn;
    private SharedPreferences sharedPreferences;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private AlertDialog alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();
        name_et = findViewById(R.id.signup_name_et);
        email_et = findViewById(R.id.signup_email_et);
        pass_et = findViewById(R.id.signup_pass_et);
        confirm_pass_et = findViewById(R.id.signup_confirm_pass_et);
        signup_btn = findViewById(R.id.signup_btn);
        already_act_btn = findViewById(R.id.already_act_btn);
        sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
        progressDialog = new ProgressDialog(Signup_Activity.this);
        alertDialog = new AlertDialog.Builder(Signup_Activity.this).create();


        signup_btn.setOnClickListener(view -> {
            String email = email_et.getText().toString().toLowerCase().trim();
            String password = pass_et.getText().toString();
            String username = name_et.getText().toString();

            if (TextUtils.isEmpty(username)) {
                name_et.setError("Invalid Name");
                return;
            }
            if (TextUtils.isEmpty(email)) {
                email_et.setError("Invalid Email");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                pass_et.setError("Invalid Password");
                return;
            }
            if (!(password.equals(confirm_pass_et.getText().toString()))) {
                Toast.makeText(Signup_Activity.this, "Password not matching", Toast.LENGTH_SHORT).show();
                return;
            }

            progressDialog.setMessage("Please wait...");
            progressDialog.show();
            progressDialog.setCancelable(false);

            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(username)
                            .build();
                    assert user != null;
                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    firebaseAuth.signOut();
                                    if (progressDialog.isShowing())
                                        progressDialog.cancel();
                                    alertDialog.setTitle("Done");
                                    alertDialog.setMessage("Your account is created successfully");
                                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", (dialog, which) -> {
                                        dialog.cancel();

                                    });
                                    alertDialog.setCancelable(false);
                                } else {

                                    // Delete the account if username not save successfully to let the user retry
                                    assert firebaseAuth.getCurrentUser() != null;
                                    firebaseAuth.getCurrentUser().delete();
                                    alertDialog.setTitle("Failed");
                                    alertDialog.setMessage(task1.getException().getMessage());
                                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", (dialog, which) -> {
                                        dialog.cancel();

                                    });
                                }
                                alertDialog.show();
                            });


                    //Sig up Successful
                } else {

                    if (progressDialog.isShowing())
                        progressDialog.cancel();

                    alertDialog.setTitle("Failed");
                    alertDialog.setMessage(task.getException().getMessage());
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", (dialog, which) -> {
                        dialog.cancel();

                    });
                    alertDialog.show();
                    // Sign up failed
                }

            });

        });

        already_act_btn.setOnClickListener(view -> startActivity(new Intent(Signup_Activity.this, SignIn_Activity.class)));
    }


}
