package com.example.marvel_vs_dc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Activity_TopTen extends Activity_Base {

    private Fragment_list fragment_list;
//    private Fragment_Map fragment_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten);

        findViews();
        initViews();

        fragment_list = new Fragment_list();
//        fragment_list.setCallBack_top(callBack_top);
        getSupportFragmentManager().beginTransaction().add(R.id.topten_LAY_list, fragment_list).commit();

//        fragment_map = new Fragment_Map();
//        getSupportFragmentManager().beginTransaction().add(R.id.topten_LAY_map, fragment_map).commit();

    }

    private void initViews() {
    }

    private void findViews() {
    }


}