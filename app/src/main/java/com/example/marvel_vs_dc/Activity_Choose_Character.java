package com.example.marvel_vs_dc;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    private ImageView gameButton;

    private RadioGroup chooseMode;
    private RadioButton modeAuto;
    private RadioButton modeManual;

    private Button back;

    private static final int IMG_SIZE = 100;
    private MainViewController mainViewController;
    private int mode = 0; // 0 - manual, 1 - auto

    private EditText rightPlayerName;
    private EditText leftPlayerName;

    Hero[] heroes;
    Hero right;
    Hero left;

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
        // choose 2 random characters for start
        left = heroes[new Random().nextInt(10)+10];
        right = heroes[new Random().nextInt(10)];

        rightPlayer.setImageResource(right.getId());
        rightName.setText(right.getName());

        leftPlayer.setImageResource(left.getId());
        leftName.setText(left.getName());
    }

    private void initAll() {
        // init all characters to be chosen from & listener
        for (int i = 0; i < heroes.length; i++) {
            ImageView iv = new ImageView(getApplicationContext());
            iv.setImageResource(heroes[i].getId());
            iv.setTag(heroes[i]);
            iv.setLayoutParams(exmp.getLayoutParams());
            setListener(iv, i);
        }
        gameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Choose_Character.this, Activity_Main_Menu.class);
                startActivity(intent);
                finish();
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

    private void startGame() {
        Gson gson = new Gson();

        left.setPlayer(handleName(leftPlayerName.getText().toString(), left.getName()));
        right.setPlayer(handleName(rightPlayerName.getText().toString(), right.getName()));

        Hero[] selected = new Hero[2];
        selected[0] = left;
        selected[1] = right;
        String jsonLeft = gson.toJson(left);
        String jsonRight = gson.toJson(right);

        Intent intent = new Intent(this, Activity_Game.class);
        intent.putExtra(Constants.HERO_SELECTED_L, jsonLeft);
        intent.putExtra(Constants.HERO_SELECTED_R, jsonRight);

        intent.putExtra(Constants.MODE, mode);

        this.startActivity(intent);
        this.finish();


    }

    private String handleName(String text, String name) {
        if(text == "")
            return name;
        return text;
    }

    private void findViews() {
        leftLinLay = findViewById(R.id.choose_LAY_left);
        rightLinLay = findViewById(R.id.choose_LAY_right);

        leftName = findViewById(R.id.choose_LBL_leftName);
        rightName = findViewById(R.id.choose_LBL_rightName);

        leftPlayer = findViewById(R.id.choose_IMG_leftPlayer);
        rightPlayer = findViewById(R.id.choose_IMG_rightPlayer);

        exmp = findViewById(R.id.choose_IMG_exmp);
        gameButton = findViewById(R.id.choose_IMG_button);

        chooseMode = findViewById(R.id.choose_RDG_mode);
        modeAuto = findViewById(Constants.RDB_MODE_AUTO_ID);
        modeManual = findViewById(Constants.RDB_MODE_MANUAL_ID);

        back = findViewById(R.id.choose_BTN_back);

        rightPlayerName = findViewById(R.id.choose_EDT_rightPlayer);
        leftPlayerName = findViewById(R.id.choose_EDT_leftPlayer);
    }

    public void onRadioButtonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case Constants.RDB_MODE_AUTO_ID:
                if (checked)
                    mode = 1;
                    break;
            case Constants.RDB_MODE_MANUAL_ID:
                if (checked)
                    mode = 0;
                    break;
        }
//        Log.d("aaa", "Mode is " + mode);
    }
}