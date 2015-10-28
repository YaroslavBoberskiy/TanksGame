package com.kademika.boberskiy.tanksgame;

/**
 * Created by YB on 27.10.2015.
 */
public class T34 extends Tank {

    public T34(ActionField af, BattleField bf) {
        super(af, bf);
    }

    @Override
    public String toString() {
        return "T34 legendary tank";
    }

    @Override
    public void move() throws Exception {
        System.out.println(this.toString() + " is moving");
    }

}
