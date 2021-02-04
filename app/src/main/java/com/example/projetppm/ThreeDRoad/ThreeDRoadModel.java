package  com.example.projetppm.ThreeDRoad;

import android.animation.ObjectAnimator;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.Size;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import com.example.projetppm.Frame;
import com.example.projetppm.ModelRoad;
import com.example.projetppm.Param;
import com.example.projetppm.Type;
import com.example.projetppm.TypeOfObject;


enum Level{
    LOW,
    MEDIUM,
    HIGHT,
}


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

                double s = computeSpeed();
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
                f.setType(TypeOfRoad.EMPTY);

                initAnimation(f);
            }
        }
        for(int k=0 ; k<= super.nRows ; k++) {
            Frame f = this.getObj(0, k);

            Rect fr = computeFrame(nRows-k);
            Double s = computeSpeed();
            float o = computeOpacity(nRows-k);
            float sc = 1/factor;
            float y = computeYTranslation(nRows - k);
            Size size = computeSize( nRows - k);
            f.frame = fr;
            f.duration = s;
            f.opacity = o;
            f.scaleH = sc;
            f.scaleW = sc;
            f.yTranslate = y;
            f.xTranslate = 0;
            f.size = size;
            f.setType(TypeOfRoad.EMPTY);
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

    public Point getCenter(int index) {
        return getElemAtIndex(nRows - index).center;
    }

     @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
     public Rect computeFrame(int index){

         int i = nRows - index;
         float f = linearNoCenter(0, i);
         float h = F(i-1)-F(i);


         return new Rect((int)f, (int)(heightOfScreen - F(i-1)), (int)(f + size0.getWidth()) , (int)(heightOfScreen - F(i)));


    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Size computeSize(int index) {
        int i = nRows - index;
        float h = F(i-1)-F(i);
        return new Size((int)(size0.getWidth() * Math.pow(factor,index)),(int) h );
    }



    public double computeSpeed() {
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



    public Type generateElement(Level level){
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
            nextPossibleType = new TypeOfRoad[]{TypeOfRoad.STRAIGHT};
            break;
            case PASSAGE:
                nextPossibleType = new TypeOfRoad[]{TypeOfRoad.STRAIGHT};
                break;
            case TREE:
                nextPossibleType = new TypeOfRoad[]{TypeOfRoad.STRAIGHT};
                break;
            case EMPTY:
                nextPossibleType = new TypeOfRoad[]{TypeOfRoad.STRAIGHT};
                break;
            case TURN_RIGHT:
                nextPossibleType = new TypeOfRoad[]{TypeOfRoad.STRAIGHT};
                break;
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
        startAnimation(obj);
        nbElements += 1;

        return obj;
    }


    public void startAnimation(Frame elem){
       // elem.view?.layer.add(elem.transformation!, forKey: "translationAndResize")
    }



    public void initAnimation(Frame elem){

        Point center;
        if(elem.index_j == -1) {
            center = new Point(elem.frame.centerX(), elem.frame.centerY());
        }
        else {
            center = elem.center;
        }


        ObjectAnimator translateY = ObjectAnimator.ofFloat(elem.view, "translationY",elem.yTranslate);
        ObjectAnimator translateX = ObjectAnimator.ofFloat(elem.view, "translationX",elem.xTranslate);

        ScaleAnimation scale = new ScaleAnimation(1, elem.scaleW, 1, elem.scaleH);

        translateY.setDuration(duration);
        translateX.setDuration(duration);
        scale.setDuration(duration);


        elem.view.startAnimation(scale);
        translateX.start();
        translateY.start();
    }

    public Frame removeObject(int i , int j, Type type){
        Frame r = super.removeObject(i,j,type);
        r.view.clearAnimation();

        return r;
    }

    /**
    completion : la methode à effectuer en cas de sortie d'une piece de l'ecran
     */
    public void movedown(){

        //suppression de la derniere ligne

        for(int i =0 ; i<nColumns ; i++){
            Frame obj = getObj(i, nRows);
            if (obj.getType() != TypeOfRoad.EMPTY ){
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

                View view = obj.view;

                if (i == 0){
                    view.layout(obj.frame.left, obj.frame.top, obj.frame.right, obj.frame.bottom);
                } else {
                    ModelRoad.setlayout(view,obj.size,obj.center);
                }

                startAnimation(obj);
                //view!.layer.addSublayer(viewToAnimate.layer)

                j -= 1;
            }
        }




        //Creation d'une ligne vide et insertion au debut de la grille
        for(int i =0 ; i<nColumns ; i++) {
            Frame obj = roadGrid[i];
            obj.view = null;
            obj.type = TypeOfRoad.EMPTY;
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

}