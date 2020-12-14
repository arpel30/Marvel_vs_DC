package com.example.marvel_vs_dc.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.marvel_vs_dc.Others.Constants;
import com.example.marvel_vs_dc.Objects.Hero;
import com.example.marvel_vs_dc.Others.MainViewController;
import com.example.marvel_vs_dc.R;
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
    private int mode = Constants.MODE_AUTO; // 0 - manual, 1 - auto

    private EditText rightPlayerName;
    private EditText leftPlayerName;

    private ImageView choose_IMG_bg;

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
        left = heroes[new Random().nextInt(Constants.NUM_MARVEL)+Constants.NUM_DC];
        right = heroes[new Random().nextInt(Constants.NUM_DC)];

        setImage(right.getId(), rightPlayer);
        rightName.setText(right.getName());

        setImage(left.getId(), leftPlayer);
        leftName.setText(left.getName());
    }

    private void initAll() {
        // init all characters to be chosen from & listener
        setImage(getResources().getIdentifier(Constants.Thunder_BG, null, getPackageName()), choose_IMG_bg);
        for (int i = 0; i < heroes.length; i++) {
            ImageView iv = new ImageView(getApplicationContext());
            setImage(heroes[i].getId(), iv);
            iv.setTag(heroes[i]);
            iv.setLayoutParams(exmp.getLayoutParams());
            setListener(iv, i);
        }
        setImage(getResources().getIdentifier(Constants.SHIELD, null, getPackageName()), gameButton);
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
        if (i < Constants.NUM_MARVEL) {
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    heroImageClick(0, v);
                }
            });
            rightLinLay.addView(iv);
        } else if (i < Constants.NUM_MARVEL + Constants.NUM_DC) {
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
            setImage(right.getId(), rightPlayer);
            rightName.setText(right.getName());
        } else if (team == 1) {
            left = (Hero) v.getTag();
            setImage(left.getId(), leftPlayer);
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
        if(text.matches(""))
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

        choose_IMG_bg = findViewById(R.id.choose_IMG_bg);
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
    }
}