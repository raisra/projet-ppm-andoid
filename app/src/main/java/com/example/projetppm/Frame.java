package com.example.projetppm;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.Size;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class ObjectView extends  androidx.appcompat.widget.AppCompatImageView {
    ArrayList<BitmapDrawable> view;
    public Size size;
    public Point center;
    public Rect frame;

    public float yTranslate = 0;
    public float xTranslate = 0;
    public long duration ;
    public float scaleH = 0 ;
    public float scaleW = 0 ;
    public int index  = 0 ;
    public int index_j = 0 ;
    public float opacity  = 0 ;

    public Type type ;

    public android.animation.Animator transformation ;

    /**
     * indiquates if the object is animated
     */
    boolean animated;

    /**
     * indiquates if the object moves on the screen
     */
    boolean moves ;


    public ObjectView(Context context, String name, boolean moves, boolean animated) {
        super(context);
        this.moves = moves;
        this.animated = animated;

        Bitmap img = getBitMap(name);

        if(img!=null){
            view = new ArrayList<>();
            view.add(new BitmapDrawable(getResources(),img));
            animated = false;
        }
        else {
            //check if it is an animated bitmap
            int i = 1;

            img = getBitMap(name + "_" + String.valueOf(i));
            while( img != null){
                view.add(new BitmapDrawable(getResources(),img));
                i++;
            }

            animated = true;
        }

        if(moves){
            setBackground(view.get(0));
        }



    }

    public ObjectView(@NonNull Context context) {
        super(context);
    }

    private Bitmap getBitMap(String name){
        int imgId = getResources().getIdentifier(name, "drawable", getPackageName());
        Bitmap image = BitmapFactory.decodeResource(getResources(), imgId);
        return  image;
    }


    public void setFrame(Rect frame) {
        this.frame = frame;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public void setYTranslation(Float d)  {
        this.yTranslate = d;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type t){ this.type = t; }





    public void initAnimation(){

        if(!moves) return;

        Point center;
        if(index_j == -1) {
            center = new Point(frame.centerX(), frame.centerY());
        }


        ObjectAnimator translateY = ObjectAnimator.ofFloat(view.get(0), "translationY",yTranslate);
        ObjectAnimator translateX = ObjectAnimator.ofFloat(view.get(0), "translationX",xTranslate);

        ScaleAnimation scale = new ScaleAnimation(1, scaleW, 1,scaleH);

        translateY.setDuration(duration);
        translateX.setDuration(duration);
        scale.setDuration(duration);


        startAnimation(scale);
        translateX.start();
        translateY.start();
    }

}
