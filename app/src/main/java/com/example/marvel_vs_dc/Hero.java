package com.example.marvel_vs_dc;

import android.util.Log;

public class Hero {

    private int luckNumber;
    private int luckColor; // By the team (Dc/Marvel)
    private int hp;
    private int id;
    private String name;
    private int score;

    public Hero() {
    }

    public Hero(int luckNumber, int luckColor, int id, String name) {
        this.luckNumber = luckNumber;
        this.luckColor = luckColor;
        this.hp = 100;
        this.id = id;
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public Hero(int score, int id, String name) {
        this.score = score;
        this.id = id;
        this.name = name;
    }

    public int hit(int val, int rand, int color){
        // Returns the player hit power (according to card & random number)
        if(val == 1){ // Ace = random
            val = rand;
        }
        if(val == this.luckNumber){ // Luck number - Life Bonus
            this.hp+=5;
        }
        if(color == this.luckColor){ // Luck color - 2*HitPower
            val *= 2;
        }
        return val;
    }


    // Getters & Setters
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

    @Override
    public String toString() {
        return "Hero{" +
                "luckNumber=" + luckNumber +
                ", luckColor=" + luckColor +
                ", hp=" + hp +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
