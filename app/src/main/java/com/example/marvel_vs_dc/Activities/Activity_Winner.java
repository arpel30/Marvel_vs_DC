package com.example.marvel_vs_dc.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.example.marvel_vs_dc.Others.Constants;
import com.example.marvel_vs_dc.Objects.Hero;
import com.example.marvel_vs_dc.Objects.MySPV;
import com.example.marvel_vs_dc.R;
import com.example.marvel_vs_dc.Objects.Record;
import com.example.marvel_vs_dc.Objects.TopTen;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.List;

public class Activity_Winner extends Activity_Base {
    private ImageView hero;
    private ImageView winner_IMG_bg;
    private TextView score;
    private TextView name;
    private Button new_game;
    private Button top10;
    private Button menu;
    private int mode;

    private TopTen topTen;
    private SharedPreferences prefs;
    private Gson gson;
    private FusedLocationProviderClient loc;
    private Location playerLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_winner);
        findViews();
        initTopTen();
        initViews(savedInstanceState);
    }

    private void initTopTen() {
        // Init TopTen from memory
        prefs = getSharedPreferences(Constants.SP_FILE, MODE_PRIVATE);
        gson = new Gson();

        String prefStr = prefs.getString(Constants.TOPTEN, "");
        if (prefStr != "")
            topTen = gson.fromJson(prefStr, TopTen.class);
        else
            topTen = new TopTen();
    }

    private void initViews(Bundle savedInstanceState) {
        // Init views
        mode = getIntent().getIntExtra(Constants.MODE, 0);
        String json = getIntent().getStringExtra(Constants.WINNER_KEY);
        saveRecord(json);
        setImage(getResources().getIdentifier(Constants.Conf_BG, null, getPackageName()), winner_IMG_bg);
        new_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Winner.this, Activity_Game.class);
                intent.putExtra(Constants.MODE, mode);
                startActivity(intent);
                finish();
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Winner.this, Activity_Main_Menu.class);
                startActivity(intent);
                finish();
            }
        });

        top10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Winner.this, Activity_TopTen.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void saveRecord(String json) {
        // save new record
        Hero jsonWinner = gson.fromJson(json, Hero.class);
        jsonWinner.setScore(jsonWinner.getHp());
        int score_val = jsonWinner.getHp();
        int id = jsonWinner.getId();
        String name_val = jsonWinner.getName();

        name.setText(name_val + " won !");
        score.setText("With Score : " + score_val);
        setImage(id, hero);

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yy");
        String date = format.format(System.currentTimeMillis());
        getLastLocation();

        playerLocation.setLongitude(playerLocation.getLongitude());
        playerLocation.setLatitude(playerLocation.getLatitude());

        Record r = new Record(jsonWinner.getPlayer(), jsonWinner, date, playerLocation.getLongitude(), playerLocation.getLatitude());

        topTen.newRecord(r);
        json = gson.toJson(topTen);
        MySPV.getInstance().putString(Constants.TOPTEN, json);
    }

    private void findViews() {
        // Finding views
        hero = findViewById(R.id.win_IMG_Player);
        score = findViewById(R.id.win_LBL_Score);
        name = findViewById(R.id.win_LBL_Name);
        new_game = findViewById(R.id.win_BTN_NewGame);
        top10 = findViewById(R.id.win_BTN_top10);
        menu = findViewById(R.id.win_BTN_menu);
        winner_IMG_bg = findViewById(R.id.winner_IMG_bg);
    }

    private void getLastLocation() {
        // get player location. if there is no permissions - set 0,0
            try {
                // taking location
                playerLocation = getLastKnownLocation();
            } catch (Exception e) {
                // if location is off
                playerLocation = new Location("");
                playerLocation.setLatitude(0);
                playerLocation.setLongitude(0);
            }
            if (playerLocation == null) {
                // in case there is no gps connection/permission
                playerLocation = new Location("");
                playerLocation.setLatitude(0);
                playerLocation.setLongitude(0);
            }
        }

    private Location getLastKnownLocation() {
        LocationManager mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    private void setLocation(Location location) {
        playerLocation = location;
    }

}