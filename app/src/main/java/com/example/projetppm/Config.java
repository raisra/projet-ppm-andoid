package com.example.projetppm;

import android.os.Build;
import android.util.Size;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Config {



    public static int DISTANCE_OF_MAGNET = 5;
    public static double  TTL_POWER = 5.0;
    public static boolean  COINS_ARE_ANIMATED = false;
    public static int  INITIAL_CHAR_POSITION  = 1;
    public static String  BACK_GROUND_IMAGE = "aboveTheSky";

    public static int  NB_ROWS  = 10;
    public static int  NB_COLUMNS  = 5;

    public static long  DURATION = 300;
    //the size of the uiview representing the character;
    public static Size sizeChar  = new Size(300, 675);







    public static long JUMP_DURATION = 1000;
    public static long BLINK_DURATION  = 5000;
    public static long MOVE_DURATION = 1000;
    public static long TRANSPARENCY_DURATION = 3000;
}
