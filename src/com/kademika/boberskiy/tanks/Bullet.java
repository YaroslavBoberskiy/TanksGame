package com.kademika.boberskiy.tanks;

import com.kademika.boberskiy.engine.Destroyable;
import com.kademika.boberskiy.engine.Direction;
import com.kademika.boberskiy.engine.Drawable;

import java.awt.*;

/**
 * Created by YB on 26.10.2015.
 */
public class Bullet implements Drawable, Destroyable {

    private int x = -100;
    private int y = -100;
    private final int speed = 5;
    private final Direction direction;
    private boolean destroyed;

    public Bullet (int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.destroyed = false;
    }

    public int getSpeed() {
        return speed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void updateX (int x) {
        this.x += x;
    }

    public void updateY (int y) {
        this.y += y;
    }



    @Override
    public void draw(Graphics g) {

        // BULLET
        if (! destroyed) {
            g.setColor(new Color(0, 255, 255));
            g.fillRect(this.getX(), this.getY(), 14, 14);
        }
    }

    @Override
    public void selfDestroy() {
        destroyed = true;
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }
}
