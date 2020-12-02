package com.example.marvel_vs_dc;

public class Record {

    private String name;
    private Hero hero;
    private long date;
    private float lon, lat;

    public Record() {
    }

    public Record(String name, Hero hero, long date, float lon, float lat) {
        this.name = name;
        this.hero = hero;
        this.date = date;
        this.lon = lon;
        this.lat = lat;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public String getName() {
        return name;
    }

    public Hero getHero() {
        return hero;
    }

    public long getDate() {
        return date;
    }

    public float getLon() {
        return lon;
    }

    public float getLat() {
        return lat;
    }
}
