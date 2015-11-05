package com.kademika.boberskiy.tanksgame;

/**
 * Created by YB on 05.11.2015.
 */
public class TankAgressor extends Tank {

    public TankAgressor (ActionField af, BattleField bf) {
        super(af, bf);
    }

    public TankAgressor(ActionField af, BattleField bf, int x, int y, Direction direction) {
        super(af, bf, x, y, direction);
    }

}
