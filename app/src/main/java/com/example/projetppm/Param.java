package com.example.projetppm;

import android.util.Size;

public class Param {
    public int nRows;
    public int nColumns;
    public float W;

    public float D;
    public float bSize;
    public float fSize;

    public Size size0;
    public float factor;

    public int heightOfScreen;

    public Param(int nRows, int nColumns, float W, float D, float bSize, float fSize, Size size0, float factor, int heightOfScreen) {
        this.nRows = nRows;
        this.nColumns = nColumns;
        this.W = W;

        this.D = D;
        this.bSize = bSize;
        this.fSize = fSize;

        this.size0 = size0;
        this.factor = factor;

        this.heightOfScreen = heightOfScreen;
    }
}
