package com.example.hivelearners2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Blogs_Activity extends AppCompatActivity implements MyAdapter.onListItemClickListener {

    private ListView posts_lv;
    ArrayList<MyList_POJO> myList_pojos;
    MyAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blogs);

        posts_lv = findViewById(R.id.posts_lv);
        myList_pojos = new ArrayList<>();
        myList_pojos.add(new MyList_POJO("Pakistan", "Lahore"));
        myList_pojos.add(new MyList_POJO("India", "Punjab"));
        myList_pojos.add(new MyList_POJO("United Kingdom", "London"));
        customAdapter = new MyAdapter(this, R.layout.my_list_item, myList_pojos);
        posts_lv.setAdapter(customAdapter);

    }

    @Override
    public void onDeleteClick(int position) {
        myList_pojos.remove(position);
        customAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();

    }
}