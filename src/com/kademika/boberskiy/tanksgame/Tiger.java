package com.kademika.boberskiy.tanksgame;

/**
 * Created by YB on 27.10.2015.
 */
public class Tiger extends Tank {

    public Tiger(ActionField af, BattleField bf) {
        super(af, bf);
    }

    @Override
    public String toString() {
        return "Tiger super tank";
    }

    @Override
    public void move() throws Exception {
        System.out.println(this.toString() + " is moving");
    }

}
