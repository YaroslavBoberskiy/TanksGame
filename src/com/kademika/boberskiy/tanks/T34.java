package com.kademika.boberskiy.tanks;

import com.kademika.boberskiy.engine.ActionField;
import com.kademika.boberskiy.battlefield.BattleField;
import com.kademika.boberskiy.engine.Direction;

import java.awt.*;

/**
 * Created by YB on 05.11.2015.
 */
public class T34 extends AbstractTank {

    public T34(ActionField af, BattleField bf) {
        super(af, bf);
        tankColor = new Color(128, 255, 0);
        towerColor = new Color(255, 0, 128);
    }

    public T34(ActionField af, BattleField bf, int x, int y, Direction direction) {
        super(af, bf, x, y, direction);
        tankColor = new Color(128, 255, 0);
        towerColor = new Color(255, 0, 128);
    }


    @Override
    public void selfDestroy() {
        updateX (-100*64);
        updateY (-100*64);
    }
}
