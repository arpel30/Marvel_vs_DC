package com.example.marvel_vs_dc;

import android.app.Application;

public class App  extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        MySPV.init(this);
//        MySignal.init(this);

    }
}
