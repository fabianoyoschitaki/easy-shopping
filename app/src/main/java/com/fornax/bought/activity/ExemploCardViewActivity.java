package com.fornax.bought.activity;

import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.Button;
import android.widget.SeekBar;

import bought.fornax.com.bought.R;

public class ExemploCardViewActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private CardView cardView;
    private SeekBar seekBar1;
    private SeekBar seekBar2;
    private Button buttonCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exemplo_card_view);
        cardView = (CardView) findViewById(R.id.cardView);
        seekBar1 = (SeekBar) findViewById(R.id.seekBar1);
        seekBar2 = (SeekBar) findViewById(R.id.seekBar2);
        seekBar1.setOnSeekBarChangeListener(this);
        seekBar2.setOnSeekBarChangeListener(this);
        buttonCardView = (Button) findViewById(R.id.buttonCardView);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar == this.seekBar1){
            cardView.setCardElevation(progress);
            ViewCompat.setElevation(buttonCardView, progress);
        } else if (seekBar == this.seekBar2){
            cardView.setRadius(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
