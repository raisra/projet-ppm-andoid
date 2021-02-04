package com.example.projetppm;

//
//  HumanInterface.swift
//  projet-ppm
//
//  Description : Boutton pause, score, message, calcul du score.
//


import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.function.Function;

public class HumanInterfaceView extends View {

    //nombre de pieces réscoltées
    public int score = 0;


    //le nombre de pouvoirs
    public int nbPower = 0;

    //TODO traduire le code suivant
    //public Point powerAnchor = CGPoint(x: 0, y: 200);
    //let sizeOfPowerIcons : CGSize  = CGSize(width: 20, height: 20)


    TextView scoreLabel;
    Button pauseButton;

    Button messageButton;
    Button startButton;

    public HumanInterfaceView(Context context) {
        super(context);


        scoreLabel =  findViewById(R.id.score_label);
        pauseButton = findViewById(R.id.pause_button);
        startButton = findViewById(R.id.start_button);
        messageButton = findViewById(R.id.message_button);

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

        ImageView counterView = findViewById(R.id.counter_view);
        counterView.getAnimation().start();
       // Animation animation = AnimationUtils.loadAnimation(getContext(),R.drawable.start_count_animation);
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
