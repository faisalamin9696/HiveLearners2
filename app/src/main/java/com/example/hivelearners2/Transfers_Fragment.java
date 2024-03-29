package com.example.hivelearners2;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Transfers_Fragment extends Fragment {


    public Transfers_Fragment() {
        // Required empty public constructor
    }

    private EditText account_num_et, amount_et, doc_id_et;
    private MaterialButton send_btn, update_doc_btn;
    private ProgressDialog progressDialog;

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference sendings_ref;
    private FirebaseAuth firebaseAuth;
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transfers, container, false);

        account_num_et = view.findViewById(R.id.account_num_et);
        amount_et = view.findViewById(R.id.amount_et);
        send_btn = view.findViewById(R.id.send_payment_btn);
        doc_id_et = view.findViewById(R.id.doc_id_et);
        update_doc_btn = view.findViewById(R.id.update_doc_btn);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(requireContext());

        sendings_ref = database.getReference("sendings");

        update_doc_btn.setOnClickListener(v -> {
            HashMap<String, Object> hashMap = new HashMap<String, Object>();

            hashMap.put("account", account_num_et.getText().toString().trim());
            hashMap.put("amount", Float.parseFloat(amount_et.getText().toString().trim()));
            firestore.collection("sendings").document(doc_id_et.getText().toString().trim()).update(hashMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    Toast.makeText(requireContext(), "Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show();
                }

            });


        });

        send_btn.setOnClickListener(v -> {
            // Button Click Listener
            String account = account_num_et.getText().toString().trim();
            String amount = amount_et.getText().toString().trim();
            if (account.isEmpty() || amount.isEmpty()) {
                Toast.makeText(requireContext(), "Some field are empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if (firebaseAuth.getCurrentUser() == null) {
                Toast.makeText(requireContext(), "Login Session Expired", Toast.LENGTH_SHORT).show();
                return;
            }

            String fid = firebaseAuth.getCurrentUser().getUid();

            String doc_id = firestore.collection("sendings").document().getId();

            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("account", account);
            hashMap.put("amount", Float.parseFloat(amount));
            hashMap.put("doc_id", doc_id);


            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please wait...");
            progressDialog.show();

            firestore.collection("sendings").document(doc_id).set(hashMap).addOnCompleteListener(task -> {

                if (task.isSuccessful()) {
                    if (progressDialog.isShowing())
                        progressDialog.cancel();

                    Toast.makeText(requireContext(), "Data Added", Toast.LENGTH_SHORT).show();
                } else {

                    if (progressDialog.isShowing())
                        progressDialog.cancel();
                    Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show();
                }

            });


        });
        return view;
    }
}