package com.example.projetppm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class WelcomViewController extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.welcome_view);

    }


    public void startGame(View view){
        Intent intent = new Intent(view.getContext(), GameViewController.class);
        startActivity(intent);
        finish();
    }


}
