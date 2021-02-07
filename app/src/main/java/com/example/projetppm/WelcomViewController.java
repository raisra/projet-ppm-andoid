package com.example.projetppm;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class WelcomViewController extends AppCompatActivity {

    public static MediaPlayer gameSound;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_view);
        this.gameSound = MediaPlayer.create(getApplicationContext(), R.raw.gamesound);
    }


    public void startTheGame(View view){
        Intent intent = new Intent(view.getContext(), GameViewController.class);
        gameSound.start();
        startActivity(intent);
        finish();
    }

}
