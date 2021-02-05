package com.example.projetppm;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.projetppm.ThreeDRoad.*;


import java.util.HashMap;
import java.util.Map;

import static com.example.projetppm.TypeOfObject.*;


@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class GameViewController extends Activity implements  Runnable{

        public  String TAG = "GAMEVIEWCONTROLLER";

        public Map<TypeOfRoad, String> NAMES  ;

        public Size sizeIm = new Size(400, 100);
        public static float alpha = (float) 75.96;
        public static float factor  = (float) ( 309.96/398.52);

        public boolean SoundOnOff = true;

        public Thread thread ;
        public long duration ;

        public GameView gv ;
        public HumanInterfaceView hv;
        public ThreeDRoadModel modelRoad;
        public ThreeDRoadViewController threeDRoadVC  ;

        public boolean gameIsStoped = true;

        //the position of the character on the screen grid
        public Point thePosition  ;
        public ImageView backGround ;

//        public SoundManager soundManager;
//        public GestureManager gestureManager;
//        public MotionManager motionManager ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "view on create");
        setContentView(R.layout.game_view);

        gv = (GameView) findViewById(R.id.game_view_id);
        hv = (HumanInterfaceView) findViewById(R.id.human_interface_view_id);


        Log.d(TAG, hv.toString());

        NAMES = new HashMap<TypeOfRoad, String>();
        NAMES.put(TypeOfRoad.STRAIGHT ,"pave");
        NAMES.put(TypeOfRoad.BRIDGE,"bridge");
        NAMES.put(TypeOfRoad.EMPTY, "pave");
        NAMES.put(TypeOfRoad.PASSAGE, "pave");
        NAMES.put(TypeOfRoad.TREE, "tree");
        NAMES.put(TypeOfRoad.TURN_RIGHT,"turnRight");
        NAMES.put(TypeOfRoad.TURNLEFT , "turnLeft");


        duration = Config.DURATION;
        //init du model 3D

        float r = (float)sizeIm.getHeight()/(float)sizeIm.getWidth();
        Point p = new Point();
        getWindowManager().getDefaultDisplay().getSize(p);

        sizeIm = new Size(p.x, (int) (sizeIm.getWidth() * r));
        float D = (float) (sizeIm.getWidth() * Math.pow(factor, Config.NB_ROWS));
        Param param = new Param(Config.NB_ROWS, Config.NB_COLUMNS, p.x, D, 5, 50, sizeIm, factor, p.y);

        modelRoad = new ThreeDRoadModel(param, duration);

        //initialise la position du persinnage au mileu de l'ecran
        thePosition = new Point((modelRoad.iMax + modelRoad.iMin)/2 , Config.NB_ROWS - Config.INITIAL_CHAR_POSITION);
        Point posOfCharacter = modelRoad.getCenter(thePosition.x, thePosition.y);


        hv.init();
        gv.init(duration,posOfCharacter, Config.sizeChar);

        threeDRoadVC = new ThreeDRoadViewController( NAMES, duration, modelRoad, Config.NB_ROWS);
        threeDRoadVC.init(this, (RelativeLayout) findViewById(R.id.road_view));
    }


    @Override
    protected void onStart() {
        super.onStart();
        
        hv.animationForNumber();

        startTheGame();
        threeDRoadVC.startTheGame();
    }



    @Override
    public void run() {
        while(!gameIsStoped) {
           // updateView();
           // gv.draw();
            sleep();
        }
    }




    public void sleep(){
        try {
            Thread.sleep((long) duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void  startTheGame(){

            gv.showCharacter();
            gv.startAnimation();

            hv.pauseButton.setVisibility(View.INVISIBLE);
            hv.messageButton.setVisibility(View.VISIBLE);
            hv.startButton.setVisibility(View.INVISIBLE);
            gv.objectsView.setVisibility(View.VISIBLE);

            gameIsStoped = false;
            thread = new Thread(this);
            thread.start();
        }

//        @objc func startGame() {
//            startTheGame()
//        }


        public void  pauseGame() throws InterruptedException {
            //TODO SAUVEGARDER LE niveau et la valeur du timer pr elancer le timer a la meme frequence
            if(!gameIsStoped){
                gameIsStoped = true;
                thread.join();
                gv.stopAnimation();
                gv.objectsView.setVisibility(View.INVISIBLE);

                hv.pauseButton.setVisibility(View.VISIBLE);
            }
            else {
                //restart the game
                thread.start();
                gameIsStoped = false;
                hv.pauseButton.setVisibility(View.INVISIBLE);
                gv.objectsView.setVisibility(View.VISIBLE);

            }
        }



        //display the message view
        public void seeMessage(){
         //   Intent intent = new Intent(getBaseContext(), Message);
        }


    @Override
    protected void onPause() {
        super.onPause();
        try {
            pauseGame();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * Init the view with the bitmap name
     * @param view the view to initialize
     * @param name name of the bitmap
     * @param animated
     */
    public boolean initView(ImageView view , String name, boolean animated) {

        if (!animated) {
            Bitmap img = getBitMap(name);
            if (img != null) {
                view.setBackground(new BitmapDrawable(getResources(), img));
                return true;
            } else {
                return false;
            }

        } else{
            int imgId = getResources().getIdentifier("animation_"+"name", "drawable", getPackageName());
            if (imgId == 0) {
                return false;
            } else {
                view.setBackgroundResource(imgId);
                return true;
            }
        }


    }


    private Bitmap getBitMap(String name){
        int imgId = getResources().getIdentifier(name, "drawable", getPackageName());
        Bitmap image = BitmapFactory.decodeResource(getResources(), imgId);
        return  image;
    }



        /**
         genere aleatoirement un objet qur le parcours
         */
        public void  createObject(){
            //on commence par générer un objet grace au model
            TypeOfObject objs[] = modelRoad.generateNewObject(0);


            for ( int colonne=0; colonne<modelRoad.iMax ; colonne++){
                //le type de l'objet créé
                TypeOfObject type= objs[colonne];


                boolean animated = false;
                String name = null; //the name of the file cointaining the object to draw

                switch(type) {
                    case coin:
                        //le model a créé une piece
                        animated = Config.COINS_ARE_ANIMATED;
                        name = "coin";
                        break;

                    case magnet :
                        //le model a créé un aimant
                        animated = false;
                        name = "magnet";
                        break;

                    case coinx2 :
                        //le model a créé une piece+2
                        animated = Config.COINS_ARE_ANIMATED;
                        name = "coin-x2";
                        break;

                    case coinx5 :
                        //le model a créé une piece+2
                        animated = Config.COINS_ARE_ANIMATED;
                        name = "coin-x5";
                        break;

                    case any : continue;
                    case empty: continue;
                    default: continue;
                }

                //le nouvel objet est initialisée avec l'image qui lui correspond
                ImageView newObject = new ImageView(getBaseContext());
                newObject.setVisibility(View.VISIBLE);
                initView(newObject,name, animated);

                gv.objectsView.addView(newObject);
                //TODO sendtoback ne fonctionne pas pourquoi???????????
               // newObject.sendToBack();


                Frame f = modelRoad.addObj(newObject, type, colonne + modelRoad.iMin , 0);
                modelRoad.initAnimation(f);
                modelRoad.startAnimation(f);
            }
        }



        //dictionnaire contenant le Time to live de chaque pouvoir qui a été enclenché
        Map<TypeOfRoad, Long> TTL = new HashMap<>();

        /**
         cette fonction créé  un objet
         vérifie si des pouvoirs sont en cours d'execution et met à jour leur timers respectifs
         */
        int nbOfTurn  = 0;
        Level level = Level.LOW;

        long MALUS_DURATION  = 2l;
        boolean malus  = false; //indique si le personnage s'est cogné

        long timerJump = 0l;
        long timerBlink = 0l;



        public void  updateView() {
            //on véerifie si le personnage a sauté
            if (wantToJump) {
                timerJump -= duration;
                if (timerJump < 0 ){
                    timerJump = 0l;
                    wantToJump = false;
                    gv.animationForRunning();
                }
            }

            if(malus) {
                timerBlink -= duration;
                if (timerBlink < 0) {
                    timerBlink = 0;
                    malus = false;
                }
            }


            //on vérifie sur quel type de route se trouve le personnage
            TypeOfRoad t = (TypeOfRoad)modelRoad.getElemAtIndex(Config.INITIAL_CHAR_POSITION).type;

            if ((t == TypeOfRoad.TURNLEFT && !wantToTurnLeft) || (t == TypeOfRoad.TURN_RIGHT && !wantToTurnRight)) {
                //le joueur a perdu
               // gameOver()
                return;
            }


            if( t == TypeOfRoad.TREE && !wantToJump && malus ){
                //le personnage s'est cogné deux fois d'affiler
                //gameOver();
                return;
            }

            if ((t == TypeOfRoad.TURNLEFT && wantToTurnLeft) || (t == TypeOfRoad.TURN_RIGHT && wantToTurnRight)){

                nbOfTurn += 1;

                threeDRoadVC.turn(level);
                wantToTurnLeft = false;
                wantToTurnRight = false;

                //on invalide le timer et on en rreceer un autre plus rapide
                thread.interrupt();
                duration = duration - 10;
                threeDRoadVC.setDuration(duration);

                thread = new Thread();
                thread.start();

                return;
            }


            if( t == TypeOfRoad.TREE && !wantToJump) {
                malus = true;
                gv.animateBlink();
                timerBlink = Config.BLINK_DURATION;
            }





            TypeOfRoad lastElemType = (TypeOfRoad)modelRoad.getLastElem().type;
            if (!threeDRoadVC.stopGeneratingCoins() && (lastElemType == TypeOfRoad.STRAIGHT || lastElemType == TypeOfRoad.BRIDGE)){
                createObject();
            }

            modelRoad.movedown();

            threeDRoadVC.createRoad(null, level);

            //vérifie s'il y a des pouvoirs en cours d'execution
            //met à jour le timer
          //  handlePower();


            //tentative de suppression de l'objet situé devant le personnage
            //suppression de l'objet située 1 cases devant
            Frame obj = modelRoad.removeObject(thePosition.x, thePosition.y, any);

            //si aucun n'objet n'est devant le personnage rien à faire
            if (obj == null){
                return;
            }


            //les coordonnées du point vers lesquels renvoyer les pieces
           // Point p;
                    //callback a executer qd le personnage rencontre un object
           // var cb : ((Bool) -> ())?
            switch ((TypeOfObject)obj.type) {

                case coin:
                    //le personnage attrape une piece

                    hv.addScore(1);
                   // p = hv.scoreLabel.center
                   // cb = {(Bool) in  obj.view?.isHidden = true }
                    break;

                case coinx2:
                    //le personnage attrape une piece double
                    hv.addScore(2);
                  //  p = hv.scoreLabel.center;
                  //  cb = {(Bool) in  obj.view?.isHidden = true }
                    break;

                case coinx5:
                    hv.addScore(5);
                   // p = hv.scoreLabel.center
                   // cb = {(Bool) in  obj.view?.isHidden = true }
                    break;
                //TODO FACTORISER LE CODE PRECEDENT



                case magnet:
                    //le personnage attrape un aimant
                    //l'icone de l'aimant est déplacée au point de coordonnées p
                  //  p = hv.powerAnchor;


                    //a la fin de l'animation, ajout de l'icone du pouvoir à l'emplacement réservé dans HumanInterface
//                    cb = {(Bool) in
//                            //declenchement du timer pendant 10s
//                            self.TTL.append((magnet, TTL_POWER))
                    //self.hv.addPower(powerView: obj.view!, duration: TTL_POWER)
                    //print("add the power now")
                //}

                    break;


                default:
                    break;
            }

            //deplacement de l'objet si les coordonnées p sont non nuls
            //moveObjToPoint(obj.view!, point: p, withDuration: 1, options: .transitionCurlUp, cb : cb)

            wantToTurnLeft = false;
            wantToTurnRight = false;

        }




//        func handlePower(){
//
//            //si un aucun poucoir n'est en cours d'execution on ne fait rien
//            if(TTL.isEmpty) {return}
//
//            var i = 0
//            while i<TTL.count  {
//                let (power, ttl) = TTL.first!
//                //si le pouvoir a fini de s'executer
//                if ttl <= 0 {
//                    print("le pouvoir \(power) est terminé")
//                    //un pouvoir est enclenché
//                    TTL.remove(at: 0)
//
//                    continue
//                }
//
//                let timeToLive = ttl - duration!
//                        TTL[0].1 = timeToLive
//                aa += 1
//
//                switch power {
//
//                    case magnet:
//                        //attirer les pieces situé dans le voisinage
//                        //on commence par retirer les pieces concernées du chemin
//
//
//                        for i in 0..<modelRoad.nColumns {
//                        for neighborhood in 1 ... DISTANCE_OF_MAGNET {
//                            //on tente de retirer les pieces situées dans le voisinage du personnage
//                            let coin = modelRoad.removeObject(i: i, j: thePosition.1 - neighborhood, type: coin).view
//
//                            //si on trouve une piece
//                            if coin != nil {
//                                //on deplace la piece vers le personnage
//                                moveObjToPoint(coin!,
//                                        point: gv.character.center,
//                                        withDuration: 1,
//                                        options: .curveEaseIn,
//                                        cb : {_ in
//                                    coin!.isHidden = true
//                                    self.coins.insert(coin!)
//                                }
//                            )
//
//                                //TODO faire un mouvement plus naturel
//                                //Peut etre animation suivant une courbe de bezier
//                            }
//                        }
//                    }
//                    break
//
//                    case transparency:
//                        //le personnage devient mi-transparent
//                        //il devient capable de traverser les obstacles
//                        gv.character.alpha = 0.5
//                        break
//
//                    default:
//                        print("handlePower : ne devrait jamais safficher")
//                }
//
//                i += 1
//            }
//
//        }






        boolean wantToTurnLeft  = false;
        boolean wantToTurnRight  = false;
        boolean wantToJump = false;


        public void jump() {
            if (!wantToJump) {
                timerJump = Config.JUMP_DURATION;
                wantToJump = true;

                gv.animationForJump();
            }
        }





}




