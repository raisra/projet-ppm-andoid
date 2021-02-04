package com.example.projetppm;

public enum  TypeOfObject implements Type {
    coin,
    empty,
    coinx2,
    coinx5,
    magnet,
    transparency,
    turbo, //accelere
    teleportation, // le personnage disparait pendant que le jeu accelere puis il reapparait
    any;


    public static TypeOfObject getRandomPower() {
        TypeOfObject powers [] = {TypeOfObject.coinx2, TypeOfObject.coinx5, TypeOfObject.magnet};
        int r = (int) (Math.random() * powers.length);
        return powers[r];
    }
}
