package com.kademika.boberskiy.tanksgame;

/**
 * Created by YB on 27.10.2015.
 */
public class BT7 extends Tank {

    public BT7(ActionField af, BattleField bf) {
        super(af, bf);
        speed = super.getSpeed()*2;
    }


}
