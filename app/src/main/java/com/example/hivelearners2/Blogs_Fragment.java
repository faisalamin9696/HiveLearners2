package com.example.hivelearners2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;


public class Blogs_Fragment extends Fragment {

    public Blogs_Fragment() {
        // Required empty public constructor
    }

    private ListView posts_lv;
    ArrayList<MyList_POJO> myList_pojos;
    MyAdapter customAdapter;

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference sendings_ref;

    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blogs, container, false);

        posts_lv = view.findViewById(R.id.posts_lv);
        myList_pojos = new ArrayList<>();
        customAdapter = new MyAdapter(requireContext(), R.layout.my_list_item, myList_pojos);
        posts_lv.setAdapter(customAdapter);

        sendings_ref = database.getReference("sendings");

        firestore.collection("sendings").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                myList_pojos.clear();
                for (DocumentSnapshot ds : task.getResult()) {
                    String username = ds.get("account", String.class);
                    Float amount = ds.get("amount", Float.class);
                    String doc_id = ds.get("doc_id", String.class);

                    myList_pojos.add(new MyList_POJO(username, String.valueOf(amount), doc_id));

                }
                customAdapter.notifyDataSetChanged();

            } else Toast.makeText(requireContext(), "Fetching failed", Toast.LENGTH_SHORT).show();


        });

        return view;
    }


}