package com.kademika.boberskiy.tanksgame;

import javax.swing.*;
import java.awt.*;

/**
 * Created by YB on 26.10.2015.
 */
public class ActionField extends JPanel {

    private boolean COLORDED_MODE = true;
    private BattleField battleField;
    private TankDefender defender;
    private TankAgressor agressor;

    private ActionField af;
    private BattleField bf;
    private Bullet bullet;

    public ActionField() throws Exception {
        battleField = new BattleField();
        defender = new TankDefender(this, battleField);
        agressor = new TankAgressor(this, battleField);
        agressor.setNewRandomLocation();

        bullet = new Bullet(-100, -100, Direction.NONE);

        JFrame frame = new JFrame("BATTLE FIELD, DAY 2");
        frame.setLocation(750, 150);
        frame.setMinimumSize(new Dimension(battleField.getBfWidth(), battleField.getBfHeight() + 22));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);
    }

    void runTheGame() throws Exception {

        defender.fire();
        defender.fire();
        defender.fire();
        defender.fire();

        //tank.clean();
        //tank.moveRandom();

    }

    public void processTurn(Tank tank) throws Exception {
        repaint();
    }

    public void processMove(Tank tank) throws Exception {

        this.defender = (TankDefender) tank;
        Direction direction = defender.getDirection();

        int step = 1;
        int covered = 0;

        // check limits x: 0, 513; y: 0, 513

        if ((direction == Direction.UP && tank.getY() == 0) || (direction == Direction.DOWN && tank.getY() >= 512) ||
                (direction == Direction.LEFT && tank.getX() == 0) || (direction == Direction.RIGHT && tank.getX() >= 512)) {
            System.out.println("[Illegal move] direction " + direction + "Tank X: " + tank.getX() + "Tank Y: " + tank.getY());
            return;
        }
        tank.turn(direction);

        while (covered < 64) {
            if (direction == Direction.UP) {
                tank.updateY(-step);
                System.out.println("[move up] direction: " + direction + " tankX: " +
                        tank.getX() + "tankY: " + tank.getY());
            } else if (direction == Direction.DOWN) {
                tank.updateY(step);
                System.out.println("[move down] direction: " + direction + " tankX: " +
                        tank.getX() + "tankY: " + tank.getY());
            } else if (direction == Direction.LEFT) {
                tank.updateX(-step);
                System.out.println("[move left] direction: " + direction + " tankX: " +
                        tank.getX() + "tankY: " + tank.getY());
            } else {
                tank.updateX(step);
                System.out.println("[move right] direction: " + direction + " tankX: " +
                        tank.getX() + "tankY: " + tank.getY());
            }
            covered += step;
            repaint();
            Thread.sleep(tank.getSpeed());
        }
    }

    public void processFire(Bullet bullet) throws Exception {

        this.bullet = bullet;

        int step = 1;

        while ((bullet.getX() > -14 && bullet.getX() <= 590) && (bullet.getY() > -14
                && bullet.getY() <= 590)) {
            if (bullet.getDirection() == Direction.UP) {
                bullet.updateY(-step);
                //System.out.println("bullet-X: "+ bullet.getX()+ "bullet-Y: "+bullet.getY());
            } else if (bullet.getDirection() == Direction.DOWN) {
                bullet.updateY(step);
                //System.out.println("bullet-X: "+ bullet.getX()+ "bullet-Y: "+bullet.getY());
            } else if (bullet.getDirection() == Direction.LEFT) {
                bullet.updateX(-step);
                //System.out.println("bullet-X: "+ bullet.getX()+ "bullet-Y: "+bullet.getY());
            } else {
                bullet.updateX(step);
                //System.out.println("bullet-X: "+ bullet.getX()+ "bullet-Y: "+bullet.getY());
            }
            if (processInterception()) {
                bullet.destroy();
            }
            repaint();
            Thread.sleep(bullet.getSpeed());
        }
    }

    boolean processInterception() {

        String bulletCoordinates = getQuadrant(bullet.getX(), bullet.getY());
        String defenderCoordinates = getQuadrant(defender.getX(), defender.getY());
        String agressorCoordinates = getQuadrant(agressor.getX(), agressor.getY());

        int y = Integer.parseInt(bulletCoordinates.split("_")[0]);
        int x = Integer.parseInt(bulletCoordinates.split("_")[1]);

        if (y >= 0 && y < 9 && x >= 0 && x < 9) {

            if (!battleField.scanQuadrant(y, x).trim().isEmpty()) {
                battleField.updateQuadrant(y, x, " ");
                return true;
            }

            if (processInterceptionCheck(defenderCoordinates, bulletCoordinates)) {
                defender.selfDestroy();
                return true;
            }

            if (processInterceptionCheck(agressorCoordinates, bulletCoordinates)) {
                if (agressor.getArmor() == 0) {
                    agressor.selfDestroy();
                    return true;
                }
                else {
                    agressor.setArmor(agressor.getArmor() - 1);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean processInterceptionCheck(String tankCoordinates, String bulletCoordinates) {
        int bY = Integer.parseInt(bulletCoordinates.split("_")[0]);
        int bX = Integer.parseInt(bulletCoordinates.split("_")[1]);

        int tY = Integer.parseInt(tankCoordinates.split("_")[0]);
        int tX = Integer.parseInt(tankCoordinates.split("_")[1]);

        if (bX >= 0 & bX < 9 & bY >= 0 & bY < 9 & tX >= 0 & tX < 9 & tY >= 0 & tY < 9) {
            if ((bX == tX) && (bY == tY)) {
                System.out.println("bX = " + bX + "tX = " + tX + "bY = " + bY + "tY = " + tY);
                return true;
            }
        }
        return false;
    }

    public String getQuadrant(int x, int y) {

        return y / 64 + "_" + x / 64;
    }

    public String getQuadrantXY(int v, int h) {

        return (v - 1) * 64 + "_" + (h - 1) * 64;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int i = 0;
        Color cc;
        for (int v = 0; v < 9; v++) {
            for (int h = 0; h < 9; h++) {
                if (COLORDED_MODE) {
                    if (i % 2 == 0) {
                        cc = new Color(252, 241, 177);
                    } else {
                        cc = new Color(233, 243, 255);
                    }
                } else {
                    cc = new Color(180, 180, 180);
                }
                i++;
                g.setColor(cc);
                g.fillRect(h * 64, v * 64, 64, 64);
            }
        }

        for (int j = 0; j < battleField.getDimensionY(); j++) {
            for (int k = 0; k < battleField.getDimensionX(); k++) {
                if (battleField.scanQuadrant(j, k).equals("B")) {
                    String coordinates = getQuadrantXY(j + 1, k + 1);
                    int separator = coordinates.indexOf("_");
                    int y = Integer.parseInt(coordinates
                            .substring(0, separator));
                    int x = Integer.parseInt(coordinates
                            .substring(separator + 1));
                    g.setColor(new Color(0, 0, 255));
                    g.fillRect(x, y, 64, 64);
                }
            }
        }

        // DEFENDER

        g.setColor(new Color(255, 0, 0));
        g.fillRect(defender.getX(), defender.getY(), 64, 64);

        g.setColor(new Color(0, 255, 0));
        if (defender.getDirection() == Direction.UP) {
            g.fillRect(defender.getX() + 20, defender.getY(), 24, 34);
        } else if (defender.getDirection() == Direction.DOWN) {
            g.fillRect(defender.getX() + 20, defender.getY() + 30, 24, 34);
        } else if (defender.getDirection() == Direction.LEFT) {
            g.fillRect(defender.getX(), defender.getY() + 20, 34, 24);
        } else {
            g.fillRect(defender.getX() + 30, defender.getY() + 20, 34, 24);
        }

        // AGRESSOR

        g.setColor(new Color(255, 0, 255));
        g.fillRect(agressor.getX(), agressor.getY(), 64, 64);

        g.setColor(new Color(255, 255, 0));
        if (agressor.getDirection() == Direction.UP) {
            g.fillRect(agressor.getX() + 20, agressor.getY(), 24, 34);
        } else if (defender.getDirection() == Direction.DOWN) {
            g.fillRect(agressor.getX() + 20, agressor.getY() + 30, 24, 34);
        } else if (agressor.getDirection() == Direction.LEFT) {
            g.fillRect(agressor.getX(), agressor.getY() + 20, 34, 24);
        } else {
            g.fillRect(agressor.getX() + 30, agressor.getY() + 20, 34, 24);
        }

        // BULLET

        g.setColor(new Color(0, 255, 255));
        g.fillRect(bullet.getX(), bullet.getY(), 14, 14);

    }

}
