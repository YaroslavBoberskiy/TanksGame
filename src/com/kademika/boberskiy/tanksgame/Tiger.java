package com.kademika.boberskiy.tanksgame;

import java.awt.*;

/**
 * Created by YB on 05.11.2015.
 */
public class Tiger extends AbstractTank {

    private ActionField af;
    private int armor = 1;

    public Tiger(ActionField af, BattleField bf) {
        super(af, bf);
        tankColor = new Color(128, 128, 0);
        towerColor = new Color(255, 128, 0);
    }

    public Tiger(ActionField af, BattleField bf, int x, int y, Direction direction) {
        super(af, bf, x, y, direction);
        tankColor = new Color(128, 128, 0);
        towerColor = new Color(255, 128, 0);
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    @Override
    public void selfDestroy() {
        updateX (-100*64);
        updateY (-100*64);
    }
}
