package com.example.projetppm;

//
//  HumanInterface.swift
//  projet-ppm
//
//  Description : Boutton pause, score, message, calcul du score.
//


import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class HumanInterfaceView extends ConstraintLayout implements  Game {

    public String TAG = "HumanInterfaceView";
    //nombre de pieces réscoltées
    public int score = 0;


    //le nombre de pouvoirs
    public int nbPower = 0;

    //TODO traduire le code suivant
    //public Point powerAnchor = CGPoint(x: 0, y: 200);
    //let sizeOfPowerIcons : CGSize  = CGSize(width: 20, height: 20)


    TextView scoreLabel;
    ImageButton pauseButton;

    ImageButton messageButton;
    ImageButton startButton;

    ImageView counterView;


    public HumanInterfaceView(Context context, AttributeSet set) {
        super(context, set);
    }

    public void init(){
        scoreLabel =  findViewById(R.id.score_label_id);
        pauseButton = findViewById(R.id.pause_button_id);
        startButton = findViewById(R.id.start_button_id);
        messageButton = findViewById(R.id.message_button_id);
        counterView = findViewById(R.id.counter_view_id);




        scoreLabel.setVisibility(INVISIBLE);
        pauseButton.setVisibility(INVISIBLE);
        startButton.setVisibility(INVISIBLE);
        messageButton.setVisibility(INVISIBLE);
        counterView.setVisibility(VISIBLE);

      //  Log.d(TAG, "init: HumanInterfaceView");
    }

    public void startTheGame(){
        scoreLabel.setVisibility(VISIBLE);
        startButton.setVisibility(VISIBLE);
        messageButton.setVisibility(VISIBLE);
        counterView.setVisibility(INVISIBLE);

        pauseButton.setVisibility(INVISIBLE);
    }

    public void stopTheGame(){
        scoreLabel.setVisibility(INVISIBLE);
        startButton.setVisibility(INVISIBLE);
        messageButton.setVisibility(INVISIBLE);
        counterView.setVisibility(INVISIBLE);

        pauseButton.setVisibility(VISIBLE);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }


    public int getScore() {
        return score;
    }

    public void setScore(int score){
        this.score = score;

        scoreLabel.setText(String.valueOf(score));

        invalidate();
        requestLayout();
    }

    public void addScore(int score){
        this.score += score;
        this.scoreLabel.setText(String.valueOf(score));
    }




    public void animationForNumber() {
        AnimationDrawable counterAnimation = (AnimationDrawable)getResources().getDrawable( R.drawable.animation_start_count );
        counterAnimation.setOneShot(true);
        counterView.setBackground(counterAnimation);
        counterAnimation.start();
    }


//    func addPower(powerView : UIImageView, duration : TimeInterval){
//        powerView.isHidden = false
//        powerView.frame.origin = self.powerAnchor
//        powerView.frame.size = self.sizeOfPowerIcons
//
//        nbPower += 1
//        powerAnchor.y += self.sizeOfPowerIcons.height
//
//        UIView.animate(withDuration: duration, delay: 0, options: .curveEaseIn, animations:
//        {
//            powerView.alpha = 0
//        },
//        completion: {(Bool) in
//                print("---------------completion done")
//            self.nbPower -= 1
//            self.powerAnchor.y -= self.sizeOfPowerIcons.height
//            powerView.isHidden = true
//        })
//
//    }
//
//    func resetPower() {
//        powerAnchor.y -= (sizeOfPowerIcons.height * CGFloat(nbPower))
//        nbPower = 0
//    }

}
