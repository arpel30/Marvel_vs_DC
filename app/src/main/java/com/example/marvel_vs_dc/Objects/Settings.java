package com.example.marvel_vs_dc.Objects;

import com.example.marvel_vs_dc.Others.Constants;
import com.google.gson.Gson;

// class for saving player settings on the device
public class Settings {
    private float bg_music_volume;
    private boolean isMute;
    private int gameSpeed;


    public Settings() {
        // default settings
        this.bg_music_volume = 0.5f;
        this.isMute = false;
        this.gameSpeed = Constants.DELAY_MEDIUM;
    }

    public void setGameSpeed(int gameSpeed) {
        this.gameSpeed = gameSpeed;
    }

    public int getGameSpeed() {
        return this.gameSpeed;
    }


    public float getBg_music_volume() {
        return this.bg_music_volume;
    }

    public boolean isMute() {
        return this.isMute;
    }

    public void setBg_music_volume(float bg_music_volume) {
        this.bg_music_volume = bg_music_volume;
    }

    public void setMute(boolean mute) {
        isMute = mute;
    }

    public void getSettings() {
        // get from memory
        Gson gson = new Gson();
        String json = MySPV.getInstance().getString(Constants.SETTINGS, "");
        if (json != "") {
            Settings tmp = gson.fromJson(json, Settings.class);
            setBg_music_volume(tmp.getBg_music_volume());
            setMute(tmp.isMute());
            setGameSpeed(tmp.getGameSpeed());
        }
    }

    public void saveSettings() {
        // save to memory
        Gson gson = new Gson();
        String json = gson.toJson(this);
        MySPV.getInstance().putString(Constants.SETTINGS, json);
    }
}
