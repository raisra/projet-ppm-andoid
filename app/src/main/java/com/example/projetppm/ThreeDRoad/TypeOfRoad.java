package com.example.projetppm.ThreeDRoad;

import com.example.projetppm.Type;

public enum  TypeOfRoad implements Type {
    TURNLEFT,
    TURN_RIGHT ,
    STRAIGHT,
    TREE ,
    BRIDGE,
    PASSAGE,
    EMPTY;


    public static TypeOfRoad getRandom() {
        int r = (int) (Math.random() * values().length);
        return values()[r];
    }
}
