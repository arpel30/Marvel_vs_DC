package com.example.marvel_vs_dc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class Activity_Main_Menu extends Activity_Base {

    private Button settings;
    private Button top10;
    private Button auto;
    private Button choose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_menu);
        findViews();
        auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Main_Menu.this, Activity_Game.class);
                intent.putExtra(Constants.MODE, 1); // 1 for automatic
                startActivity(intent);
                finish();
            }
        });

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Main_Menu.this, Activity_Choose_Character.class);
                intent.putExtra(Constants.MODE, 0); // 1 for automatic
                startActivity(intent);
                finish();
            }
        });
    }

    private void findViews() {
        settings = findViewById(R.id.menu_BTN_settings);
        auto = findViewById(R.id.menu_BTN_auto);
        top10 = findViewById(R.id.menu_BTN_top10);
        choose = findViewById(R.id.menu_BTN_choose);
    }
}