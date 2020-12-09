package com.example.marvel_vs_dc;

import android.os.Bundle;
import android.view.Window;

public class Activity_Game extends Activity_Base {
    private MainViewController mainViewController;
    private int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Full screen, no buttons bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_game);


        mainViewController = new MainViewController(this);
        this.isDoubleBackPressToClose = true;

        // Init arraylist of cards, heroes & views
//        mainViewController.findViews();
        mainViewController.setBundle(savedInstanceState);
        mainViewController.bgMusic();
        mainViewController.initHeroes();
        mainViewController.initDeck();
        mainViewController.initViews();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mainViewController.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainViewController.onStop();
    }
}
