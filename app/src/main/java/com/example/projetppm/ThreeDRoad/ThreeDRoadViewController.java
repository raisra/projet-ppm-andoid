package com.example.projetppm.ThreeDRoad;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.util.Pair;
import android.widget.ImageView;

import androidx.fragment.app.FragmentActivity;

import com.example.projetppm.Frame;
import com.example.projetppm.Type;

import java.util.ArrayList;
import java.util.Map;

public class ThreeDRoadViewController extends FragmentActivity {

    public   Map<TypeOfRoad, String> names;

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

    public ThreeDRoadViewController(Map<TypeOfRoad, String> names, long duration, ThreeDRoadModel model3D, int N){
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




    public void updateDuration(long d){
        duration = d;
    }

    private String getName(Type accordingTo ){
        return names.get(accordingTo);
    }

    private Bitmap getBitMap(String name){
        int imgId = getResources().getIdentifier(name, "drawable", getPackageName());
        Bitmap image = BitmapFactory.decodeResource(getResources(), imgId);
        return  image;
    }

    public void getImages(Type ofType){
        String name = getName(ofType);

        Bitmap image = getBitMap(name);
        if( image != null ){
            ImageView img = new ImageView(getBaseContext());
            img.setBackground(new BitmapDrawable(getResources(), image));
            buffer.add(new Pair<>(img, ofType));
        }
        else {
            int i = 1;

            image = getBitMap(name + "_" + String.valueOf(i));
            while( image != null){
                ImageView img = new ImageView(getBaseContext());
                img.setBackground(new BitmapDrawable(getResources(), image));
                buffer.add(new Pair<>(img, ofType));
                i++;
            }
        }
    }


    /*
     genere 4 vue de type straight
     ajouter les à la vue principale
     commencer les animations
     */
    public void startTheGame(){
        model3D.deleteAllRoad();
        for (int i =0 ; i< N; i++) {
            createRoad(TypeOfRoad.STRAIGHT, level);
        }
    }


    public boolean stopGeneratingCoins(){
        return stopCoins;
    }



    //get called by the timer every duration
    public void createRoad(TypeOfRoad withType, Level level){

        TypeOfRoad t =  withType;

        if(!goingToTurn){
            //on genere un nouvel element si on ne va pas tourner
            if( withType == null ){
                t = model3D.generateElement(level);

                if (t == TypeOfRoad.TURNLEFT || t == TypeOfRoad.TURN_RIGHT ){
                    Log.d(TAG,"------------lutilisateur va tourner");
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
          //  model3D.initAnimation(frame);
           // model3D.startAnimation(frame);

            buffer.remove(0);
        }
    }


    public void turn (Level level) {
        model3D.deleteAllRoad();
        buffer.clear();

        goingToTurn = false;
        stopCoins = false;

        for(int i = 1 ; i< 5 ; i++) {
            createRoad(TypeOfRoad.STRAIGHT, level);
        }

        for(int i = 1 ; i< N ; i++) {
            createRoad(null, level);
        }
    }


    public void setDuration(long duration) {
        this.duration = duration;
    }
}






