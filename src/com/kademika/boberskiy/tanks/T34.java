package com.kademika.boberskiy.tanks;

import com.kademika.boberskiy.battlefield.BattleField;
import com.kademika.boberskiy.engine.ActionsRecorder;
import com.kademika.boberskiy.engine.Direction;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by YB on 05.11.2015.
 */
public class T34 extends AbstractTank {

    public ArrayList<Object> tankBehaviorScenario = new ArrayList<Object>();
    Direction directions[] = {Direction.DOWN, Direction.LEFT, Direction.RIGHT, Direction.UP};

    public T34(BattleField bf) {
        super(bf);
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

    public T34(BattleField bf, int x, int y, Direction direction) {
        super(bf, x, y, direction);
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

    public void defendTheEagle() {
        moveToQuadrant(8, 2);
        moveToQuadrant(6, 2);
        moveToQuadrant(6, 6);
        moveToQuadrant(8, 6);
        moveToQuadrant(6, 6);
        moveToQuadrant(6, 2);
        moveToQuadrant(8, 2);
    }

    public void moveToQuadrant(int y, int x) {

        int tankYQuadrant = getTankYQuadrant();
        int tankXQuadrant = getTankXQuadrant();
        Direction direction = getDirection();

        while (!(y == tankYQuadrant && x == tankXQuadrant)) {
            if (y > tankYQuadrant) {
                tankBehaviorScenario.add(Direction.DOWN);
                tankBehaviorScenario.add(Actions.MOVE);
                direction = Direction.DOWN;
                tankYQuadrant++;
                spotAndFireTheEnemy(tankYQuadrant, tankXQuadrant);
            }

            if (y < tankYQuadrant) {
                tankBehaviorScenario.add(Direction.UP);
                tankBehaviorScenario.add(Actions.MOVE);
                direction = Direction.UP;
                tankYQuadrant--;
                spotAndFireTheEnemy(tankYQuadrant, tankXQuadrant);
            }

            if (x < tankXQuadrant) {
                tankBehaviorScenario.add(Direction.LEFT);
                tankBehaviorScenario.add(Actions.MOVE);
                direction = Direction.LEFT;
                tankXQuadrant--;
                spotAndFireTheEnemy(tankYQuadrant, tankXQuadrant);
            }

            if (x > tankXQuadrant) {
                tankBehaviorScenario.add(Direction.RIGHT);
                tankBehaviorScenario.add(Actions.MOVE);
                direction = Direction.RIGHT;
                tankXQuadrant++;
                spotAndFireTheEnemy(tankYQuadrant, tankXQuadrant);
            }
        }
    }

    public void spotAndFireTheEnemy(int y, int x) {
        for (int i = 0; i < 4; i++) {
            if (!scanFrontPath(directions[i], y, x).contains("H") &&
                    scanFrontPath(directions[i], y, x).contains("T") &&
                    checkRocksOnTheWayToTheTarget(directions[i], y, x) == false) {
                tankBehaviorScenario.add(directions[i]);
                tankBehaviorScenario.add(Actions.FIRE);
                tankBehaviorScenario.add(Actions.FIRE);
            }
        }
    }

    public boolean checkRocksOnTheWayToTheTarget(Direction direction, int y, int x) {
        StringBuilder sb = new StringBuilder(scanFrontPath(direction, y, x));
        int tankLocation = sb.indexOf("T");
        String pathCode = scanFrontPath(direction, y, x).substring(0, tankLocation);
        if (pathCode.contains("R")) {
            return true;
        } else {
            return false;
        }
    }

    private int currentStep = 0;

    public void writeActionsToFile () {
        ActionsRecorder actionsRecorder = new ActionsRecorder(tankBehaviorScenario);
        actionsRecorder.writeDefenderToFile();

    }

    @Override
    public Actions setUp() {
        if (currentStep >= tankBehaviorScenario.size()) {
            currentStep = 0;
        }
        if (!(tankBehaviorScenario.get(currentStep) instanceof Actions)) {
            turn((Direction) tankBehaviorScenario.get(currentStep++));
        }
        if (currentStep >= tankBehaviorScenario.size()) {
            currentStep = 0;
        }
        return (Actions) tankBehaviorScenario.get(currentStep++);
    }

    @Override
    public void selfDestroy() {
        super.selfDestroy();
    }

    @Override
    public boolean checkEnemyLocation(int y, int x) {
        if (x == bf.getAgressor().getTankXQuadrant() && y == bf.getAgressor().getTankYQuadrant()) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        return false;
    }
}
