package com.example.hivelearners2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Blogs_Activity extends AppCompatActivity {

    private ListView posts_lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blogs);

        posts_lv = findViewById(R.id.posts_lv);

        ArrayList<MyList_POJO> myList_pojos = new ArrayList<>();
        myList_pojos.add(new MyList_POJO("Pakistan", "Lahore"));
        myList_pojos.add(new MyList_POJO("India", "Punjab"));
        myList_pojos.add(new MyList_POJO("United Kingdom", "London"));
        MyAdapter customAdapter = new MyAdapter(this, R.layout.my_list_item, myList_pojos);

        posts_lv.setAdapter(customAdapter);


    }
}