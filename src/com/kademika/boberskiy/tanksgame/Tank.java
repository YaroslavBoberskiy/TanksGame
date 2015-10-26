package com.kademika.boberskiy.tanksgame;

import java.util.Random;

/**
 * Created by YB on 26.10.2015.
 */
public class Tank {

    protected int speed = 10;
    private int x;
    private int y;
    private ActionField af;
    private BattleField bf;
    private Direction direction;

    public Tank (ActionField af, BattleField bf) {

        this(af, bf, 128, 128, Direction.RIGHT);
    }

    public Tank(ActionField af, BattleField bf, int x, int y, Direction direction) {
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
        af.processTurn(this);
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

    public void move() throws Exception {
        af.processMove(this);
    }

    public void clean () throws Exception {
        moveToQuadrant(0, 0);

        for (int i = 0; i < bf.getDimensionX(); i ++) {
            turn(Direction.RIGHT);
            if (bf.scanQuadrant(0, i).equals("B")) {
                fire();
            }
        }

        for (int i = 0; i < bf.getDimensionX(); i ++) {
            turn(Direction.DOWN);
            for (int j = 0; j < bf.getDimensionY(); j ++) {
                if (bf.scanQuadrant(j, i).equals("B")) {
                    fire();
                }
            }
            turn(Direction.RIGHT);
            move();
        }
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

    public void moveRandom () throws Exception {
        Random r = new Random();
        int i;
        while (true) {
            i = r.nextInt(5);
            if (i > 0 && i == 1) {
                turn(Direction.UP);
                move();
            } else if (i > 0 && i == 2) {
                turn(Direction.DOWN);
                move();
            } else if (i > 0 && i == 3) {
                turn(Direction.LEFT);
                move();
            } else {
                turn(Direction.RIGHT);
                move();
            }
        }
    }

    public void selfDestroy () {
        updateX (-100*64);
        updateY (-100*64);
        af.repaint();
    }

}
