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

public class GameView extends RelativeLayout implements Game{

    public String TAG = "GAMEVIEW";
    /*
    La vue contenant le personnage
     */
    public ImageView character ;
    /*
    la vue contenant les objets pieces et autres pouvoirs
     */
    public RelativeLayout objectsView ;

    /**
     * Les animations
     */
    AnimationDrawable jumpAnimation;
    AnimationDrawable runAnimation;

    /**
     * la durée d'une animation
     */
    public long speed ;

    /**
     * Constructeur par défaut appelé automatiquement qd la vue est généré à partir de son fichier xml
     * @param context
     * @param set
     */
    public GameView(Context context, AttributeSet set) {
        super(context, set);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    /**
     * Init de la vue
     */
    public void init(long s, Point centerOfChar, Size sizeOfChar){
        this.speed = s;
        character = (ImageView) findViewById(R.id.character_view_id);
        objectsView = (RelativeLayout) findViewById(R.id.objects_view);
        

        initPersonnage(centerOfChar, sizeOfChar);

        Log.d(TAG, "init: gameView");
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    /**
     * Init de a vue du personnage. Init de l'animation du personnage
     */
    private void initPersonnage(Point center , Size size)  {
        Point topLeft = new Point(center.x - size.getWidth()/2, center.y - size.getHeight()/2);
        ModelRoad.setlayout(character,size,topLeft);
        character.setVisibility(INVISIBLE);

        jumpAnimation = (AnimationDrawable)getResources().getDrawable( R.drawable.animation_char_jump );
        jumpAnimation.setOneShot(true);
        runAnimation = (AnimationDrawable)getResources().getDrawable( R.drawable.animation_char_run );
        runAnimation.setOneShot(false);

        animationForRunning();
    }



    public void animationForJump() {
        runAnimation.stop();
        character.setBackground(jumpAnimation);
        jumpAnimation.start();
    }


    public void animationForRunning() {
        character.setBackground(runAnimation);
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

}









