package com.example.projetppm;//
//  GameView.swift
//  projet-ppm
//
//  Description : gestion image perso, road, animation jump/run.
//


import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;

public class GameView extends ViewGroup {

    public String TAG = "GAMEVIEW";
    // la vue contenant tous les objets
    public ImageView character ;
    public RelativeLayout objectsView ;

    AnimationDrawable jumpAnimation;
    AnimationDrawable runAnimation;

    public long speed ;

    public GameView(Context context, AttributeSet set) {
        super(context, set);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void init(long s, Point centerOfChar, Size sizeOfChar){
        this.speed = s;
        character = (ImageView) findViewById(R.id.character_view_id);
        objectsView = (RelativeLayout) findViewById(R.id.objects_view);
        
        character.setBackgroundResource(R.drawable.animation_char_run);
        initPersonnage(centerOfChar, sizeOfChar);

        Log.d(TAG, "init: gameView");
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initPersonnage(Point center , Size size)  {
        Point topLeft = new Point(center.x - size.getWidth()/2, center.y - size.getHeight()/2);
        ModelRoad.setlayout(character,size,topLeft);
        character.setVisibility(INVISIBLE);
        runAnimation = (AnimationDrawable) character.getBackground();
    }



    public void animationForJump() {
        runAnimation.stop();
        character.setBackgroundResource(R.drawable.animation_char_jump);
        jumpAnimation = (AnimationDrawable) character.getBackground();
        jumpAnimation.setOneShot(true);
        jumpAnimation.start();
    }


    public void animationForRunning() {
        jumpAnimation.stop();

        character.setBackgroundResource(R.drawable.animation_char_run);
        runAnimation = (AnimationDrawable) character.getBackground();
        runAnimation.setOneShot(false);
        runAnimation.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void animationForTransparency() {
        Animation blinkAnimation = new AlphaAnimation(1f,0.2f);
        blinkAnimation.setDuration(Config.TRANSPARENCY_DURATION);

        blinkAnimation.setFillAfter(false);
        blinkAnimation.setRepeatMode(Animation.REVERSE);
        character.startAnimation(blinkAnimation);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void animateBlink() {
        Animation blinkAnimation = new AlphaAnimation(0.5f,1.0f);
        blinkAnimation.setDuration(Config.BLINK_DURATION);

        blinkAnimation.setFillAfter(false);
        blinkAnimation.setRepeatMode(Animation.REVERSE);
        character.startAnimation(blinkAnimation);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void animationMove(Point toPoistion) {
        ObjectAnimator moveAnimation = ObjectAnimator.ofFloat(this, "translationX", 100f);
        moveAnimation.setDuration(Config.MOVE_DURATION);

        moveAnimation.start();
    }



    private AnimationDrawable updateDurationofAnimation(AnimationDrawable input, int duration){
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


    public void startTheGame(){
        character.setVisibility(VISIBLE);
        objectsView.setVisibility(VISIBLE);
        runAnimation.start();
    }

    public void stopTheGame(){
        character.setVisibility(INVISIBLE);
        objectsView.setVisibility(INVISIBLE);
        runAnimation.stop();
    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}









