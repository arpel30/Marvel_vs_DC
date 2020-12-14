package com.example.marvel_vs_dc.Activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SeekBar;

import com.example.marvel_vs_dc.Others.Constants;
import com.example.marvel_vs_dc.R;
import com.example.marvel_vs_dc.Objects.Settings;

public class Activity_Settings extends Activity_Base {
    private Settings settings;
    private Button back;
    private SeekBar settings_SBR_volume;
    private MediaPlayer player_bg;
    private ImageView settings_IMG_bg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settings = new Settings();
        settings.getSettings();

        findViews();
        initviews();

    }

    @Override
    protected void onStart() {
        super.onStart();
        player_bg_start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        player_bg_stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void findViews() {
        back = findViewById(R.id.settings_BTN_back);
        settings_SBR_volume = findViewById(R.id.settings_SBR_volume);
        settings_IMG_bg = findViewById(R.id.settings_IMG_bg);
    }

    private void initviews() {
        setImage(getResources().getIdentifier(Constants.Thunder_BG, null, getPackageName()), settings_IMG_bg);
        settings_SBR_volume.setProgress(Math.round(settings.getBg_music_volume() * 100));
        settings_SBR_volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float tmp = (float)progress/100;
//                Log.d("aaa", "Volume : " + tmp);
                settings.setBg_music_volume(tmp);
                if(!settings.isMute())
                    player_bg.setVolume(tmp, tmp);

//                Log.d("aaa", "Actual Volume : " + settings.getBg_music_volume());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Settings.this, Activity_Main_Menu.class);
                settings.saveSettings();
                startActivity(intent);
                finish();
            }
        });
        player_bg = MediaPlayer.create(this, Constants.BG_MUSIC);
        player_bg.setLooping(true);
        player_bg.setVolume(settings.getBg_music_volume(), settings.getBg_music_volume());

        player_bg_start();
    }

    public void player_bg_start() {
        if (player_bg != null && !player_bg.isPlaying()) {
            if(settings.isMute())
                player_bg.setVolume(0f,0f);
            player_bg.start();
        }
    }

    public void player_bg_stop() {
        if (player_bg != null)
            player_bg.pause();
    }


    public void onMuteButtonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.settings_RDB_NOTMUTE:
                if (checked) {
                    settings.setMute(false);
                    player_bg.setVolume((float)settings_SBR_volume.getProgress()/100,(float)settings_SBR_volume.getProgress()/100);
                }
                    break;
            case R.id.settings_RDB_MUTE:
                if (checked) {
                    settings.setMute(true);
                    player_bg.setVolume(0f,0f);
                }
                    break;
        }
    }

    public void onSpeedButtonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();
        int tmp = Constants.DELAY_MEDIUM;
        // Check which radio button was clicked
        switch(view.getId()) {
            case Constants.RDB_MODE_FAST:
                if (checked)
                    tmp = Constants.DELAY_FAST;
                break;
            case Constants.RDB_MODE_MEDIUM:
                if (checked)
                    tmp = Constants.DELAY_MEDIUM;
                break;
            case Constants.RDB_MODE_SLOW:
                if (checked)
                    tmp = Constants.DELAY_SLOW;
                break;
        }
        settings.setGameSpeed(tmp);
    }
}