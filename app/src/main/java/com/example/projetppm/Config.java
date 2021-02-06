package com.example.projetppm;

import android.os.Build;
import android.util.Size;

import androidx.annotation.RequiresApi;

import com.example.projetppm.ThreeDRoad.TypeOfRoad;

import java.util.HashMap;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Config {
    public static int DISTANCE_OF_MAGNET = 5;
    public static double  TTL_POWER = 5.0;
    public static boolean  COINS_ARE_ANIMATED = false;
    public static int  INITIAL_CHAR_POSITION  = 1;

    public static int  NB_ROWS  = 10;
    public static int  NB_COLUMNS  = 5;

    public static long  DURATION = 300;
    //the size of the uiview representing the character;
    public static Size sizeChar  = new Size(600, 1350);

    public static long JUMP_DURATION = 1000;
    public static long BLINK_DURATION  = 5000;
    public static long MOVE_DURATION = 1000;
    public static long TRANSPARENCY_DURATION = 3000;

    public static Map<TypeOfRoad, String> NAMES = new HashMap<TypeOfRoad, String>();
    static {
        NAMES.put(TypeOfRoad.STRAIGHT ,"road_pave");
        NAMES.put(TypeOfRoad.BRIDGE,"road_bridge");
        NAMES.put(TypeOfRoad.EMPTY, "road_pave");
        NAMES.put(TypeOfRoad.PASSAGE, "road_pave");
        NAMES.put(TypeOfRoad.TREE, "road_tree");
        NAMES.put(TypeOfRoad.TURN_RIGHT,"road_turn_right");
        NAMES.put(TypeOfRoad.TURNLEFT , "road_turn_left");
    }

}
