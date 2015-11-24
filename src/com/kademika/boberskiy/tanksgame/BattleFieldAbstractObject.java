package com.kademika.boberskiy.tanksgame;

import java.awt.*;

/**
 * Created by YB on 22.11.2015.
 */
public class BattleFieldAbstractObject implements Drawable {

    protected Color objectColor;
    protected int objXCoordinate;
    protected int objYCoordinate;

    @Override
    public void draw(Graphics g) {
        g.setColor(objectColor);
        g.fillRect(objXCoordinate, objYCoordinate, 64, 64);
    }
}
