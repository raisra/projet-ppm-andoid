package com.example.projetppm;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import java.util.ArrayList;

/**
 * This class represents objects to be displayed.
 * An object can be of Type TypeOfObject or TypeOfRoad
 */
public class ObjectView extends View {

    /**
     * contains all the informations needed to display the object ( Position, size ... etc)
     */
    Frame frame;


    /**
     * Contains the bitmap to display
     */




    /**
     *
     * @param context
     * @param name the name of the resource to display
     */
    public ObjectView(Context context, String name, boolean moves, boolean animated) {
        super(context);
        this.moves = moves;
        this.animated = animated;

        Bitmap img = getBitMap(name);

        if(img!=null){
                bitmap = new ArrayList<>();
                bitmap.add(img);
                animated = false;
        }
        else {
            //check if it is an animated bitmap
                int i = 1;

                img = getBitMap(name + "_" + String.valueOf(i));
                while( img != null){
                    bitmap.add(img);
                    i++;
                }

                animated = true;
            }



        }


    public void setFrame(Frame frame) {
        this.frame = frame;
    }


    public Frame getFrame() {
        return frame;
    }





}
