package com.example.marvel_vs_dc;

public class Record {

    private String name;
    private Hero hero;
    private String date;
    private double lon, lat;

    public Record() {
    }

    public Record(String name, Hero hero, String date, double lon, double lat) {
        this.name = hero.getName();
        if(name != null && name != ""){
            this.name = name;
        }
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

    public void setDate(String date) {
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

    public String getDate() {
        return date;
    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }
}
