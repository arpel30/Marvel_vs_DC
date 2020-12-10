package com.example.marvel_vs_dc;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainViewController {

    private Settings gameSettings;

    private int mode;
    private AppCompatActivity activity;

    private Bundle bundle;

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
    private boolean gameStarted = false;

    private Timer autoGameTimer;
    private ProgressBar cardLoad;
    private int timeCount = 0;
    MediaPlayer player_bg;

    private LinearLayout all;
    private ImageView bg_img;

    public MainViewController(AppCompatActivity activity) {
        this.activity = activity;

        mode = activity.getIntent().getIntExtra(Constants.MODE, Constants.MODE_MANUAL);
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public void startGame() {
        if (activity instanceof Activity_Game) {
            initSettings();
            bgMusic();
            findViews();
            initHeroes();
            initDeck();
            initViews();
        }
    }

    public void findViews() {
        bg_img = activity.findViewById(R.id.main_IMG_bg);
        leftPlayer = activity.findViewById(R.id.main_IMG_leftPlayer);
        leftScore = activity.findViewById(R.id.main_LBL_leftScore);
        leftName = activity.findViewById(R.id.main_LBL_leftName);
        leftCard = activity.findViewById(R.id.main_IMG_letfCard);
        rightPlayer = activity.findViewById(R.id.main_IMG_rightPlayer);
        rightScore = activity.findViewById(R.id.main_LBL_rightScore);
        rightName = activity.findViewById(R.id.main_LBL_rightName);
        rightCard = activity.findViewById(R.id.main_IMG_rightCard);
        deal = activity.findViewById(R.id.main_IMG_button);
        leftProg = activity.findViewById(R.id.main_PRB_left);
        rightProg = activity.findViewById(R.id.main_PRB_right);
        cardLoad = activity.findViewById(R.id.main_PRB_loadCard);
    }

    public void bgMusic() {
        if (!gameSettings.isMute()) {
            MediaPlayer player_start = MediaPlayer.create(activity, Constants.FIGHT_AUDIO);
            player_start.setVolume(gameSettings.getBg_music_volume(), gameSettings.getBg_music_volume());
            player_start.start();
            player_bg = MediaPlayer.create(activity, Constants.BG_MUSIC);
            player_bg.setLooping(true); // Set looping
            player_bg.setVolume(gameSettings.getBg_music_volume(), gameSettings.getBg_music_volume());

            player_bg.start();
        }
    }

    public void playSound(String name) {
        int id = activity.getResources().getIdentifier(name, null, activity.getPackageName());
        MediaPlayer player_start = MediaPlayer.create(activity, id);
        player_start.setVolume(gameSettings.getBg_music_volume(), gameSettings.getBg_music_volume());
        player_start.start();
    }

    public void player_bg_start() {
        if (player_bg != null && !player_bg.isPlaying())
            player_bg.start();
    }

    public void player_bg_stop() {
        if (player_bg != null)
            player_bg.pause();
    }

    private void initSettings() {
        gameSettings = new Settings();
        gameSettings.getSettings();
//        Log.d("aaa", "Volume : " + gameSettings.getBg_music_volume() + ", Speed : " + gameSettings.getGameSpeed() + ", isMute : " + gameSettings.isMute());
    }

    private void round_over(Hero lost, Hero won) {
        // save params & open next activity
        // if tie - winner.hp = 0, so we will give him 1 point
        gameover = true;
        Gson gson = new Gson();
        String jsonWinner = gson.toJson(won);
        if (won.getHp() <= 0)
            won.setHp(1);
        Intent intent = new Intent(activity, Activity_Winner.class);
        intent.putExtra(Constants.WINNER_KEY, jsonWinner);
        intent.putExtra(Constants.MODE, mode);
        activity.startActivity(intent);
        activity.finish();
    }

    public void initDeck() {
        // init deck & Shuffle
        this.deck = new ArrayList<Card>();
        String[] sym = {"diamonds", "clubs", "hearts", "spades"};
        String uri = "@drawable/poker_";
        for (int i = 0; i < sym.length; i++) {
            for (int j = 1; j < 14; j++) {
                Card card = new Card(j, activity.getResources().getIdentifier(uri + sym[i] + "" + j, null, activity.getPackageName()), i % 2);
                this.deck.add(card);
            }
        }
        Collections.shuffle(this.deck);

    }

    public Hero[] initHeroes() {
        // Hard Coded heroes
        // Marvel = 0, DC = 1
        this.heroes = new Hero[20];
        heroes[0] = new Hero(2, 0, activity.getResources().getIdentifier("@drawable/thor", null, activity.getPackageName()), "Thor");
        heroes[1] = new Hero(7, 0, activity.getResources().getIdentifier("@drawable/ironman", null, activity.getPackageName()), "Ironman");
        heroes[2] = new Hero(5, 0, activity.getResources().getIdentifier("@drawable/spiderman", null, activity.getPackageName()), "Spiderman");
        heroes[3] = new Hero(10, 0, activity.getResources().getIdentifier("@drawable/groot", null, activity.getPackageName()), "Groot");
        heroes[4] = new Hero(13, 0, activity.getResources().getIdentifier("@drawable/hulk", null, activity.getPackageName()), "Hulk");
        heroes[5] = new Hero(11, 0, activity.getResources().getIdentifier("@drawable/deadpool", null, activity.getPackageName()), "Deadpool");
        heroes[6] = new Hero(6, 0, activity.getResources().getIdentifier("@drawable/black_pant", null, activity.getPackageName()), "Black Panther");
        heroes[7] = new Hero(2, 0, activity.getResources().getIdentifier("@drawable/black_widow", null, activity.getPackageName()), "Black Widow");
        heroes[8] = new Hero(3, 0, activity.getResources().getIdentifier("@drawable/cap_america", null, activity.getPackageName()), "Captain America");
        heroes[9] = new Hero(11, 0, activity.getResources().getIdentifier("@drawable/wolverine", null, activity.getPackageName()), "Wolverine");
        heroes[10] = new Hero(12, 1, activity.getResources().getIdentifier("@drawable/dc_flash", null, activity.getPackageName()), "Flash");
        heroes[11] = new Hero(4, 1, activity.getResources().getIdentifier("@drawable/dc_batman", null, activity.getPackageName()), "Batman");
        heroes[12] = new Hero(5, 1, activity.getResources().getIdentifier("@drawable/dc_green_arrow", null, activity.getPackageName()), "Green Arrow");
        heroes[13] = new Hero(10, 1, activity.getResources().getIdentifier("@drawable/dc_green_lant", null, activity.getPackageName()), "Green Lantern");
        heroes[14] = new Hero(9, 1, activity.getResources().getIdentifier("@drawable/dc_gal_gadot", null, activity.getPackageName()), "Gal Gadot");
        heroes[15] = new Hero(8, 1, activity.getResources().getIdentifier("@drawable/dc_superman", null, activity.getPackageName()), "Superman");
        heroes[16] = new Hero(6, 1, activity.getResources().getIdentifier("@drawable/darth", null, activity.getPackageName()), "Darth Vader");
        heroes[17] = new Hero(7, 1, activity.getResources().getIdentifier("@drawable/batwoman", null, activity.getPackageName()), "Batwoman");
        heroes[18] = new Hero(8, 1, activity.getResources().getIdentifier("@drawable/superwoman", null, activity.getPackageName()), "Super woman");
        heroes[19] = new Hero(7, 1, activity.getResources().getIdentifier("@drawable/cyborg", null, activity.getPackageName()), "Cyborg");
        return heroes;
    }

    public void initViews() {
        // Init all views
        setImage(activity.getResources().getIdentifier(Constants.Tekken_BG, null, activity.getPackageName()), bg_img);
        String leftFromMemory = activity.getIntent().getStringExtra(Constants.HEROE_SELECTED_L);
        String rightFromMemory = activity.getIntent().getStringExtra(Constants.HEROE_SELECTED_R);
        if (leftFromMemory != null && rightFromMemory != null) {
            Gson gson = new Gson();
            leftHero = gson.fromJson(leftFromMemory, Hero.class);
            rightHero = gson.fromJson(rightFromMemory, Hero.class);
        } else {
            leftHero = heroes[new Random().nextInt(num_marvel) + num_dc];
            rightHero = heroes[new Random().nextInt(num_marvel)];
        }
        rightName.setText(rightHero.getName());
        leftName.setText(leftHero.getName());

        leftPlayer.setImageResource(leftHero.getId());
        rightPlayer.setImageResource(rightHero.getId());

        rightScore.setText(rightHero.getHp() + "");
        leftScore.setText(leftHero.getHp() + "");

        // handle button by mode selected
        if (mode == Constants.MODE_AUTO) {
            setAutoHandler();
        } else {
            setManualHandler();
        }
    }

    private void setManualHandler() {
        deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Deal();
            }
        });
    }

    private void setAutoHandler() {
        deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deal.setVisibility(View.GONE);
                cardLoad.setVisibility(View.VISIBLE);
                gameStarted = true;
                startTimer();
            }
        });
    }

    private void startTimer() {
        // Timer for card flip & loading bar
        autoGameTimer = new Timer();
        autoGameTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (gameover) {
                            autoGameTimer.cancel();
                        } else {
                            if (timeCount >= gameSettings.getGameSpeed()) {
                                timeCount = 0;
                                Deal();
                            }
                            cardLoad.setProgress((timeCount * 100) / gameSettings.getGameSpeed());
                            timeCount += Constants.FRACTION;
                        }

                    }
                });
            }
        }, 0, Constants.FRACTION);
    }

    public void stopTimer() {
        // stop & delete timer
        autoGameTimer.cancel();
        autoGameTimer = null;
    }

    public void runTimer() {
        // start timer (make sure it's null first)
        if (autoGameTimer == null) {
            startTimer();
        }
    }


    public void onStart() {
        // play music & run timer
        player_bg_start();
        if (gameStarted)
            runTimer();
    }


    public void onStop() {
        // stop music & cancel timer
        player_bg_stop();
        if (gameStarted)
            stopTimer();
    }

    public void Deal() {
        // Deal next cards & hit
        if(!gameSettings.isMute())
            playSound(Constants.FLIP_NAME);

        Card c = this.deck.remove(0);
        setImage(c.getId(), leftCard);
        rightHero.setHp(rightHero.getHp() - leftHero.hit(c.getValue(), new Random().nextInt(13), c.getColor()));

        c = this.deck.remove(0);
        setImage(c.getId(), rightCard);
        leftHero.setHp(leftHero.getHp() - rightHero.hit(c.getValue(), new Random().nextInt(13), c.getColor()));

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
                round_over(leftHero, rightHero);
            } else if (leftHero.getHp() >= rightHero.getHp()) {
                leftHero.setHp(leftHero.getHp() - rightHero.getHp());
                leftScore.setText(leftHero.getHp() + "");
                rightHero.setHp(0);
                rightScore.setText(0 + "");
                round_over(rightHero, leftHero);
            }
        }


    }

    public void setImage(int id, ImageView view) {
        // set image with glide
        Glide
                .with(activity)
                .load(id)
                .into(view);
    }
}