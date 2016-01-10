package com.kademika.boberskiy.tanks;

import com.kademika.boberskiy.battlefield.BattleField;
import com.kademika.boberskiy.battlefield.BattleFieldAbstractObject;
import com.kademika.boberskiy.battlefield.Eagle;
import com.kademika.boberskiy.engine.ActionField;
import com.kademika.boberskiy.engine.Direction;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * Created by YB on 05.11.2015.
 */
public class T34 extends AbstractTank {

    public T34(ActionField af, BattleField bf) {
        super(af, bf);
        tankColor = new Color(128, 255, 0);
        towerColor = new Color(255, 0, 128);
        try {
            tankImageLeft = ImageIO.read(new File("resources/magTank_LEFT.png"));
            tankImageRight = ImageIO.read(new File("resources/magTank_RIGHT.png"));
            tankImageUp = ImageIO.read(new File("resources/magTank_UP.png"));
            tankImageDown = ImageIO.read(new File("resources/magTank_DOWN.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public T34(ActionField af, BattleField bf, int x, int y, Direction direction) {
        super(af, bf, x, y, direction);
        tankColor = new Color(128, 255, 0);
        towerColor = new Color(255, 0, 128);
        try {
            tankImageLeft = ImageIO.read(new File("resources/magTank_LEFT.png"));
            tankImageRight = ImageIO.read(new File("resources/magTank_RIGHT.png"));
            tankImageUp = ImageIO.read(new File("resources/magTank_UP.png"));
            tankImageDown = ImageIO.read(new File("resources/magTank_DOWN.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Object[] actions = new Object[]{
            Direction.LEFT,
            Actions.MOVE,
            Actions.MOVE,
            Direction.RIGHT,
            Actions.MOVE,
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


    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        return false;
    }
}
