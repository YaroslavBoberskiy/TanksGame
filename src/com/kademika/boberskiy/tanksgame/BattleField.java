package com.kademika.boberskiy.tanksgame;

import java.util.Random;

/**
 * Created by YB on 26.10.2015.
 */
public class BattleField {

    private final int BF_WIDTH = 592;
    private final int BF_HEIGHT = 592;

    private String[][] battleField = {
            { " ", "B", " ", "B", "B", " ", "B", " ", "B"},
            { "B", "B", " ", " ", " ", " ", "B", " ", "B" },
            { " ", "B", " ", " ", " ", "B", " ", " ", "B" },
            { "B", "B", "B", "B", " ", " ", "B", "B", "B" },
            { " ", " ", "B", "B", "B", " ", " ", " ", " " },
            { "B", " ", " ", " ", "B", "B", "B", " ", " " },
            { "B", " ", "B", "B", " ", " ", "B", "B", " " },
            { " ", " ", " ", " ", "B", "B", " ", " ", " " },
            { "B", " ", " ", "B", "B", " ", " ", " ", "B" }, };

    public BattleField () {
    }

    public BattleField (String [][] battleField) {
        this.battleField = battleField;
    }

    public String scanQuadrant (int v, int h) {
        return battleField [v][h];
    }

    public void updateQuadrant (int v, int h, String object) {
        battleField [v][h] = object;
    }

    public int getDimensionX () {
        return battleField.length;
    }

    public int getDimensionY () {
        return battleField.length;
    }

    public int getBfWidth() {
        return BF_WIDTH;
    }

    public int getBfHeight() {
        return BF_HEIGHT;
    }
}
