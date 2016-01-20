package com.kademika.boberskiy.battlefield;

import com.kademika.boberskiy.engine.ActionField;
import com.kademika.boberskiy.tanks.T34;
import com.kademika.boberskiy.tanks.Tiger;

import java.awt.*;

/**
 * Created by YB on 26.10.2015.
 */
public class BattleField {

    private final int BF_WIDTH = 592;
    private final int BF_HEIGHT = 592;
    private final int BF_MIN_COORDINATE = 0;
    private final int BF_MAX_COORDINATE = 576;
    private final boolean COLORED_MODE = false;
    private ActionField af;
    private Tiger agressor;
    private T34 defender;

    private String[][] battleFieldMap = {
            {"empty", "empty", "empty", "empty", "rock", "empty", "empty", "empty", "empty"},
            {"brick", "water", "water", "empty", "rock", "empty", "water", "water", "brick"},
            {"brick", "brick", "brick", "brick", "brick", "brick", "brick", "brick", "brick"},
            {"empty", "empty", "water", "water", "brick", "water", "water", "empty", "empty"},
            {"brick", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "brick"},
            {"rock", "rock", "brick", "rock", "rock", "rock", "brick", "rock", "rock"},
            {"brick", "brick", "empty", "empty", "empty", "empty", "empty", "brick", "brick"},
            {"empty", "brick", "empty", "brick", "brick", "brick", "empty", "brick", "empty"},
            {"empty", "brick", "empty", "brick", "eagle", "brick", "empty", "brick", "empty"}};

    private BattleFieldAbstractObject[][] battleField = new BattleFieldAbstractObject[9][9];

    public BattleField(ActionField af) {
        this.af = af;
        createBattleField();
    }

    public void drawField(Graphics g) {
        int i = 0;
        Color cc;
        for (int v = 0; v < 9; v++) {
            for (int h = 0; h < 9; h++) {
                if (COLORED_MODE) {
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
    }

    public void createBattleField() {
        if (battleFieldMap != null && battleField != null) {
            for (int j = 0; j < this.getDimensionY(); j++) {
                for (int k = 0; k < this.getDimensionX(); k++) {
                    if (battleFieldMap[j][k].equals("empty")) {
                        battleField[j][k] = new Empty(j, k);
                    } else if (battleFieldMap[j][k].equals("brick")) {
                        battleField[j][k] = new Brick(j, k);
                    } else if (battleFieldMap[j][k].equals("water")) {
                        battleField[j][k] = new Water(j, k);
                    } else if (battleFieldMap[j][k].equals("eagle")) {
                        battleField[j][k] = new Eagle(j, k);
                    } else if (battleFieldMap[j][k].equals("rock")) {
                        battleField[j][k] = new Rock(j, k);
                    } else {
                        throw new IllegalArgumentException();
                    }
                }
            }
        }
    }

    public void drawObjectsBesidesWater(Graphics g) {
        for (int j = 0; j < this.getDimensionY(); j++) {
            for (int k = 0; k < this.getDimensionX(); k++) {
                if (!(battleField[j][k] instanceof Water)) {
                    battleField[j][k].draw(g);
                }
            }
        }
    }

    public void drawObjectsOfWater(Graphics g) {
        for (int j = 0; j < this.getDimensionY(); j++) {
            for (int k = 0; k < this.getDimensionX(); k++) {
                if (battleField[j][k] instanceof Water) {
                    battleField[j][k].draw(g);
                }
            }
        }
    }

    public Tiger getAgressor() {
        return af.getAgressor();
    }

    public T34 getDefender() {
        return af.getDefender();
    }

    public BattleFieldAbstractObject scanQuadrant(int v, int h) {
        return battleField[v][h];
    }

    public void updateQuadrant(int v, int h, BattleFieldAbstractObject object) {
        battleField[v][h] = object;
    }

    int getDimensionX() {
        return battleField.length;
    }

    int getDimensionY() {
        return battleField.length;
    }

    public int getBfWidth() {
        return BF_WIDTH;
    }

    public int getBfHeight() {
        return BF_HEIGHT;
    }

    public int getBF_MIN_COORDINATE() {
        return BF_MIN_COORDINATE;
    }

    public int getBF_MAX_COORDINATE() {
        return BF_MAX_COORDINATE;
    }

    public void destroyObject(int v, int h) {
        if (!battleField[v][h].getClass().getName().contains("Rock")) {
            battleField[v][h].selfDestroy();
            updateQuadrant(v, h, new Empty(v, h));
        }
    }
}
