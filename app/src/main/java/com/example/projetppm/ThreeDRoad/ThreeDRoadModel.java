package  com.example.projetppm.ThreeDRoad;

import android.animation.AnimatorSet;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.Size;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import com.example.projetppm.Frame;
import com.example.projetppm.ModelRoad;
import com.example.projetppm.Param;
import com.example.projetppm.Type;
import com.example.projetppm.TypeOfObject;


public class ThreeDRoadModel extends ModelRoad {

    public int nbElements =0;

    public long duration ;
    public final long duration0;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ThreeDRoadModel(Param p, long duration) {
        super(p);
        this.duration = duration;
        this.duration0 = duration;
        float scale = (p.fSize - p.bSize)/p.nRows;

        for(int k=0  ; k<= super.nRows ; k++) {
            for(int i =super.iMin ; i<= super.iMax ; i++){
                //
                Frame f = getObj(i, k);

                long s = computeSpeed();
                float o = computeOpacity(super.nRows-k);
                float sc = 1 + scale/( p.bSize + scale * k );
                float x = computeXTranslation(i,k);
                float y = computeYTranslation(i,k,1);

                f.duration = s;
                f.opacity = o;
                f.scaleH = sc;
                f.scaleW = sc;
                f.yTranslate = y;
                f.xTranslate = x;
                f.setType(null);

                initAnimation(f);
            }
        }
        for(int k=0 ; k<= super.nRows ; k++) {
            Frame f = this.getObj(0, k);

            Point fr = computeTopLeft(nRows-k);
            long s = computeSpeed();
            float o = computeOpacity(nRows-k);
            float sc = 1/factor;
            float y = computeYTranslation(nRows - k);
            Size size = computeSize( nRows - k);
            f.topLeft = fr;
            f.duration = s;
            f.opacity = o;
            f.scaleH = sc;
            f.scaleW = sc;
            f.yTranslate = y;
            f.xTranslate = 0;
            f.size = size;
            f.setType(null);
            f.index = nRows - k;
            f.index_j = -1;
            initAnimation(f);
        }

    }





    public void reset() {
        super.reset();
        nbElements = 0;
        duration = duration0;
    }




    public void setDuration(long duration)  {
        this.duration = duration;
    }

    public Frame getElemAtIndex(int i) {
        if( nbElements == 0){
        return null;
    }

        return getObj(0, nRows - i );
    }



     @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
     public Point computeTopLeft(int index){
         int i = nRows - index;
         float f = linearNoCenter(0, i);
         float h = F(i-1)-F(i);

         return new Point((int)f, (int)(heightOfScreen - F(i-1)));
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Size computeSize(int index) {
        int i = nRows - index;
        float h = F(i-1)-F(i);
        return new Size((int)(size0.getWidth() * Math.pow(factor,index)),(int) h );
    }



    public long computeSpeed() {
        return  duration;
    }

    public float computeOpacity(int index) {

        return 1;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public float computeYTranslation(int index) {
        float i = nRows - index;

        float c = (factor+1) * F(i) / 2;
        float d = (factor+1) * F(i+1) / 2;

        return c-d;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public float computeXTranslation(int i, int j){
        return  linearX(i, j+1) - linearX(i, j);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public float computeYTranslation(int i, int j, float factor){
        float c = (factor+1) * F(j) / 2;
        float d = (factor+1) * F(j+1) / 2;

        return c-d;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public float computeYTranslation(int i, int j){
        return computeYTranslation(i,j, this.factor);
    }



    public TypeOfRoad generateElement(Level level){
        TypeOfRoad lastElementType ;
        if (nbElements == 0){
            lastElementType = TypeOfRoad.STRAIGHT;
        } else {
            lastElementType = (TypeOfRoad)getLastElem().getType();
        }


        TypeOfRoad nextPossibleType [] ;


        switch (lastElementType) {
            case STRAIGHT:
            //straight turn tree bridge passage
            nextPossibleType = new TypeOfRoad[]{TypeOfRoad.STRAIGHT, TypeOfRoad.BRIDGE, TypeOfRoad.PASSAGE, TypeOfRoad.TREE, TypeOfRoad.EMPTY, TypeOfRoad.TURNLEFT, TypeOfRoad.TURN_RIGHT};
            break;


            case BRIDGE:
            case PASSAGE:
            case TREE:
            case EMPTY:
            case TURN_RIGHT:
            case TURNLEFT:
            nextPossibleType = new TypeOfRoad[]{TypeOfRoad.STRAIGHT};
            break;

            default:
            nextPossibleType = new TypeOfRoad[]{TypeOfRoad.STRAIGHT};
        }


        TypeOfRoad nextElementType;

        int probability;

        switch (level) {
            case LOW:
            probability = 80;
            break;
            case MEDIUM:
            probability = 50;
            break;
            case HIGHT:
            probability = 30;
            break;

            default:
                probability=80;
        }

        int p = random(1,100);
        if (p < probability) {
            nextElementType = TypeOfRoad.STRAIGHT;
        }
        else{
            nextElementType = nextPossibleType[random(0,nextPossibleType.length)];
        }


        return nextElementType;
    }



    //ajoute à la verticiale
    public Frame append(ImageView im, Type type){
        if( (nRows - nbElements) < 0){
            return null;
        }

        Frame obj = super.addObj(im, type, 0, nRows - nbElements);
        nbElements += 1;
        return obj;
    }






    public Frame removeObject(int i , int j, Type type){
        Frame r = super.removeObject(i,j,type);
        if(r!=null && r.type != null) {
            r.view.clearAnimation();
        }
        return r;
    }

    /**
    completion : la methode à effectuer en cas de sortie d'une piece de l'ecran
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void movedown(){

        //suppression de la derniere ligne

        for(int i =0 ; i<nColumns ; i++){
            Frame obj = getObj(i, nRows);
            TypeOfRoad t = (TypeOfRoad) obj.getType();
            if (t != null ){
                obj.view.setVisibility(View.INVISIBLE);
            }
        }


        //print("decalage vers le bas")
        //decalage des cases vers le bas
        for(int i =0 ; i<nColumns ; i++) {
            int j = nRows-1;

            while(j>0)
            {
                Frame obj = getObj(i, j+1);
                Frame prevObj = getObj(i, j);

                obj.view = prevObj.view;
                obj.type = prevObj.type;

                if(obj.view!= null) {
                    setlayout(obj.view, obj.size, obj.topLeft);
                }

                ThreeDRoadViewController.startAnimation(obj);
                j -= 1;
            }
        }




        //Creation d'une ligne vide et insertion au debut de la grille
        for(int i =0 ; i<nColumns ; i++) {
            Frame obj = roadGrid[i];
            obj.view = null;
            obj.type = null;
        }


        nbElements -= 1;
        if (nbElements < 0){

            nbElements = 0;
        }
    }




    public void deleteAllRoad(){
        if (nbElements == 0 ){ return ;}

        for( int i=0 ; i<nbElements; i++) {
            removeAndDelete(0, nRows - i, TypeOfObject.any);
        }

        nbElements = 0;
    }



    public Frame getLastElem(){
        if (nbElements == 0){
            return null;
        }

        return this.getObj(0, nRows-nbElements+1);
    }



    public boolean isFirst(Frame elem)  {
        return elem.index == 0;
    }



    public int getIndex(Frame elem){
        return elem.index;
    }




    public Frame getFirst(){
        if (nbElements == 0) {
            return null;
        }
        return this.getObj(0, nRows);
    }




    public String toString(){
        if (nbElements == 0 ){
            return "ROAD IS EMPTY";
        }
        String ret = "";
        ret += "&&&&&&&&&&&&&&&&&&&&";
        for( int j= 0; j<nbElements; j++) {
            ret += "elem" +  String.valueOf(nRows - j) + ": " + String.valueOf(this.getObj(0, nRows - j).type)  + "\n";
        }
        ret += "&&&&&&&&&&&&&&&&&&&&";
                return ret;
    }



    public void initAnimation(Frame elem){

        TranslateAnimation translate = new TranslateAnimation(elem.topLeft.x, elem.topLeft.x+elem.xTranslate,
                elem.topLeft.y, elem.topLeft.y+elem.yTranslate);


        ScaleAnimation scale = new ScaleAnimation(1, elem.scaleW, 1,elem.scaleH);

        translate.setDuration(duration);
        scale.setDuration(duration);

        AnimationSet set = new AnimationSet(false);
        set.addAnimation(translate);
        set.addAnimation(scale);
        set.setDuration(duration);
        set.setFillAfter(true);
        elem.transformation = set;
    }




}