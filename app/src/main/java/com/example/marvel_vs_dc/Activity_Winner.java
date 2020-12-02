package com.example.marvel_vs_dc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity_Winner extends Activity_Base {
    private ImageView hero;
    private TextView score;
    private TextView name;
    private Button new_game;
    private Button top10;
    private Button menu;
    private int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_winner_);
        findViews();
        initViews(savedInstanceState);
    }

    private void initViews(Bundle savedInstanceState) {
        // Init views
        int score_val = getIntent().getIntExtra(Constants.SCORE_KEY, 0);
        int id = getIntent().getIntExtra(Constants.ID_KEY, 0);
        mode = getIntent().getIntExtra(Constants.MODE, 0);
        String name_val = getIntent().getStringExtra(Constants.NAME_KEY);

        name.setText(name_val + " won !");
        score.setText("With Score : " + score_val);
        hero.setImageResource(id);
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
}