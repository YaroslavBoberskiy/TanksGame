package com.kademika.boberskiy.tanks;

import com.kademika.boberskiy.battlefield.BattleField;
import com.kademika.boberskiy.battlefield.BattleFieldAbstractObject;
import com.kademika.boberskiy.battlefield.Eagle;
import com.kademika.boberskiy.engine.ActionField;
import com.kademika.boberskiy.engine.Direction;

import java.awt.*;
import java.util.NoSuchElementException;

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

    private Object[] actions = new Object[]{
            Direction.RIGHT,
            Actions.FIRE,
            Actions.MOVE,
            Actions.FIRE,
            Direction.LEFT,
            Actions.MOVE,
    };

    private int currentStep = 0;

    @Override
    public Actions setUp() {
        if (currentStep >= actions.length) {
            currentStep = 0;
        }
        if (!(actions[currentStep] instanceof Actions)) {
            turn((Direction) actions[currentStep++]);
        }
        if (currentStep >= actions.length) {
            currentStep = 0;
        }
        return (Actions) actions[currentStep++];
    }

    @Override
    public void selfDestroy() {
        super.selfDestroy();
    }


}
