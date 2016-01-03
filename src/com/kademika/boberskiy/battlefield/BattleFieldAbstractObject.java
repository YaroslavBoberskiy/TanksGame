package com.kademika.boberskiy.battlefield;

import com.kademika.boberskiy.engine.Destroyable;
import com.kademika.boberskiy.engine.Drawable;

import java.awt.*;

/**
 * Created by YB on 22.11.2015.
 */
public class BattleFieldAbstractObject implements Drawable, Destroyable {

    Color objectColor;
    int objXCoordinate;
    int objYCoordinate;
    boolean destroyed;

    public BattleFieldAbstractObject(int v, int h) {
        this.objYCoordinate = v;
        this.objXCoordinate = h;
        destroyed = false;
    }

    public Color getObjectColor() {
        return objectColor;
    }

    public int getObjXCoordinate() {
        return objXCoordinate;
    }

    public int getObjYCoordinate() {
        return objYCoordinate;
    }

    @Override
    public void selfDestroy() {
        destroyed = true;
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }

    @Override
    public void draw(Graphics g) {
        if (!destroyed) {
            g.setColor(this.getObjectColor());
            g.fillRect(this.getObjXCoordinate() * 64, this.getObjYCoordinate() * 64, 64, 64);
        }
    }
}
