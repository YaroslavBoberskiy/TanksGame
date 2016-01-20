package com.kademika.boberskiy.tanks;
import com.kademika.boberskiy.battlefield.*;
import com.kademika.boberskiy.engine.ActionField;
import com.kademika.boberskiy.engine.Destroyable;
import com.kademika.boberskiy.engine.Direction;
import com.kademika.boberskiy.engine.Drawable;
import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * Created by YB on 26.10.2015.
 */
public abstract class AbstractTank implements Drawable, Destroyable, Tankable, ImageObserver {

    final private int speed = 10;
    protected int movePath = 1;
    private int x;
    private int y;
    private int tankXQuadrant;
    private int tankYQuadrant;
    private Direction direction;
    private boolean destroyed;
    private BattleFieldAbstractObject bfObject;
    Color tankColor;
    Color towerColor;
    Image tankImageLeft;
    Image tankImageRight;
    Image tankImageUp;
    Image tankImageDown;
    public BattleField bf;

    AbstractTank(BattleField bf) {
        this(bf, 512, 0, Direction.RIGHT);
        this.destroyed = false;
    }

    AbstractTank(BattleField bf, int x, int y, Direction direction) {
        this.bf = bf;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.destroyed = false;
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

    public void turn(Direction direction) {
        this.direction = direction;
    }

    public Bullet fire() {
        int bulletX = -100;
        int bulletY = -100;
        if (direction == Direction.UP && getY() > bf.getBF_MIN_COORDINATE()) {
            bulletX = x + 25;
            bulletY = y;
        } else if (direction == Direction.DOWN && getY() < bf.getBF_MAX_COORDINATE()) {
            bulletX = x + 25;
            bulletY = y + 64;
        } else if (direction == Direction.LEFT && getX() > bf.getBF_MIN_COORDINATE()) {
            bulletX = x;
            bulletY = y + 25;
        } else if (direction == Direction.RIGHT && getX() < bf.getBF_MAX_COORDINATE()) {
            bulletX = x + 64;
            bulletY = y + 25;
        }
        return new Bullet(bulletX, bulletY, direction);
    }

    public BattleFieldAbstractObject getObjectInFrontOfTank (int y, int x) {

        if (direction == Direction.DOWN && y < 8) {
            bfObject = bf.scanQuadrant(y+1, x);
        } else if (direction == Direction.UP && y > 0) {
            bfObject = bf.scanQuadrant(y-1, x);
        } else if (direction == Direction.LEFT && x > 0) {
            bfObject = bf.scanQuadrant(y, x-1);
        } else if (direction == Direction.RIGHT && x < 8) {
            bfObject = bf.scanQuadrant(y, x+1);
        } else {
            bfObject = bf.scanQuadrant(y, x);
        }
        return bfObject;
    }

    public String scanFrontPath(Direction direction, int y, int x) {
        String pathCode = "";
        if (x < 9 && y < 9 && x >= 0 && y >= 0) {
            if (direction.equals(Direction.LEFT)) {
                pathCode = "";
                for (; x >= 0; x--) {
                    pathCode += getFieldObjectID(y, x);
                }
            } else if (direction.equals(Direction.RIGHT)) {
                pathCode = "";
                for (; x < 9; x++) {
                    pathCode += getFieldObjectID(y, x);
                }
            } else if (direction.equals(Direction.DOWN)) {
                pathCode = "";
                for (; y < 9; y++) {
                    pathCode += getFieldObjectID(y, x);
                }
            } else if (direction.equals(Direction.UP)) {
                pathCode = "";
                for (; y >= 0; y--) {
                    pathCode += getFieldObjectID(y, x);
                }
            }
        }

        return pathCode;
    }

    public String getFieldObjectID(int y, int x) {

        if (checkEnemyLocation(y, x) == true) {
            return "T";
        } else if (bf.scanQuadrant(y, x) instanceof Eagle) {
            return "H";
        } else if (bf.scanQuadrant(y, x) instanceof Rock) {
            return "R";
        } else if (bf.scanQuadrant(y, x) instanceof Water) {
            return "W";
        } else if (bf.scanQuadrant(y, x) instanceof Brick) {
            return "B";
        } else if (bf.scanQuadrant(y, x) instanceof Empty) {
            return "E";
        } else {
            return "";
        }
    }


    public void move() {
    }

    public Image getTankImageLeft() {
        return tankImageLeft;
    }

    public Image getTankImageRight() {
        return tankImageRight;
    }

    public Image getTankImageUp() {
        return tankImageUp;
    }

    public Image getTankImageDown() {
        return tankImageDown;
    }

    public int getTankXQuadrant() {
        return this.getX()/64;
    }

    public int getTankYQuadrant() {
        return this.getY()/64;
    }

    public void updateX(int x) {
        this.x += x;
    }

    public void updateY(int y) {
        this.y += y;
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
            //g.setColor(tankColor);
            //g.fillRect(this.getX(), this.getY(), 64, 64);
            //g.setColor(towerColor);
            if (this.getDirection() == Direction.UP) {
                //g.fillRect(this.getX() + 20, this.getY(), 24, 34);
                g.drawImage(this.getTankImageUp(), this.getX(), this.getY(), 64, 64, this);
            } else if (this.getDirection() == Direction.DOWN) {
                //g.fillRect(this.getX() + 20, this.getY() + 30, 24, 34);
                g.drawImage(this.getTankImageDown(), this.getX(), this.getY(), 64, 64, this);
            } else if (this.getDirection() == Direction.LEFT) {
                //g.fillRect(this.getX(), this.getY() + 20, 34, 24);
                g.drawImage(this.getTankImageLeft(), this.getX(), this.getY(), 64, 64, this);
            } else {
                //g.fillRect(this.getX() + 30, this.getY() + 20, 34, 24);
                g.drawImage(this.getTankImageRight(), this.getX(), this.getY(), 64, 64, this);
            }
        }
    }

    @Override
    public int getMovePath() {
        return movePath;
    }

    public abstract boolean checkEnemyLocation(int y, int x);
}
