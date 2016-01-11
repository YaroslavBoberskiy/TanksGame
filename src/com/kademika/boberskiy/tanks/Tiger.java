package com.kademika.boberskiy.tanks;

import com.kademika.boberskiy.battlefield.*;
import com.kademika.boberskiy.engine.ActionField;
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

    int x = this.getX() / 64;
    int y = this.getY() / 64;


    public Tiger(ActionField af, BattleField bf) {
        super(af, bf);
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

    public Tiger(ActionField af, BattleField bf, int x, int y, Direction direction) {
        super(af, bf, x, y, direction);
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

    public void destroyEagleScenario() {

        int tankCoordinateX = x;
        int tankCoordinateY = y;
        Direction direction = getDirection();
        boolean fireEmplacementCheck = false;


        while (fireEmplacementCheck == false) {
            if (tankCoordinateY >= 0 && tankCoordinateX >= 0 && tankCoordinateY <= 8 && tankCoordinateX <= 8) {

                fireEmplacementCheck = fireEmplacementCheck(tankCoordinateY, tankCoordinateX);

                if (isPathClearToMoveDown(tankCoordinateY, tankCoordinateX) == true) {
                    System.out.println("Path down towards Eagle is clear");
                    tankBehaviorScenario.add(Direction.DOWN);
                    tankBehaviorScenario.add(Actions.FIRE);
                    tankBehaviorScenario.add(Actions.MOVE);
                    direction = Direction.DOWN;
                    tankCoordinateY++;
                    fireEmplacementCheck = fireEmplacementCheck(tankCoordinateY, tankCoordinateX);
                } else {
                    if (!(getObjectInFrontOfTank(tankCoordinateY, tankCoordinateX) instanceof Rock)) {
                        tankBehaviorScenario.add(Direction.DOWN);
                        tankBehaviorScenario.add(Actions.FIRE);
                        tankBehaviorScenario.add(Actions.MOVE);
                        direction = Direction.DOWN;
                        tankCoordinateY++;
                        fireEmplacementCheck = fireEmplacementCheck(tankCoordinateY, tankCoordinateX);
                    } else if (getObjectInFrontOfTank(tankCoordinateY, tankCoordinateX) instanceof Rock) {
                        tankBehaviorScenario.add(Direction.LEFT);
                        tankBehaviorScenario.add(Actions.FIRE);
                        tankBehaviorScenario.add(Actions.MOVE);
                        direction = Direction.LEFT;
                        tankCoordinateX--;
                        fireEmplacementCheck = fireEmplacementCheck(tankCoordinateY, tankCoordinateX);
                    }
                }
            }
        }
    }

    public boolean isPathClearToMoveDown(int tankCoordinateY, int tankCoordinateX) {
        if (!scanFrontPath(Direction.DOWN, tankCoordinateY, tankCoordinateX).contains("R") &&
                scanFrontPath(Direction.DOWN, tankCoordinateY, tankCoordinateX).length() > 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean fireEmplacementCheck(int tankCoordinateY, int tankCoordinateX) {
        if (!lookForClearPathToEagle(tankCoordinateY, tankCoordinateX).equals(Direction.NONE)) {
            tankBehaviorScenario.add(lookForClearPathToEagle(tankCoordinateY, tankCoordinateX));
            for (int i = 0; i < scanFrontPath(lookForClearPathToEagle(tankCoordinateY, tankCoordinateX),
                    tankCoordinateY, tankCoordinateX).substring(0, scanFrontPath(lookForClearPathToEagle(tankCoordinateY, tankCoordinateX),
                    tankCoordinateY, tankCoordinateX).indexOf("H")).length(); i++) {
                tankBehaviorScenario.add(Actions.FIRE);
            }
            System.out.println("Clear Path To Eagle Found");
            return true;
        } else {
            return false;
        }
    }

    public Direction lookForClearPathToEagle(int y, int x) {
        if (scanFrontPath(Direction.UP, y, x).contains("H") && !scanFrontPath(Direction.UP, y, x).contains("R")) {
            return Direction.UP;
        } else if (scanFrontPath(Direction.RIGHT, y, x).contains("H") && !scanFrontPath(Direction.RIGHT, y, x).contains("R")) {
            return Direction.RIGHT;
        } else if (scanFrontPath(Direction.DOWN, y, x).contains("H") && !scanFrontPath(Direction.DOWN, y, x).contains("R")) {
            return Direction.DOWN;
        } else if (scanFrontPath(Direction.LEFT, y, x).contains("H") && !scanFrontPath(Direction.LEFT, y, x).contains("R")) {
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

    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        return false;
    }
}
