package com.kademika.boberskiy.tanks;

import com.kademika.boberskiy.battlefield.BattleField;
import com.kademika.boberskiy.engine.ActionField;
import com.kademika.boberskiy.engine.Destroyable;
import com.kademika.boberskiy.engine.Direction;
import com.kademika.boberskiy.engine.Drawable;

import java.awt.*;

/**
 * Created by YB on 26.10.2015.
 */
public abstract class AbstractTank implements Drawable, Destroyable {

    final private int speed = 10;
    private int x;
    private int y;
    final private ActionField af;
    final private BattleField bf;
    private Direction direction;
    Color tankColor;
    Color towerColor;

    AbstractTank(ActionField af, BattleField bf) {

        this(af, bf, 128, 128, Direction.RIGHT);
    }

    AbstractTank(ActionField af, BattleField bf, int x, int y, Direction direction) {
        this.af = af;
        this.bf = bf;
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public Direction getDirection() {
        return direction;
    }

    public void turn(Direction direction) throws  Exception {
        this.direction = direction;
        af.processTurn();
    }

    public void fire () throws  Exception {
        if (direction == Direction.UP) {
            Bullet bullet = new Bullet((x + 25), y, direction);
            af.processFire(bullet);
        } else if (direction == Direction.DOWN) {
            Bullet bullet = new Bullet(x+25, y+64, direction);
            af.processFire(bullet);
        } else if (direction == Direction.LEFT) {
            Bullet bullet = new Bullet(x, y+25, direction);
            af.processFire(bullet);
        } else if (direction == Direction.RIGHT) {
            Bullet bullet = new Bullet(x+64, y+25, direction);
            af.processFire(bullet);
        }
        System.out.println("FIRE");
    }

    void move() throws Exception {
        af.processMove(this);
    }

    public void updateX (int x) {
        this.x += x;
    }

    public void updateY (int y) {
        this.y += y;
    }

    public void moveToQuadrant(int localY, int localX) throws Exception {

        if (this.x < localX*64) {
            while (this.x != localX*64) {
                turn(Direction.RIGHT);
                fire();
                move();
            }
        } else {
            while (this.x != localX*64) {
                turn(Direction.LEFT);
                fire();
                move();
            }
        }

        if (this.y < localY*64) {
            while (this.y != localY*64) {
                turn(Direction.DOWN);
                fire();
                move();
            }
        } else {
            while (this.y != localY*64) {
                turn(Direction.UP);
                fire();
                move();
            }
        }
    }

    @Override
    public void draw(Graphics g) {

        g.setColor(tankColor);
        g.fillRect(this.getX(), this.getY(), 64, 64);

        g.setColor(towerColor);
        if (this.getDirection() == Direction.UP) {
            g.fillRect(this.getX() + 20, this.getY(), 24, 34);
        } else if (this.getDirection() == Direction.DOWN) {
            g.fillRect(this.getX() + 20, this.getY() + 30, 24, 34);
        } else if (this.getDirection() == Direction.LEFT) {
            g.fillRect(this.getX(), this.getY() + 20, 34, 24);
        } else {
            g.fillRect(this.getX() + 30, this.getY() + 20, 34, 24);
        }
    }

}
