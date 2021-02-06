package com.example.projetppm;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.os.Build;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;

import java.util.Arrays;


public class ModelRoad {
    protected float iN[];
    protected int iMin;
    protected int iMax;
    protected int nRows;
    protected int nColumns;

    protected float D = 0;
    protected float d = 0;

    protected float W;
    protected float H;

    protected float bSize = 10;
    protected float fSize = 100;

    protected Frame roadGrid[];

    protected int repeatCount = 0;
    protected TypeOfObject prevRandomValue[];
    protected int prevR = 0;

    protected Size size0;
    protected float factor;

    protected int heightOfScreen;




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ModelRoad(Param p) {
        this.nRows = p.nRows;
        this.nColumns = p.nColumns;

        this.W = p.W;

        this.D = p.D;
        this.d = this.D / p.nColumns;
        this.bSize = p.bSize;
        this.fSize = p.fSize;

        float i0 = (p.W - p.D) / 2;
        this.iN = new float[p.nColumns];

        for (int i = 0; i < nColumns; i++) {
            iN[i] = i0 + d * i;
        }

        this.iMin = 1;
        this.iMax = nColumns - 2;

        this.size0 = p.size0;
        this.factor = p.factor;

        this.heightOfScreen = p.heightOfScreen;

        H = (float) (size0.getHeight() * (1 - Math.pow(factor, p.nRows)) / (1 - factor));

        roadGrid = new Frame[(nRows + 1) * nColumns];

        /**Utile pour la génération des pieces */
        repeatCount = 0;
        prevRandomValue = new TypeOfObject[nColumns];
        Arrays.fill(prevRandomValue, null);// LE REMPLIR DE EMPTY

        prevR = 0;


        for (int k = 0; k <= nRows; k++) {
            for (int i = 0; i < nColumns; i++) {
                // print("k:\(k) i:\(i) center:\(x)" )
                Size s = new Size((int) G(k), (int) G(k));
                float x = linearX(i, k) - s.getWidth()/2;;
                float y = heightOfScreen - F(k) - s.getHeight()/2;

                Point o = new Point((int) x, (int) y);
                Frame f = new Frame();

                f.index = i;
                f.index_j = k;

                f.setSize(s);
                f.setTopLeft(o);
                f.setType(null);
                roadGrid[nColumns * k + i] = f;
            }
        }


    }


    public void reset() {
        for (int k = 0; k <= nRows; k++) {
            for (int i = 0; i < nColumns; i++) {
                removeAndDelete(i, k, TypeOfObject.any);
            }
        }
    }


    public Frame getObj(int i, int j) {
        return roadGrid[nColumns * j + i];
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public float linearNoCenter(int i, int k) {
        float x = (iN[i] - i * W / nColumns) * (F(k) / H) + i * W / nColumns;
        return x;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public float linearX(int i, int k) {
        return (float) (linearNoCenter(i, k) + W / (2 * nColumns) + F(k) * (d - W / nColumns) / (2.0 * H));
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public float F(float k) {
        float f = factor;
        float h0 = size0.getHeight();
        float a = (float) (H - h0 * Math.pow(f, nRows - 1) * (Math.pow(1 / f, k) - 1) / (1 / f - 1));
        return a;
    }


    public float G(float k) {
        return bSize + (fSize - bSize) * k / nRows;
    }

//    public Bitmap getObject(int i, int j) {
//        Frame obj = getObj(i, j);
//        return obj.view;
//    }


    /**
     * completion : la methode à effectuer en cas de sortie d'une piece de l'ecran
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void movedown() {
        //suppression de la derniere ligne
        for (int i = iMin; i <= iMax; i++) {
            Frame obj = getObj(i, nRows);
            if (obj.getType() != null) obj.view.setVisibility(View.INVISIBLE);
        }

        //decalage des case vers le bas
        for (int i = iMin; i <= iMax; i++) {
            int j = nRows - 1;
            while (j >= 0) {
                Frame obj = getObj(i, j + 1);
                Frame prevObj = getObj(i, j);

                obj.view = prevObj.view;
                obj.type = prevObj.type;

                View view = obj.view;
                setlayout(view, obj.size, obj.topLeft);

                j -= 1;
            }
        }

        //Creation d'une ligne vide et insertion au debut de la grille
        for (int i = iMin; i <= iMax; i++) {
            Frame obj = roadGrid[i];
            obj.view = null;
            obj.type = null;
        }
    }


    @SuppressLint("NewApi")
    public static void setlayout(View v, Size size, Point topLeft) {
        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(size.getWidth(), size.getHeight());
        layoutParams.setMargins(topLeft.x, topLeft.y,0, 0);

        v.setLayoutParams(layoutParams);
        // v.layout(topLeft.x, topLeft.y , topLeft.x + size.getWidth() , topLeft.y + size.getHeight());

        Log.d("SETLAYOUT", "setlayout: " + topLeft.x + " " + topLeft.y + " " + " " + size.getWidth() +  " " + size.getHeight());
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Point getCenter(int i, int j) {
        Frame f = getObj(i,j);
        float x = linearX(i, j) ;
        float y = heightOfScreen - F(j) ;

        return  new Point((int)x,(int)y);
    }

    public Frame addObj(ImageView img, Type type, int i, int j) {
        Frame obj = getObj(i, j);

        obj.setView(img);
        obj.setType(type);

        return obj;
    }

    public void removeAndDelete(int i, int j, Type type) {
        Frame r = removeObject(i, j, type);
        r.view.setVisibility(View.INVISIBLE);
        r.view = null;
    }


    public Frame removeObject(int i, int j, Type type) {
        //le personnage a sauté
        if (i == 42) {
            return null;
        }

        Frame obj = getObj(i, j);
        if (obj.getType() == type || type == TypeOfObject.any) {
            obj.setType(null);
            obj.view = null;
            return obj;
        }

        return null;
    }


    public int random(int lower, int upper) {
        return (int) (Math.random() * (upper - lower)) + lower;
    }

    /**
     * Generate one or two coins
     *
     * @return
     */
    public TypeOfObject[] generateCoin() {
        if (repeatCount > 0) {
            repeatCount -= 1;
            return prevRandomValue;
        }

        if (repeatCount == 0) {
            repeatCount = random(0, 5);
        }


        prevRandomValue = new TypeOfObject[nColumns - 2];
        Arrays.fill(prevRandomValue, null);


        int r = random(1, nColumns - 2);
        int r1 = (r + prevR) % (nColumns - 2);
        prevRandomValue[r1] = TypeOfObject.coin;
        prevR = r1;
        repeatCount -= 1;
        return prevRandomValue;
    }


    /**
     * Generate a New Object
     *
     * @param level
     * @return
     */
    public TypeOfObject[] generateNewObject(int level) {
        TypeOfObject[] coins = generateCoin();
        int p = random(1, 100);

        if (p < 90) {
            //nothing to do
            return coins;
        } else {
            p = random(1, 100);
            TypeOfObject[] objPos = new TypeOfObject[iMax];
            Arrays.fill(objPos, null);

            int r1 = random(0, iMax - 1);
            TypeOfObject t;
            if (p > 90) {
                //ici evenement rare
                //on affiche un bonus ou autre evenement rare
                //0.3*0.3 de chance d'arriver la soit 3 chances sur 100
                t = TypeOfObject.getRandomPower();
                objPos[r1] = t;
            }

            return objPos;
        }

    }





    public  Point getTopLeft(int i, int k){
        return getObj(i,k).getTopLeft();
    }
}




