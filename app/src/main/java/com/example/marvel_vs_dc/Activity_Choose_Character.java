package com.example.marvel_vs_dc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Random;

public class Activity_Choose_Character extends Activity_Base {

    private ImageView leftPlayer;
    private ImageView rightPlayer;

    private TextView leftName;
    private TextView rightName;

    private LinearLayout leftLinLay;
    private LinearLayout rightLinLay;

    private ImageView exmp;
    private ImageView button;

    private static final int IMG_SIZE = 100;
    private MainViewController mainViewController;

    Hero[] heroes;
    Hero right;
    int rightID;
    Hero left;
    int leftID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_character);
        isDoubleBackPressToClose = true;

        findViews();
        mainViewController = new MainViewController(this);
        heroes = mainViewController.initHeroes();

        initAll();
        randomInit();
        exmp.setVisibility(View.GONE);
    }

    private void randomInit() {
        left = heroes[new Random().nextInt(10)+10];
        right = heroes[new Random().nextInt(10)];

        rightPlayer.setImageResource(right.getId());
        rightName.setText(right.getName());

        leftPlayer.setImageResource(left.getId());
        leftName.setText(left.getName());
    }

    private void initAll() {
        for (int i = 0; i < heroes.length; i++) {
            ImageView iv = new ImageView(getApplicationContext());
            iv.setImageResource(heroes[i].getId());
            iv.setTag(heroes[i]);
            iv.setLayoutParams(exmp.getLayoutParams());
            setListener(iv, i);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked();
            }
        });

    }

    private void setListener(ImageView iv, int i) {
        if (i < 10) {
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    heroImageClick(0, v);
                }
            });
            rightLinLay.addView(iv);
        } else if (i < 20) {
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    heroImageClick(1, v);
                }
            });
            leftLinLay.addView(iv);
        }
    }


    private void heroImageClick(int team, View v) {
        if (team == 0) {
            right = (Hero) v.getTag();
            rightPlayer.setImageResource(right.getId());
            rightName.setText(right.getName());
        } else if (team == 1) {
            left = (Hero) v.getTag();
            leftPlayer.setImageResource(left.getId());
            leftName.setText(left.getName());
        }
    }

    private void buttonClicked() {
        Gson gson = new Gson();

        Hero[] selected = new Hero[2];
        selected[0] = left;
        selected[1] = right;
        String jsonLeft = gson.toJson(left);
        String jsonRight = gson.toJson(right);

        Log.d("aaa", jsonLeft + ", " + jsonRight);

        Intent intent = new Intent(this, Activity_Game.class);
        intent.putExtra(Constants.HEROE_SELECTED_L, jsonLeft);
        intent.putExtra(Constants.HEROE_SELECTED_R, jsonRight);
        this.startActivity(intent);
        this.finish();


    }

    private void findViews() {
        leftLinLay = findViewById(R.id.choose_LAY_left);
        rightLinLay = findViewById(R.id.choose_LAY_right);

        leftName = findViewById(R.id.choose_LBL_leftName);
        rightName = findViewById(R.id.choose_LBL_rightName);

        leftPlayer = findViewById(R.id.choose_IMG_leftPlayer);
        rightPlayer = findViewById(R.id.choose_IMG_rightPlayer);

        exmp = findViewById(R.id.choose_IMG_exmp);
        button = findViewById(R.id.choose_IMG_button);
    }
}