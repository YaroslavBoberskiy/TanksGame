package com.kademika.boberskiy.tanksgame;

/**
 * Created by YB on 26.10.2015.
 */
public class Bullet {

    private int speed = 5;
    private int x = -100;
    private int y = -100;
    private Direction direction;

    public Bullet (int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
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

    public void destroy () {
        x = -100;
        y = -100;
    }

}
