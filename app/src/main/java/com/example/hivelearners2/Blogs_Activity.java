package com.example.hivelearners2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Blogs_Activity extends AppCompatActivity {

    private ListView posts_lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blogs);

        posts_lv = findViewById(R.id.posts_lv);

        String[] posts_array = {"My 1st Post", "My 2nd Post", "My 3rd Post", "My 4th Post"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, posts_array);
        posts_lv.setAdapter(adapter);


        // Lec 37


    }
}