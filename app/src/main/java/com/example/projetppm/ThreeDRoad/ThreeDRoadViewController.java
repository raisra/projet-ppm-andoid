package com.example.projetppm.ThreeDRoad;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.projetppm.Frame;
import com.example.projetppm.GameView;
import com.example.projetppm.GameViewController;
import com.example.projetppm.ModelRoad;
import com.example.projetppm.R;
import com.example.projetppm.Type;

import java.util.ArrayList;
import java.util.Map;

import static android.os.Build.VERSION_CODES.P;

public class ThreeDRoadViewController  {

    public RelativeLayout roadView;
    public Context ctx;
    public  Map<TypeOfRoad, String> names;

    public String TAG = "THReeDRoadViewController";

    public long duration;

    public int nbElements;
    public Level level;

    public ThreeDRoadModel model3D;
    public int N;


    private ArrayList<Pair< ImageView, Type>> buffer;
    //créé la vue associée à l'element en parametre

    public  boolean goingToTurn ;
    public boolean stopCoins;


    public ThreeDRoadViewController(){
        super();
    }

    public  void init(Context ctx,RelativeLayout roadView) {
        this.roadView = roadView;
        this.ctx = ctx;

        Log.d(TAG, "init roadView : " + roadView);
    }

    public ThreeDRoadViewController( Map<TypeOfRoad, String> names, long duration, ThreeDRoadModel model3D, int N){
        super();
        this.N = N;
        this.names = names;
        this.nbElements = 0;
        level = Level.LOW;
        this.model3D = model3D;

        buffer = new ArrayList<>();

        goingToTurn = false;
        stopCoins = false;
    }


    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void updateDuration(long d){
        duration = d;
    }

    private String getName(Type accordingTo ){
        return names.get(accordingTo);
    }




    public void getImages(Type ofType){
        String name = getName(ofType);

        Log.d(TAG, "---------getImages: of type " + ofType);

        if(ofType == TypeOfRoad.TURN_RIGHT || ofType == TypeOfRoad.TURNLEFT){
            Log.d(TAG, "getImages: turn left");
        }
        Bitmap image = getBitMap(name);
        if( image != null ){
            ImageView img = new ImageView(ctx);
            img.setImageDrawable(new BitmapDrawable(ctx.getResources(), image));
            buffer.add(new Pair<>(img, ofType));
        }
        else {
            int i = 1;

            image = getBitMap(name + "_" + String.valueOf(i));
            while(image != null){
                ImageView img = new ImageView(ctx);
                img.setImageDrawable(new BitmapDrawable(ctx.getResources(), image));
                buffer.add(new Pair<>(img, ofType));
                i++;
                image = getBitMap(name + "_" + String.valueOf(i));
            }
        }
    }

    protected Bitmap getBitMap(String name){
        int imgId = ctx.getResources().getIdentifier(name, "drawable", ctx.getPackageName());
        if(imgId == 0) return  null;
        Bitmap image = BitmapFactory.decodeResource(ctx.getResources(), imgId);
        return  image;
    }

    /*
     genere 4 vue de type straight
     ajouter les à la vue principale
     commencer les animations
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void startTheGame(){
        model3D.deleteAllRoad();
        for (int i =0 ; i< N; i++) {
            createRoad(TypeOfRoad.STRAIGHT, level);
            Log.d(TAG, "startTheGame: "+nbElements);
        }
    }


    public boolean stopGeneratingCoins(){
        return stopCoins;
    }



    //get called by the timer every duration
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void createRoad(TypeOfRoad withType, Level level){

        TypeOfRoad t =  withType;

        if(!goingToTurn){
            //on genere un nouvel element si on ne va pas tourner
            if( withType == null ){
                t = model3D.generateElement(level);
                Log.d(TAG, "createRoad: create road of type "+t.toString());

                if (t == TypeOfRoad.TURNLEFT || t == TypeOfRoad.TURN_RIGHT ){
                    Log.d(TAG,"createRoad: *************l'utilisateur va tourner************");
                    goingToTurn = true;
                    stopCoins = true;
                }
            }
            //ici on remplie le buffer
            getImages(t);
        }

        if (buffer.isEmpty()) {
            return;
        }

        //on vide le buffer
        Pair<ImageView, Type> pair = buffer.get(0);
        Frame frame = model3D.append(pair.first, pair.second);


        if (frame != null) {
            ImageView img = pair.first;
            img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

            Frame.setLayout(frame);
            roadView.addView(img);
            Frame.startAnimation(frame);
            buffer.remove(0);

            Log.d(TAG, "createRoad: append the road of type " + pair.second.toString());
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void turn (Level level) {
        model3D.deleteAllRoad();
        buffer.clear();

        goingToTurn = false;
        stopCoins = false;

        for(int i = 0 ; i< 5 ; i++) {
            createRoad(TypeOfRoad.STRAIGHT, level);
        }

        for(int i = 0 ; i< N ; i++) {
            createRoad(null, level);
        }
    }



}






