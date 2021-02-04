package com.example.projetppm;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Size;
import android.widget.ImageView;

public class Frame {
    public ImageView view;
    public Size size;
    public Point center;
    public Rect frame;

    public float yTranslate = 0;
    public float xTranslate = 0;
    public double duration ;
    public float scaleH = 0 ;
    public float scaleW = 0 ;
    public int index  = 0 ;
    public int index_j = 0 ;
    public float opacity  = 0 ;

    public Type type ;

    public android.animation.Animator transformation ;

    public void setObj(ImageView view){
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
}
