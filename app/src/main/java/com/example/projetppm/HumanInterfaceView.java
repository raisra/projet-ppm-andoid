package com.example.projetppm

//
//  HumanInterface.swift
//  projet-ppm
//
//  Description : Boutton pause, score, message, calcul du score.
//


import android.content.Context;
import android.view.View;

public class HumanInterfaceView extends View {

    let messageButton = UIButton()
    let scoreLabel = UILabel()

    let pauseButton = UIButton()
    let counterView = UIImageView()

    let startButton = UIButton()

    //nombre de pieces réscoltées
    var score : Int = 0

    let h = UIScreen.main.bounds.height
    let w = UIScreen.main.bounds.width


    //le nombre de pouvoirs
    var nbPower : Int = 0
    var powerAnchor : CGPoint = CGPoint(x: 0, y: 200)
    let sizeOfPowerIcons : CGSize  = CGSize(width: 20, height: 20)

    public HumanInterfaceView(Context context) {
        super(context);
    }


    /**
    bouton pour simuler le deplacement du character
     */

    static func setButton(title: String, posx: CGFloat, posy : CGFloat) -> UIButton {
        let b = UIButton()
        b.frame = CGRect(x: posx, y: posy, width: 50, height: 50)
        b.setTitle(title, for: .normal)
        b.setTitleColor(.black, for: .normal)

        return b
    }


    override init(frame: CGRect) {
        super.init(frame: frame)
        pauseButton.isHidden = true
        pauseButton.setImage(UIImage(named: "pauseButton"), for: .normal)
        pauseButton.addTarget(self.superview,
                action: #selector(GameViewController.pauseGame),
        for: .touchUpInside)

        startButton.isHidden = true
        startButton.setImage(UIImage(named: "startButton"), for: .normal)
        startButton.addTarget(self.superview,
                action: #selector(GameViewController.startGame),
        for: .touchUpInside)


        scoreLabel.text = "0"
        scoreLabel.font = UIFont.boldSystemFont(ofSize: 18.0)
        scoreLabel.textColor = .black

                messageButton.setImage(UIImage(named: "message"), for: .normal)
        messageButton.addTarget(self.superview, action: #selector(GameViewController.seeMessage), for: .touchUpInside)
        messageButton.isHidden = true


        addSubview(pauseButton)
        addSubview(counterView)
        addSubview(scoreLabel)
        addSubview(messageButton)
        addSubview(startButton)


    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }



    override func draw(_ rect: CGRect) {
        pauseButton.frame = CGRect(x: w/2-50, y: h/2-50, width: 100, height: 100)
        startButton.frame = CGRect(x: w/2-50, y: h/2-50, width: 100, height: 100)
        counterView.frame = CGRect(x: w/2-50, y: h/2-50, width: 100, height: 100)

        scoreLabel.frame = CGRect(x: w-100, y: 30, width: 100, height: 100)
        messageButton.frame = CGRect(x: 100, y: 30, width: 100, height: 100)
    }



    func getScore()->Int {
        return score
    }

    func setScore(score: Int){
        self.score = score
        self.scoreLabel.text = String(score)
    }

    func addScore(_ score: Int){
        self.score += score
        self.scoreLabel.text = String(self.score)
    }

    func animationForNumber(imageName: Int, callback: @escaping ()->Void) {

        if(imageName>3){
            print("start the game from callback")
            callback()
            return
        }


        let h = UIScreen.main.bounds.height
                let w = UIScreen.main.bounds.width

                counterView.image = UIImage(named: String(imageName))
        counterView.alpha = 1
        counterView.frame.origin = CGPoint(x: w/2-50, y: h/2-50)
        counterView.frame.size = CGSize(width: 100, height: 100)


        UIView.animate(withDuration: 1, delay: 0, options : [],
        animations: {
            print("animation \(imageName)")

            self.counterView.alpha = 0
            self.counterView.frame.origin = CGPoint(x: w/2-100, y: h/2-100)
            self.counterView.frame.size = CGSize(width: 200, height: 200)

        }, completion: {(true) in

                self.animationForNumber(imageName: imageName + 1, callback: callback)

        })
    }


    func addPower(powerView : UIImageView, duration : TimeInterval){
        powerView.isHidden = false
        powerView.frame.origin = self.powerAnchor
        powerView.frame.size = self.sizeOfPowerIcons

        nbPower += 1
        powerAnchor.y += self.sizeOfPowerIcons.height

        UIView.animate(withDuration: duration, delay: 0, options: .curveEaseIn, animations:
        {
            powerView.alpha = 0
        },
        completion: {(Bool) in
                print("---------------completion done")
            self.nbPower -= 1
            self.powerAnchor.y -= self.sizeOfPowerIcons.height
            powerView.isHidden = true
        })

    }

    func resetPower() {
        powerAnchor.y -= (sizeOfPowerIcons.height * CGFloat(nbPower))
        nbPower = 0
    }

}
