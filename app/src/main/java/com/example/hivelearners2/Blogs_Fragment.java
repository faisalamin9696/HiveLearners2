package com.example.hivelearners2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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


public class Blogs_Fragment extends Fragment implements MyAdapter.onListItemClickListener {

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
        myList_pojos.add(new MyList_POJO("Pakistan", "Lahore"));
        myList_pojos.add(new MyList_POJO("India", "Punjab"));
        myList_pojos.add(new MyList_POJO("United Kingdom", "London"));
        customAdapter = new MyAdapter(requireContext(), R.layout.my_list_item, myList_pojos);
        posts_lv.setAdapter(customAdapter);

        sendings_ref = database.getReference("sendings");

        sendings_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(requireContext(), snapshot.getValue().toString(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }

    @Override
    public void onDeleteClick(int position) {
        myList_pojos.remove(position);
        customAdapter.notifyDataSetChanged();
        Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show();

    }
}