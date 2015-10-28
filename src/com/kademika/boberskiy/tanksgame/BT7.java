package com.kademika.boberskiy.tanksgame;

/**
 * Created by YB on 27.10.2015.
 */
public class BT7 extends Tank {

    public BT7(ActionField af, BattleField bf) {
        super(af, bf);
    }

    @Override
    public String toString() {
        return "BT7 easy tank";
    }

    @Override
    public void move() throws Exception {
        System.out.println(this.toString() + " is moving");
    }

}
