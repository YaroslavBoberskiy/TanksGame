package com.kademika.boberskiy.tanks;

import com.kademika.boberskiy.battlefield.*;
import com.kademika.boberskiy.engine.ActionField;
import com.kademika.boberskiy.engine.Direction;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by YB on 05.11.2015.
 */
public class Tiger extends AbstractTank {

    private int armor = 1;
    private int currentStep = 0;

    private ArrayList<Object> tankBehaviorScenario = new ArrayList<Object>();
    int x = this.getX() / 64;
    int y = this.getY() / 64;


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

    @Override
    public void selfDestroy() {
        if (armor > 0) {
            armor--;
        } else {
            super.selfDestroy();
        }
    }

    public void destroyEagleScenario() {

        int tankCoordinateX = this.getX() / 64;
        int tankCoordinateY = this.getY() / 64;

        while (rotateLocator(tankCoordinateY, tankCoordinateX).equals(Direction.NONE)) {
            tankBehaviorScenario.add(Actions.FIRE);
            tankBehaviorScenario.add(Actions.MOVE);
            tankCoordinateY++;
            if (!rotateLocator(tankCoordinateY, tankCoordinateX).equals(Direction.NONE)) {
                tankBehaviorScenario.add(rotateLocator(tankCoordinateY, tankCoordinateX));
                for (int i = 0; i < scanFrontPath(rotateLocator(tankCoordinateY, tankCoordinateX),
                        tankCoordinateY, tankCoordinateX).substring(0, scanFrontPath(rotateLocator(tankCoordinateY, tankCoordinateX),
                        tankCoordinateY, tankCoordinateX).indexOf("H")).length(); i++) {
                    tankBehaviorScenario.add(Actions.FIRE);
                }
            }
        }
    }

    public Direction rotateLocator(int y, int x) {
        if (scanFrontPath(Direction.UP, y, x).contains("H")) {
            return Direction.UP;
        } else if (scanFrontPath(Direction.RIGHT, y, x).contains("H")) {
            return Direction.RIGHT;
        } else if (scanFrontPath(Direction.DOWN, y, x).contains("H")) {
            return Direction.DOWN;
        } else if (scanFrontPath(Direction.LEFT, y, x).contains("H")) {
            return Direction.LEFT;
        } else {
            return Direction.NONE;
        }
    }

    public String scanFrontPath(Direction direction, int y, int x) {
        String pathCode = "";
        if (x < 9 && y < 9 && x >= 0 && y >= 0) {
            if (direction.equals(Direction.LEFT)) {
                pathCode = "";
                for (; x >= 0; x--) {
                    pathCode += getFieldObjectID(y, x);
                }
            } else if (direction.equals(Direction.RIGHT)) {
                pathCode = "";
                for (; x < 9; x++) {
                    pathCode += getFieldObjectID(y, x);
                }
            } else if (direction.equals(Direction.DOWN)) {
                pathCode = "";
                for (; y < 9; y++) {
                    pathCode += getFieldObjectID(y, x);
                }
            } else if (direction.equals(Direction.UP)) {
                pathCode = "";
                for (; y >= 0; y--) {
                    pathCode += getFieldObjectID(y, x);
                }
            }
        }
        return pathCode;
    }

    private String getFieldObjectID(int y, int x) {
        if (this.bf.scanQuadrant(y, x) instanceof Eagle) {
            return "H";
        } else if (this.bf.scanQuadrant(y, x) instanceof Rock) {
            return "R";
        } else if (this.bf.scanQuadrant(y, x) instanceof Water) {
            return "W";
        } else if (this.bf.scanQuadrant(y, x) instanceof Brick) {
            return "B";
        } else if (this.bf.scanQuadrant(y, x) instanceof Empty) {
            return "E";
        } else {
            return "";
        }
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

}
