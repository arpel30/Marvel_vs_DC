package com.example.marvel_vs_dc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Winner_Activity extends AppCompatActivity {
    private ImageView hero;
    private TextView score;
    private TextView name;
    private Button new_game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_winner_);
        Log.d("aaaaa", "Im here");
        findViews();
        initViews(savedInstanceState);
    }


    @Override
    protected void onResume() {
        super.onResume();
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    private void initViews(Bundle savedInstanceState) {
        int score_val = getIntent().getIntExtra(MainActivity.SCORE_KEY, 0);
        int id = getIntent().getIntExtra(MainActivity.ID_KEY, 0);
        String name_val = getIntent().getStringExtra(MainActivity.NAME_KEY);

        name.setText(name_val + " won !");
        score.setText("With Score : " + score_val);
        hero.setImageResource(id);
        new_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Winner_Activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void findViews() {
        hero = findViewById(R.id.win_IMG_Player);
        score = findViewById(R.id.win_LBL_Score);
        name = findViewById(R.id.win_LBL_Name);
        new_game = findViewById(R.id.win_BTN_NewGame);
    }
}