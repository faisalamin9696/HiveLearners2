package com.example.hivelearners2;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Welcome_Activity extends AppCompatActivity {


    private TextView welcome_username_tv;
    private SharedPreferences sharedPreferences;
    private MaterialButton logout_btn;
    private ProgressDialog progressDialog;
    private AlertDialog.Builder alertDialog;
    private FirebaseAuth firebaseAuth;

    private TabLayout welcome_tabs;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        progressDialog = new ProgressDialog(Welcome_Activity.this);
        progressDialog.setMessage("Please Wait...");
        alertDialog = new AlertDialog.Builder(Welcome_Activity.this);
        firebaseAuth = FirebaseAuth.getInstance();
        welcome_tabs = findViewById(R.id.welcome_tabs);
        viewPager = findViewById(R.id.viewpager);




        welcome_username_tv = findViewById(R.id.welcome_username_tv);
        sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
        logout_btn = findViewById(R.id.logout_btn);

        SectionsPagerAdapter_welcome mSectionsPagerAdapter = new SectionsPagerAdapter_welcome(getSupportFragmentManager());
        viewPager.setAdapter(mSectionsPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(welcome_tabs));
        welcome_tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

        assert firebaseAuth.getCurrentUser() != null;
        welcome_username_tv.setText(firebaseAuth.getCurrentUser().getDisplayName());

        logout_btn.setOnClickListener(v -> {

            Dialog custom_dialog = new Dialog(Welcome_Activity.this, R.style.Custom_Dialog);
            custom_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            custom_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            custom_dialog.setContentView(R.layout.custom_dialog);

            MaterialButton no_btn = custom_dialog.findViewById(R.id.dialog_no_btn);
            MaterialButton yes_btn = custom_dialog.findViewById(R.id.dialog_yes_btn);

            no_btn.setOnClickListener(v12 -> custom_dialog.cancel());

            yes_btn.setOnClickListener(v1 -> {
                firebaseAuth.signOut();
                startActivity(new Intent(Welcome_Activity.this, MainActivity.class));
                Toast.makeText(Welcome_Activity.this, "Logout Successful", Toast.LENGTH_SHORT).show();
                finish();
            });
            custom_dialog.show();

        });
    }

    public class SectionsPagerAdapter_welcome extends FragmentPagerAdapter {
        @SuppressWarnings("deprecation")
        SectionsPagerAdapter_welcome(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {

            Fragment fragment = new Fragment();
            try {
                switch (position) {
                    case 0:
                        fragment = new Blogs_Fragment();
                        break;
                    case 1:
                        fragment = new Transfers_Fragment();
                        break;

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return fragment;


        }

        @Override
        public int getCount() {
            // Show 8 total pages.
            return welcome_tabs.getTabCount();
        }
    }

}