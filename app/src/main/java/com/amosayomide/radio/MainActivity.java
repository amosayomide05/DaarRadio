package com.amosayomide.radio;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private String streamUrl;
    private Button btnPlay;
    private ImageView imgPlayPause;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        streamUrl = getIntent().getStringExtra("STREAM_URL");

        btnPlay = findViewById(R.id.btnPlay);
        imgPlayPause = findViewById(R.id.imgPlayPause);
        seekBar = findViewById(R.id.seekBar);

        // Set the SeekBar's maximum value
        seekBar.setMax(100);

        // Set the initial volume to 50%
        seekBar.setProgress(50);

        // Set volume of MediaPlayer based on the SeekBar position
        float volume = seekBar.getProgress() / 100f;

        btnPlay.setOnClickListener(v -> {
            if (mediaPlayer == null) {
                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(streamUrl);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    mediaPlayer.setVolume(volume, volume);  // Set initial volume
                    imgPlayPause.setImageResource(R.drawable.ic_pause);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Error playing stream", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    imgPlayPause.setImageResource(R.drawable.ic_play);
                } else {
                    mediaPlayer.start();
                    imgPlayPause.setImageResource(R.drawable.ic_pause);
                }
            }
        });

        // Update MediaPlayer volume as the SeekBar changes
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float volume = progress / 100f;
                if (mediaPlayer != null) {
                    mediaPlayer.setVolume(volume, volume); // Adjust volume based on SeekBar position
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        super.onDestroy();
    }
}
