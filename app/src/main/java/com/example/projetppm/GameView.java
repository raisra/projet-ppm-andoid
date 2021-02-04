package com.example.projetppm;//
//  GameView.swift
//  projet-ppm
//
//  Description : gestion image perso, road, animation jump/run.
//


import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.util.Size;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class GameView extends ViewGroup {

    public long JUMP_DURATION = 1000L;
    public long BLINK_DURATION  = 5000L;
    public long MOVE_DURATION = 1000L;
    public long TRANSPARENCY_DURATION = 3000L;


    public ImageView character ;


    AnimationDrawable jumpAnimation;
    AnimationDrawable runAnimation;


    // la vue contenant tous les objets
    public View objectsView ;

/*
    //ajout de flou
    let gradientLayer : CAGradientLayer = {

        let initialColor : CGColor = UIColor.blue.cgColor
        let fincalColor : CGColor = CGColor.init(red: 0, green: 0.2, blue: 0.7, alpha: 0)

        let gradientLayer =  CAGradientLayer()
        gradientLayer.type = .axial
        gradientLayer.colors = [initialColor, fincalColor]
        gradientLayer.locations = [0, 0.7]
        gradientLayer.frame = UIScreen.main.bounds


        return gradientLayer
    }()
*/

    public long speed ;




    public GameView(Context context, long s, Point position, Size sizeOfChar){
        super(context);
        objectsView = new View(context);
        this.speed = s;

        character = (ImageView) findViewById(R.id.character_view);
        character.setBackgroundResource(R.drawable.run);
        runAnimation = (AnimationDrawable) character.getBackground();

        initPersonnage(position, sizeOfChar);


        this.addView(objectsView);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }









    public void animationForJump() {
        runAnimation.stop();
        character.setBackgroundResource(R.drawable.jump);
        jumpAnimation = (AnimationDrawable) character.getBackground();
        jumpAnimation.setOneShot(true);
        jumpAnimation.start();
    }


    public void animationForRunning() {
        jumpAnimation.stop();

        character.setBackgroundResource(R.drawable.run);
        runAnimation = (AnimationDrawable) character.getBackground();
        runAnimation.setOneShot(false);
        runAnimation.start();
    }

    public void animationForTransparency() {
        Animation blinkAnimation = new AlphaAnimation(1f,0.2f);
        blinkAnimation.setDuration(TRANSPARENCY_DURATION);

        blinkAnimation.setFillAfter(false);
        blinkAnimation.setRepeatMode(Animation.REVERSE);
        character.startAnimation(blinkAnimation);
    }


    public void animateBlink() {
        Animation blinkAnimation = new AlphaAnimation(0.5f,1.0f);
        blinkAnimation.setDuration(BLINK_DURATION);

        blinkAnimation.setFillAfter(false);
        blinkAnimation.setRepeatMode(Animation.REVERSE);
        character.startAnimation(blinkAnimation);
    }

    public void animationMove(Point toPoistion) {
        ObjectAnimator moveAnimation = ObjectAnimator.ofFloat(this, "translationX", 100f);
        moveAnimation.setDuration(MOVE_DURATION);

        moveAnimation.start();
    }

    private void initPersonnage(Point center , Size size)  {
        ModelRoad.setlayout(character,size,center);
        character.setVisibility(INVISIBLE);
    }

    public AnimationDrawable updateDurationofAnimation(AnimationDrawable input, int duration){
        AnimationDrawable output = new AnimationDrawable();

        for(int i = 0 ; i < input.getNumberOfFrames() ; i++)
        {
            output.addFrame(input.getFrame(i), duration);
        }

        output.setOneShot(input.isOneShot());

        return  output;
    }

    public void setSpeed(int speed)  {
        this.speed = speed;
        updateDurationofAnimation(runAnimation, speed);
    }


    public void startAnimation(){
        runAnimation.stop();
    }


    public void stopAnimation(){
        runAnimation.stop();
    }


    public void hideCharacter()  {
        character.setVisibility(INVISIBLE);
    }

    public void showCharacter(){
        character.setVisibility(VISIBLE);
    }



}









