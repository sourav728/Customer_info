package com.example.tvd.customer_info;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;

public class Dummy extends AppCompatActivity {
    SeekBar seekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
    }

    public void nextPhase() {
        seekBar.setProgress(seekBar.getProgress() + 1);
    }
}
