package com.example.hivelearners2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class Transfer_Funds_Activity extends AppCompatActivity {

    private EditText account_num_et, amount_et;
    private MaterialButton send_btn;
    private ProgressDialog progressDialog;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference sendings_ref;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_funds);

        account_num_et = findViewById(R.id.account_num_et);
        amount_et = findViewById(R.id.amount_et);
        send_btn = findViewById(R.id.send_payment_btn);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(Transfer_Funds_Activity.this);

        sendings_ref = database.getReference("sendings");

        send_btn.setOnClickListener(v -> {
            // Button Click Listener
            String account = account_num_et.getText().toString().trim();
            String amount = amount_et.getText().toString().trim();
            if (account.isEmpty() || amount.isEmpty()) {
                Toast.makeText(this, "Some field are empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if (firebaseAuth.getCurrentUser() != null) {
                Toast.makeText(this, "Login Session Expired", Toast.LENGTH_SHORT).show();
                return;
            }

            String fid = firebaseAuth.getCurrentUser().getUid();

            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("account", account);
            hashMap.put("amount", Float.parseFloat(amount));

            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please wait...");
            progressDialog.show();

            sendings_ref.child(fid).setValue(hashMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    if (progressDialog.isShowing())
                        progressDialog.cancel();

                    Toast.makeText(this, "Data Added", Toast.LENGTH_SHORT).show();
                } else {

                    if (progressDialog.isShowing())
                        progressDialog.cancel();
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
                }
                //41 lecture end
            });

            hashMap.put("account", "My Account");
            hashMap.put("amount", 1.21);

            sendings_ref.child(fid).updateChildren(hashMap).addOnCompleteListener(task -> {

            });


        });
    }
}