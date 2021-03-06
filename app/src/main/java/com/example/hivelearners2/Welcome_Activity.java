package com.example.hivelearners2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class Welcome_Activity extends AppCompatActivity {


    TextView welcome_username_tv;
    SharedPreferences sharedPreferences;
    MaterialButton logout_btn;
    ProgressDialog progressDialog;

    AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        progressDialog = new ProgressDialog(Welcome_Activity.this);
        progressDialog.setMessage("Please Wait...");

        alertDialog = new AlertDialog.Builder(Welcome_Activity.this);



        welcome_username_tv = findViewById(R.id.welcome_username_tv);
        sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
        logout_btn = findViewById(R.id.logout_btn);

        String login_account = sharedPreferences.getString("login_account", "");
        String account_username = sharedPreferences.getString(login_account + "_name", "");
        welcome_username_tv.setText(account_username);

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.setTitle("Confirmation");
                alertDialog.setMessage("Do you really want to logout?");

                alertDialog.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        progressDialog.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (progressDialog.isShowing()) {
                                    progressDialog.cancel();
                                }
                                sharedPreferences.edit().remove("login_account").apply();
                                startActivity(new Intent(Welcome_Activity.this, MainActivity.class));
                                Toast.makeText(Welcome_Activity.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                                finish();

                            }
                        }, 2000);

                    }
                });
                alertDialog.show();


            }
        });
    }
}