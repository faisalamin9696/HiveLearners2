package com.example.hivelearners2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Transition;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView name_tv;
    Button state_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name_tv = findViewById(R.id.name_tv);
        state_btn = findViewById(R.id.state_btn);

        state_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name_tv.getVisibility() == View.INVISIBLE) {
                    name_tv.setVisibility(View.VISIBLE);
                    state_btn.setText("HIDE");
                } else {
                    name_tv.setVisibility(View.INVISIBLE);
                    state_btn.setText("SHOW");
                }

            }
        });
    }
}