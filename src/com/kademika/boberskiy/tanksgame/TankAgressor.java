package com.kademika.boberskiy.tanksgame;

import java.util.Random;

/**
 * Created by YB on 05.11.2015.
 */
public class TankAgressor extends AbstractTank {

    private int armor = 1;

    public TankAgressor (ActionField af, BattleField bf) {
        super(af, bf);
    }

    public TankAgressor(ActionField af, BattleField bf, int x, int y, Direction direction) {
        super(af, bf, x, y, direction);
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }
}
