package com.example.projetppm;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Frame  {
    public ImageView view;
    public Size size;
    public Point topLeft;
    public float yTranslate = 0;
    public float xTranslate = 0;
    public long duration ;
    public float scaleH = 0 ;
    public float scaleW = 0 ;
    public int index  = 0 ;
    public int index_j = 0 ;
    public float opacity  = 0 ;
    public Type type ;
    public Animation transformation ;

    public void setView(ImageView view) {
        this.view = view;
    }

    public void setTopLeft(Point topLeft) {this.topLeft = topLeft;}

    public Point getTopLeft() { return topLeft; }

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


    @SuppressLint("NewApi")
    public static void setLayout(Frame f) {
        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(f.size.getWidth(), f.size.getHeight());
        layoutParams.setMargins(0, f.topLeft.y,0, 0);

        f.view.setLayoutParams(layoutParams);




        // f.view.layout(f.topLeft.x, f.topLeft.y , f.topLeft.x + f.size.getWidth() , f.topLeft.y + f.size.getHeight());

        Log.d("SETLAYOUT", "setlayout elem: (" +f.type + "," + f.index+")" + f.topLeft.x + " " + f.topLeft.y + " " + " " + f.size.getWidth() +  " " + f.size.getHeight());
    }


    @SuppressLint("NewApi")
    public static void setLayout(View v, Size size, Point topLeft) {
        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(size.getWidth(), size.getHeight());
        layoutParams.setMargins(topLeft.x, topLeft.y,0, 0);
        v.setLayoutParams(layoutParams);




         v.layout(topLeft.x, topLeft.y , topLeft.x + size.getWidth() , topLeft.y + size.getHeight());

        Log.d("SETLAYOUT", "setlayout: " + topLeft.x + " " + topLeft.y + " " + " " + size.getWidth() +  " " + size.getHeight());
    }



    private static int cc = 0;

    public static void startAnimation(Frame elem){
        if(elem.view==null){
            return;
        }
        cc++;
        Log.d("ANIM " + cc + " of elem " + elem.type," elem: " + elem.index + " trans x:" + elem.xTranslate + " trans y: " + elem.yTranslate + " scale: " + elem.scaleW + "," + elem.scaleH );
        elem.view.clearAnimation();
    //    elem.view.startAnimation(elem.transformation);
    }




    public static Bitmap getBitMap(Context ctx, String name){
        int imgId = ctx.getResources().getIdentifier(name, "drawable", ctx.getPackageName());
        if(imgId == 0) return  null;
        BitmapFactory.Options op = new BitmapFactory.Options();
     //   op.inPreferredConfig  = Bitmap.Config.RGB_565;
        op.inSampleSize = 5;

        Bitmap image = BitmapFactory.decodeResource(ctx.getResources(), imgId, op);
        return  image;
    }


}
