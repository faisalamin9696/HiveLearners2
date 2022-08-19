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

import java.util.ArrayList;
import java.util.HashMap;


public class Blogs_Fragment extends Fragment {

    public Blogs_Fragment() {
        // Required empty public constructor
    }

    private ListView posts_lv;
    ArrayList<MyList_POJO> myList_pojos;
    MyAdapter customAdapter;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference sendings_ref;

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

        sendings_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myList_pojos.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String username = ds.child("account").getValue(String.class);
                    Float amount = ds.child("amount").getValue(Float.class);
                    String pushKey = ds.child("pushKey").getValue(String.class);

                    myList_pojos.add(new MyList_POJO(username, String.valueOf(amount), pushKey));
                }
                customAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }


}