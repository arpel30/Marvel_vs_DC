package com.example.marvel_vs_dc;

import android.util.Log;

public class Hero {

    private int luckNumber;
    private int luckColor; // by the team
    private int hp;
    private int id;
    private String name;

    public Hero(int luckNumber, int luckColor, int id, String name) {
        this.luckNumber = luckNumber;
        this.luckColor = luckColor;
        this.hp = 100;
        this.id = id;
        this.name = name;
    }

    public int hit(int val, int rand, int color){
        if(val == 1){
            val = rand;
        }
        if(val == this.luckNumber){
            this.hp+=5;
//            Log.d("aaaaa", this.name + ":" + val + ":" + luckNumber);
        }
        if(color == this.luckColor){
            val *= 2;
        }
//        Log.d("aaaaa", this.name + ":" + val);
        return val;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getLuckNumber() {
        return luckNumber;
    }

    public int getLuckColor() {
        return luckColor;
    }

    public int getHp() {
        return hp;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }



}
