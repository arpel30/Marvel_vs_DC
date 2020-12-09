package com.example.marvel_vs_dc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Activity_TopTen extends Activity_Base {

    private TextView cor;
    private Button topten_BTN_menu;

    private Fragment_list fragment_list;
    private Fragment_map fragment_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten);

        findViews();
        initViews();

        fragment_list = new Fragment_list();
        fragment_list.setCallBack(callBack);
        getSupportFragmentManager().beginTransaction().add(R.id.topten_LAY_list, fragment_list).commit();

        fragment_map = new Fragment_map();
        getSupportFragmentManager().beginTransaction().add(R.id.topten_LAY_map, fragment_map).commit();

    }

    private CallBack_TopTen callBack = new CallBack_TopTen() {
        @Override
        public void changeTitle(String str) {
            cor.setText(str);
        }

        @Override
        public void displayLocation(double lat, double lon, String name) {
            fragment_map.showLocationOnMap(lat, lon, name);
        }
    };

    private void initViews() {
        topten_BTN_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_TopTen.this, Activity_Main_Menu.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void findViews() {
        cor = findViewById(R.id.topten_LBL_cor);
        topten_BTN_menu = findViewById(R.id.topten_BTN_menu);
    }


}