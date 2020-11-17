package com.example.marvel_vs_dc;

public class Card {
    private int value;
    private int id;
    private int color;

    public int getColor() {
        return color;
    }

    public Card(int value, int id, int color){
        this.value = value;
        this.id = id;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public int getValue() {
        return value;
    }
}

