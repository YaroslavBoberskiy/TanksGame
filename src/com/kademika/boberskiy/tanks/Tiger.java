package com.kademika.boberskiy.tanks;

import com.kademika.boberskiy.battlefield.*;
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
public class Tiger extends AbstractTank {

    private int armor = 1;
    private int currentStep = 0;

    public ArrayList<Object> tankBehaviorScenario = new ArrayList<Object>();

    public Tiger(BattleField bf) {
        super(bf);
        tankColor = new Color(128, 128, 0);
        towerColor = new Color(255, 128, 0);
        try {
            tankImageLeft = ImageIO.read(new File("resources/greenTank_LEFT.png"));
            tankImageRight = ImageIO.read(new File("resources/greenTank_RIGHT.png"));
            tankImageUp = ImageIO.read(new File("resources/greenTank_UP.png"));
            tankImageDown = ImageIO.read(new File("resources/greenTank_DOWN.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Tiger(BattleField bf, int x, int y, Direction direction) {
        super(bf, x, y, direction);
        tankColor = new Color(128, 128, 0);
        towerColor = new Color(255, 128, 0);
        try {
            tankImageLeft = ImageIO.read(new File("resources/greenTank_LEFT.png"));
            tankImageRight = ImageIO.read(new File("resources/greenTank_RIGHT.png"));
            tankImageUp = ImageIO.read(new File("resources/greenTank_UP.png"));
            tankImageDown = ImageIO.read(new File("resources/greenTank_DOWN.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void selfDestroy() {
        if (armor > 0) {
            armor--;
        } else {
            super.selfDestroy();
        }
    }

    @Override
    public boolean checkEnemyLocation(int y, int x) {
        if (x == bf.getDefender().getTankXQuadrant() && y == bf.getDefender().getTankYQuadrant()) {
            return true;
        } else {
            return false;
        }
    }

    public void destroyScenario(String target) {

        int tankCoordinateX = getTankXQuadrant();
        int tankCoordinateY = getTankYQuadrant();
        Direction direction = getDirection();
        boolean fireEmplacementCheck = false;

        while (fireEmplacementCheck == false) {
            if (tankCoordinateY >= 0 && tankCoordinateX >= 0 && tankCoordinateY <= 8 && tankCoordinateX <= 8) {

                fireEmplacementCheck = fireEmplacementCheck(target, tankCoordinateY, tankCoordinateX);

                if (isPathClearToMoveDown(tankCoordinateY, tankCoordinateX) == true) {
                    //System.out.println("Path down towards target is clear");
                    addMoveDownActions();
                    direction = Direction.DOWN;
                    tankCoordinateY++;
                    fireEmplacementCheck = fireEmplacementCheck(target, tankCoordinateY, tankCoordinateX);
                } else if (isPathClearToMoveDown(tankCoordinateY, tankCoordinateX) == false && tankCoordinateX < 5) {
                    addMoveRightActions();
                    direction = Direction.RIGHT;
                    tankCoordinateX++;
                    fireEmplacementCheck = fireEmplacementCheck(target, tankCoordinateY, tankCoordinateX);
                } else if (isPathClearToMoveDown(tankCoordinateY, tankCoordinateX) == false && tankCoordinateX >= 5) {
                    addMoveLeftActions();
                    direction = Direction.LEFT;
                    tankCoordinateX--;
                    fireEmplacementCheck = fireEmplacementCheck(target, tankCoordinateY, tankCoordinateX);
                }
            }
        }
    }

    public void addMoveDownActions() {
        tankBehaviorScenario.add(Direction.DOWN);
        tankBehaviorScenario.add(Actions.FIRE);
        tankBehaviorScenario.add(Actions.MOVE);
    }

    public void addMoveLeftActions() {
        tankBehaviorScenario.add(Direction.LEFT);
        tankBehaviorScenario.add(Actions.FIRE);
        tankBehaviorScenario.add(Actions.MOVE);
    }

    public void addMoveRightActions() {
        tankBehaviorScenario.add(Direction.RIGHT);
        tankBehaviorScenario.add(Actions.FIRE);
        tankBehaviorScenario.add(Actions.MOVE);
    }

    public boolean isPathClearToMoveDown(int tankCoordinateY, int tankCoordinateX) {

        if (!(getObjectInFrontOfTank(tankCoordinateY, tankCoordinateX) instanceof Rock) &&
                scanFrontPath(Direction.DOWN, tankCoordinateY, tankCoordinateX).length() > 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean fireEmplacementCheck(String target, int tankCoordinateY, int tankCoordinateX) {
        if (!lookForClearPathToTarget(target, tankCoordinateY, tankCoordinateX).equals(Direction.NONE)) {
            tankBehaviorScenario.add(lookForClearPathToTarget(target, tankCoordinateY, tankCoordinateX));
            for (int i = 0; i < scanFrontPath(lookForClearPathToTarget(target, tankCoordinateY, tankCoordinateX),
                    tankCoordinateY, tankCoordinateX).substring(0, scanFrontPath(lookForClearPathToTarget(target, tankCoordinateY, tankCoordinateX),
                    tankCoordinateY, tankCoordinateX).indexOf(target)).length(); i++) {
                tankBehaviorScenario.add(Actions.FIRE);
            }
            //System.out.println("Clear Path To Target Found");
            return true;
        } else {
            return false;
        }
    }

    public Direction lookForClearPathToTarget(String target, int y, int x) {
        if (scanFrontPath(Direction.UP, y, x).contains(target) && !scanFrontPath(Direction.UP, y, x).contains("R")) {
            return Direction.UP;
        } else if (scanFrontPath(Direction.RIGHT, y, x).contains(target) && !scanFrontPath(Direction.RIGHT, y, x).contains("R")) {
            return Direction.RIGHT;
        } else if (scanFrontPath(Direction.DOWN, y, x).contains(target) && !scanFrontPath(Direction.DOWN, y, x).contains("R")) {
            return Direction.DOWN;
        } else if (scanFrontPath(Direction.LEFT, y, x).contains(target) && !scanFrontPath(Direction.LEFT, y, x).contains("R")) {
            return Direction.LEFT;
        } else {
            return Direction.NONE;
        }
    }

    public void writeActionsToFile () {
        ActionsRecorder actionsRecorder = new ActionsRecorder(tankBehaviorScenario);
        actionsRecorder.writeAgressorToFile();
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
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        return false;
    }
}
