package com.kademika.boberskiy.tanksgame.com.kademika.boberskiy.battlefield;

import com.kademika.boberskiy.tanksgame.com.kademika.boberskiy.com.kademika.boberskiy.gameengine.Drawable;

import java.awt.*;

/**
 * Created by YB on 26.10.2015.
 */
public class BattleField implements Drawable {

    protected final int BF_WIDTH = 592;
    protected final int BF_HEIGHT = 592;
    protected boolean COLORDED_MODE = true;

    private Brick brick = new Brick();
    private Eagle eagle = new Eagle();
    private Water water = new Water();
    private Rock rock = new Rock();
    private Empty empty = new Empty();

    protected BattleFieldAbstractObject[][] battleField = {
            {empty, brick, empty, empty, rock, empty, empty, empty, brick},
            {brick, brick, empty, empty, empty, empty, brick, empty, brick},
            {empty, water, empty, empty, empty, rock, empty, empty, water},
            {brick, brick, brick, brick, brick, empty, rock, brick, brick},
            {empty, empty, brick, brick, brick, empty, empty, empty, empty},
            {brick, empty, empty, empty, eagle, brick, brick, empty, empty},
            {empty, empty, water, brick, empty, empty, brick, brick, rock},
            {empty, empty, empty, empty, brick, brick, empty, empty, empty},
            {brick, empty, empty, empty, empty, empty, empty, empty, empty},};

    public BattleField() {
    }

    public BattleField(BattleFieldAbstractObject[][] battleField) {
        this.battleField = battleField;
    }

    public BattleFieldAbstractObject scanQuadrant(int v, int h) {
        return battleField[v][h];
    }

    public void updateQuadrant(int v, int h, BattleFieldAbstractObject object) {
        battleField[v][h] = object;
    }

    public int getDimensionX() {
        return battleField.length;
    }

    public int getDimensionY() {
        return battleField.length;
    }

    public int getBfWidth() {
        return BF_WIDTH;
    }

    public int getBfHeight() {
        return BF_HEIGHT;
    }

    public String getQuadrantXY(int v, int h) {

        return (v - 1) * 64 + "_" + (h - 1) * 64;
    }

    public int getObjectXCoordinate(String coordinates) {
        int separator = coordinates.indexOf("_");
        return Integer.parseInt(coordinates.substring(separator + 1));
    }

    public int getObjectYCoordinate(String coordinates) {
        int separator = coordinates.indexOf("_");
        return Integer.parseInt(coordinates.substring(0, separator));
    }

    @Override
    public void draw(Graphics g) {
        int i = 0;
        Color cc;
        for (int v = 0; v < 9; v++) {
            for (int h = 0; h < 9; h++) {
                if (COLORDED_MODE) {
                    if (i % 2 == 0) {
                        cc = new Color(252, 241, 177);
                    } else {
                        cc = new Color(233, 243, 255);
                    }
                } else {
                    cc = new Color(180, 180, 180);
                }
                i++;
                g.setColor(cc);
                g.fillRect(h * 64, v * 64, 64, 64);
            }
        }

        for (int j = 0; j < this.getDimensionY(); j++) {
            for (int k = 0; k < this.getDimensionX(); k++) {
                if (this.scanQuadrant(j, k).equals(brick)) {
                    brick.objYCoordinate = getObjectYCoordinate(getQuadrantXY(j + 1, k + 1));
                    brick.objXCoordinate = getObjectXCoordinate(getQuadrantXY(j + 1, k + 1));
                    brick.draw(g);
                }
                if (this.scanQuadrant(j, k).equals(eagle)) {
                    eagle.objYCoordinate = getObjectYCoordinate(getQuadrantXY(j + 1, k + 1));
                    eagle.objXCoordinate = getObjectXCoordinate(getQuadrantXY(j + 1, k + 1));
                    eagle.draw(g);
                }
                if (this.scanQuadrant(j, k).equals(water)) {
                    water.objYCoordinate = getObjectYCoordinate(getQuadrantXY(j + 1, k + 1));
                    water.objXCoordinate = getObjectXCoordinate(getQuadrantXY(j + 1, k + 1));
                    water.draw(g);
                }
                if (this.scanQuadrant(j, k).equals(rock)) {
                    rock.objYCoordinate = getObjectYCoordinate(getQuadrantXY(j + 1, k + 1));
                    rock.objXCoordinate = getObjectXCoordinate(getQuadrantXY(j + 1, k + 1));
                    rock.draw(g);
                }
            }
        }
    }
}
