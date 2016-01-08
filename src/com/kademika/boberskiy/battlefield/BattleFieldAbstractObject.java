package com.kademika.boberskiy.battlefield;

import com.kademika.boberskiy.engine.Destroyable;
import com.kademika.boberskiy.engine.Drawable;

import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * Created by YB on 22.11.2015.
 */
public class BattleFieldAbstractObject implements Drawable, Destroyable, ImageObserver {

    Color objectColor;
    Image objectImage;
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

    public Image getObjectImage() {
        return objectImage;
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
            g.drawImage(this.getObjectImage(), this.getObjXCoordinate() * 64, this.getObjYCoordinate() * 64, 64, 64, this);
        }
    }

    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        return false;
    }
}
