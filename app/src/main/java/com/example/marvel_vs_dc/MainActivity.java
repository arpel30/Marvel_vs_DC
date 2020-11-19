package com.example.marvel_vs_dc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static final String SCORE_KEY = "SCORE_KEY";
    public static final String NAME_KEY = "NAME_KEY";
    public static final String ID_KEY = "ID_KEY";

    private ProgressBar leftProg;
    private ProgressBar rightProg;

    private ImageView leftPlayer;
    private ImageView leftCard;
    private ImageView rightPlayer;
    private ImageView rightCard;

    private TextView leftScore;
    private TextView leftName;
    private TextView rightScore;
    private TextView rightName;

    private ImageView deal;

    private Hero[] heroes;
    Hero leftHero;
    Hero rightHero;

    private int num_marvel = 10;
    private int num_dc = 10;

    private ArrayList<Card> deck;
    private boolean gameover = false;
    private Random rand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Full screen, no buttons bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_main);

        // Init arraylist of cards, heroes & views
        initHeroes();
        initDeck();
        initViews();

        deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Deal(savedInstanceState);
            }
        });


//        setViews();
    }

    @Override
    protected void onResume() {
        // Full screen & hide bars
        super.onResume();
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    private void round_over(Bundle savedInstanceState, Hero lost, Hero won) {
        // save params & open next activity
        // if tie - winner.hp = 0, so we will give him 1 point
        if (won.getHp() <= 0)
            won.setHp(1);
        Intent intent = new Intent(MainActivity.this, Winner_Activity.class);
        intent.putExtra(SCORE_KEY, won.getHp());
        intent.putExtra(ID_KEY, won.getId());
        intent.putExtra(NAME_KEY, won.getName());
        startActivity(intent);
        finish();
    }

    private void initDeck() {
        // init deck & Shuffle
        this.deck = new ArrayList<Card>();
        String[] sym = {"diamonds", "clubs", "hearts", "spades"};
        String uri = "@drawable/poker_";
        for (int i = 0; i < sym.length; i++) {
            for (int j = 1; j < 14; j++) {
                Card card = new Card(j, getResources().getIdentifier(uri + sym[i] + "" + j, null, getPackageName()), i % 2);
                this.deck.add(card);
            }
        }
        Collections.shuffle(this.deck);

    }

    private void initHeroes() {
        // Hard Coded heroes
        // Marvel = 0, DC = 1
        this.heroes = new Hero[20];
        heroes[0] = new Hero(7, 0, getResources().getIdentifier("@drawable/ironman", null, getPackageName()), "Ironman");
        heroes[1] = new Hero(2, 0, getResources().getIdentifier("@drawable/thor", null, getPackageName()), "Thor");
        heroes[2] = new Hero(5, 0, getResources().getIdentifier("@drawable/spiderman", null, getPackageName()), "Spiderman");
        heroes[3] = new Hero(10, 0, getResources().getIdentifier("@drawable/groot", null, getPackageName()), "Groot");
        heroes[4] = new Hero(13, 0, getResources().getIdentifier("@drawable/hulk", null, getPackageName()), "Hulk");
        heroes[5] = new Hero(11, 0, getResources().getIdentifier("@drawable/deadpool", null, getPackageName()), "Deadpool");
        heroes[6] = new Hero(6, 0, getResources().getIdentifier("@drawable/black_pant", null, getPackageName()), "Black Panther");
        heroes[7] = new Hero(2, 0, getResources().getIdentifier("@drawable/black_widow", null, getPackageName()), "Black Widow");
        heroes[8] = new Hero(3, 0, getResources().getIdentifier("@drawable/cap_america", null, getPackageName()), "Captain America");
        heroes[9] = new Hero(11, 0, getResources().getIdentifier("@drawable/wolverine", null, getPackageName()), "Wolverine");
        heroes[10] = new Hero(12, 1, getResources().getIdentifier("@drawable/dc_flash", null, getPackageName()), "Flash");
        heroes[11] = new Hero(4, 1, getResources().getIdentifier("@drawable/dc_batman", null, getPackageName()), "Batman");
        heroes[12] = new Hero(5, 1, getResources().getIdentifier("@drawable/dc_green_arrow", null, getPackageName()), "Green Arrow");
        heroes[13] = new Hero(10, 1, getResources().getIdentifier("@drawable/dc_green_lant", null, getPackageName()), "Green Lantern");
        heroes[14] = new Hero(9, 1, getResources().getIdentifier("@drawable/dc_gal_gadot", null, getPackageName()), "Gal Gadot");
        heroes[15] = new Hero(8, 1, getResources().getIdentifier("@drawable/dc_superman", null, getPackageName()), "Superman");
        heroes[16] = new Hero(6, 1, getResources().getIdentifier("@drawable/darth", null, getPackageName()), "Darth Vader");
        heroes[17] = new Hero(7, 1, getResources().getIdentifier("@drawable/batwoman", null, getPackageName()), "Batwoman");
        heroes[18] = new Hero(8, 1, getResources().getIdentifier("@drawable/superwoman", null, getPackageName()), "Super woman");
        heroes[19] = new Hero(7, 1, getResources().getIdentifier("@drawable/cyborg", null, getPackageName()), "Cyborg");
    }

    private void initViews() {
        // Find & init all views
        leftPlayer = findViewById(R.id.main_IMG_leftPlayer);
        leftScore = findViewById(R.id.main_LBL_leftScore);
        leftName = findViewById(R.id.main_LBL_leftName);
        leftCard = findViewById(R.id.main_IMG_letfCard);
        rightPlayer = findViewById(R.id.main_IMG_rightPlayer);
        rightScore = findViewById(R.id.main_LBL_rightScore);
        rightName = findViewById(R.id.main_LBL_rightName);
        rightCard = findViewById(R.id.main_IMG_rightCard);
        deal = findViewById(R.id.main_IMG_button);
        this.rand = new Random();

        leftProg = findViewById(R.id.main_PRB_left);
        rightProg = findViewById(R.id.main_PRB_right);

        leftHero = heroes[rand.nextInt(num_marvel) + num_dc];
        rightHero = heroes[rand.nextInt(num_marvel)];
        rightName.setText(rightHero.getName());
        leftName.setText(leftHero.getName());

        leftPlayer.setImageResource(leftHero.getId());
        rightPlayer.setImageResource(rightHero.getId());

        rightScore.setText(rightHero.getHp() + "");
        leftScore.setText(leftHero.getHp() + "");

    }

    private void Deal(Bundle savedInstanceState) {
        // Deal next cards & hit
        Card c = this.deck.remove(0);
        leftCard.setImageResource(c.getId());
        rightHero.setHp(rightHero.getHp() - leftHero.hit(c.getValue(), rand.nextInt(13), c.getColor()));

        c = this.deck.remove(0);
        rightCard.setImageResource(c.getId());
        leftHero.setHp(leftHero.getHp() - rightHero.hit(c.getValue(), rand.nextInt(13), c.getColor()));

        rightScore.setText(rightHero.getHp() + "");
        leftScore.setText(leftHero.getHp() + "");

        leftProg.setProgress(leftHero.getHp());
        rightProg.setProgress(rightHero.getHp());

        if (leftHero.getHp() <= 0 || rightHero.getHp() <= 0) {
            if (leftHero.getHp() < rightHero.getHp()) {
                rightHero.setHp(rightHero.getHp() - leftHero.getHp());
                rightScore.setText(rightHero.getHp() + "");
                leftHero.setHp(0);
                leftScore.setText(0 + "");
                round_over(savedInstanceState, leftHero, rightHero);
            } else if (leftHero.getHp() >= rightHero.getHp()) {
                leftHero.setHp(leftHero.getHp() - rightHero.getHp());
                leftScore.setText(leftHero.getHp() + "");
                rightHero.setHp(0);
                rightScore.setText(0 + "");
                round_over(savedInstanceState, rightHero, leftHero);
            }
        }
    }
}