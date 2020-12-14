package com.example.marvel_vs_dc.Others;

import android.app.Application;

import com.example.marvel_vs_dc.Objects.MySPV;

public class App  extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        MySPV.init(this);
//        MySignal.init(this);

    }
}
