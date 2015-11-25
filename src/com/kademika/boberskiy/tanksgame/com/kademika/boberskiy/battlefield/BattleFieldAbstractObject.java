package com.kademika.boberskiy.tanksgame.com.kademika.boberskiy.battlefield;

import com.kademika.boberskiy.tanksgame.com.kademika.boberskiy.com.kademika.boberskiy.gameengine.Drawable;

import java.awt.*;

/**
 * Created by YB on 22.11.2015.
 */
public class BattleFieldAbstractObject implements Drawable {

    public Color objectColor;
    protected int objXCoordinate;
    protected int objYCoordinate;

    @Override
    public void draw(Graphics g) {
        g.setColor(objectColor);
        g.fillRect(objXCoordinate, objYCoordinate, 64, 64);
    }

    public Color getObjectColor() {
        return objectColor;
    }
}
