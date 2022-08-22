package com.example.hivelearners2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.shashank.sony.fancywalkthroughlib.FancyWalkthroughActivity;
import com.shashank.sony.fancywalkthroughlib.FancyWalkthroughCard;

import java.util.ArrayList;
import java.util.List;

public class App_Intro extends FancyWalkthroughActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);

        FancyWalkthroughCard page1 = new FancyWalkthroughCard("Hive Learners", "Best Community", R.drawable.hive_logo);
        FancyWalkthroughCard page2 = new FancyWalkthroughCard("Learn and Earn", "with Lazy-Panda", R.drawable.lazy_panda_logo);

        // First Page
        page1.setBackgroundColor(R.color.red);
        page1.setIconLayoutParams(500, 500, 0, 0, 0, 0);
        page1.setDisplaySkip(true);
        page1.setTitleColor(R.color.black);
        page1.setDescriptionColor(R.color.black);

        // Second Page
        page2.setBackgroundColor(R.color.black);
        page2.setIconLayoutParams(500, 500, 0, 0, 0, 0);
        page2.setDisplaySkip(true);
        page1.setTitleColor(R.color.black);
        page1.setDescriptionColor(R.color.black);

        // List of Pages
        List<FancyWalkthroughCard> pages = new ArrayList<>();
        pages.add(page1);
        pages.add(page2);

        setColorBackground(R.color.white);
        setInactiveIndicatorColor(R.color.grey_600);
        showNavigationControls(true);
        setActiveIndicatorColor(R.color.white);
        setFinishButtonTitle("Get Started");

        setOnboardPages(pages);

    }

    @Override
    public void onFinishButtonPressed() {
        sharedPreferences.edit().putBoolean("is_intro_done", true).apply();
        startActivity(new Intent(App_Intro.this, SignIn_Activity.class));
        Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();
        finish();

    }
}
