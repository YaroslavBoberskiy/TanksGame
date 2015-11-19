package com.kademika.boberskiy.tanksgame;

import java.awt.*;
import java.util.Random;

/**
 * Created by YB on 05.11.2015.
 */
public class Tiger extends AbstractTank {

    private ActionField af;
    private int armor = 1;

    public Tiger(ActionField af, BattleField bf) {
        super(af, bf);
    }

    public Tiger(ActionField af, BattleField bf, int x, int y, Direction direction) {
        super(af, bf, x, y, direction);
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    @Override
    public void draw(Graphics g) {

        // AGRESSOR

        g.setColor(new Color(255, 0, 255));
        g.fillRect(this.getX(), this.getY(), 64, 64);

        g.setColor(new Color(255, 255, 0));
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

    @Override
    public void selfDestroy() {
        updateX (-100*64);
        updateY (-100*64);
    }
}
