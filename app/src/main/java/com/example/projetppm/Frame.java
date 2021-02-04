package com.example.projetppm;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.Size;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Frame  {
    public ImageView view;
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










    public void setView(ImageView view) {
        this.view = view;
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





//    public void initAnimation(){
//
//        if(!moves) return;
//
//        Point center;
//        if(index_j == -1) {
//            center = new Point(frame.centerX(), frame.centerY());
//        }
//        else {
//            center = center;
//        }
//
//
//
//        ObjectAnimator translateY = ObjectAnimator.ofFloat(view.get(0), "translationY",yTranslate);
//        ObjectAnimator translateX = ObjectAnimator.ofFloat(view.get(0), "translationX",xTranslate);
//
//        ScaleAnimation scale = new ScaleAnimation(1, scaleW, 1,scaleH);
//
//        translateY.setDuration(duration);
//        translateX.setDuration(duration);
//        scale.setDuration(duration);
//
//
//        startAnimation(scale);
//        translateX.start();
//        translateY.start();
//    }

}
