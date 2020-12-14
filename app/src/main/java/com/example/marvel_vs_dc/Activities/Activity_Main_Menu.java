package com.example.marvel_vs_dc.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.marvel_vs_dc.Others.Constants;
import com.example.marvel_vs_dc.R;
import com.google.android.gms.location.FusedLocationProviderClient;

public class Activity_Main_Menu extends Activity_Base {

    int LocationReqCode = 10001;
    FusedLocationProviderClient loc;

    private Button settings;
    private Button top10;
    private Button auto;
    private Button choose;

    private ImageView menu_IMG_bg;
    private ImageView menu_IMG_title;
    private ImageView menu_IMG_logo;
    @Override
    protected void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            askForPermission();
        }
    }

    private void askForPermission() {
        // asking for location permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LocationReqCode);
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LocationReqCode);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LocationReqCode);
            }
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LocationReqCode);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LocationReqCode);
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_menu);
        findViews();
        initViews();

    }

    private void initViews() {
        // init views & handlers
        setImage(getResources().getIdentifier(Constants.Thunder_BG, null, getPackageName()), menu_IMG_bg);
        setImage(getResources().getIdentifier(Constants.RED_TITLE, null, getPackageName()), menu_IMG_title);
        setImage(getResources().getIdentifier(Constants.LOGO, null, getPackageName()), menu_IMG_logo);

        auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Main_Menu.this, Activity_Game.class);
                intent.putExtra(Constants.MODE, Constants.MODE_AUTO); // 1 for automatic
                startActivity(intent);
                finish();
            }
        });

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Main_Menu.this, Activity_Choose_Character.class);
                intent.putExtra(Constants.MODE, Constants.MODE_MANUAL); // 0 for manual
                startActivity(intent);
                finish();
            }
        });

        top10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Main_Menu.this, Activity_TopTen.class);
                startActivity(intent);
                finish();
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Main_Menu.this, Activity_Settings.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void findViews() {
        // find views
        settings = findViewById(R.id.menu_BTN_settings);
        auto = findViewById(R.id.menu_BTN_auto);
        top10 = findViewById(R.id.menu_BTN_top10);
        choose = findViewById(R.id.menu_BTN_choose);

        menu_IMG_bg = findViewById(R.id.menu_IMG_bg);
        menu_IMG_logo = findViewById(R.id.menu_IMG_logo);
        menu_IMG_title = findViewById(R.id.menu_IMG_title);
    }
}