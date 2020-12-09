package com.example.marvel_vs_dc;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Random;

public class Activity_Winner extends Activity_Base {
    private ImageView hero;
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
        setContentView(R.layout.activity_winner_);
        loc = LocationServices.getFusedLocationProviderClient(this);
        Log.d("aaa", "find");
        findViews();
        Log.d("aaa", "initTop");
        initTopTen();
        Log.d("aaa", "initViews");
        initViews(savedInstanceState);
        Log.d("aaa", "after");
    }

    private void initTopTen() {
        prefs = getSharedPreferences(Constants.SP_FILE, MODE_PRIVATE);
        gson = new Gson();

        String prefStr = prefs.getString(Constants.TOPTEN, "");
        if(prefStr != "")
            topTen = gson.fromJson(prefStr, TopTen.class);
        else
            topTen = new TopTen();

    }

    private void initViews(Bundle savedInstanceState) {
        // Init views
//        int score_val = getIntent().getIntExtra(Constants.SCORE_KEY, 0);
//        int id = getIntent().getIntExtra(Constants.ID_KEY, 0);
        mode = getIntent().getIntExtra(Constants.MODE, 0);
//        String name_val = getIntent().getStringExtra(Constants.NAME_KEY);

        String json = getIntent().getStringExtra(Constants.WINNER_KEY);
        Hero jsonWinner = gson.fromJson(json, Hero.class);
        jsonWinner.setScore(jsonWinner.getHp());
        int score_val = jsonWinner.getHp();
        int id = jsonWinner.getId();
        String name_val = jsonWinner.getName();


        name.setText(name_val + " won !");
        score.setText("With Score : " + score_val);
        hero.setImageResource(id);

//        SharedPreferences.Editor editor = prefs.edit();
//        double lon= new Random().nextInt(100), lat = new Random().nextInt(100);

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yy");
        String date = format.format(System.currentTimeMillis());
        Log.d("aaa", "getLoc");
        getLastLocation();
        Log.d("aaa", "after");
//        Log.d("aaa", l.toString());
        playerLocation.setLongitude(playerLocation.getLongitude());
        playerLocation.setLatitude(playerLocation.getLatitude());
        // need to take name from player --------------------------------------------------
        Record r = new Record(jsonWinner.getName(), jsonWinner, date, playerLocation.getLongitude(), playerLocation.getLatitude());
        // --------------------------------------------------------------------------------

        topTen.newRecord(r);
        json = gson.toJson(topTen);
        Log.d("aaa", json);
        MySPV.getInstance().putString(Constants.TOPTEN, json);
//        editor.putString(Constants.TOPTEN, json);
//        editor.commit();

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

    private void findViews() {
        // Finding views
        hero = findViewById(R.id.win_IMG_Player);
        score = findViewById(R.id.win_LBL_Score);
        name = findViewById(R.id.win_LBL_Name);
        new_game = findViewById(R.id.win_BTN_NewGame);
        top10 = findViewById(R.id.win_BTN_top10);
        menu = findViewById(R.id.win_BTN_menu);
    }

    private void getLastLocation() {
            Log.d("aaa", "startLoc");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("aaa", "null");
            Location tmp = new Location("");
            tmp.setLatitude(0);
            tmp.setLongitude(0);
//            return tmp;
            //            return tmp;
        }
        Log.d("aaa", "startTask");
//        Task<Location> locationTask = loc.getLastLocation();
        Log.d("aaa", "afterTask");
//        return loc.getLastLocation()

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Get the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Get Current Location
        playerLocation = locationManager.getLastKnownLocation(provider);

        //latitude of location
//        double myLatitude = myLocation.getLatitude();

//        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//                if(location != null){
//                    Log.d("aaa", location.toString());
//                    setLocation(location);
//                }
//            }
//        });
//
//        locationTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d("aaa", e.getLocalizedMessage());
//            }
//        });
//
//        locationTask.addOnCompleteListener(new OnCompleteListener<Location>() {
//            @Override
//            public void onComplete(@NonNull Task<Location> task) {
//
//            }
//        });
//        Log.d("aaa", playerLocation.toString());
//        Log.d("aaa", locationTask.getResult().toString());
        if(playerLocation==null){
            Location tmp = new Location("");
            tmp.setLatitude(0);
            tmp.setLongitude(0);
            playerLocation = tmp;
        }
//        return locationTask.getResult();
    }

    private void setLocation(Location location) {
        playerLocation = location;
    }

}