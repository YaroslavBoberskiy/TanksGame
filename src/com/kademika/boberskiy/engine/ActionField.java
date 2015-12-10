package com.kademika.boberskiy.engine;

import com.kademika.boberskiy.battlefield.*;
import com.kademika.boberskiy.tanks.*;

import javax.swing.*;
import javax.swing.Action;
import java.awt.*;
import java.util.Random;

/**
 * Created by YB on 26.10.2015.
 */
public class ActionField extends JPanel {

    private final BattleField battleField;
    private T34 defender;
    private Tiger agressor;
    private final Empty empty = new Empty();
    private Bullet bullet;
    private Action action;

    public ActionField() throws Exception {
        battleField = new BattleField();
        defender = new T34(this, battleField);
        agressor = new Tiger(this, battleField, 512, 128, Direction.DOWN);

        bullet = new Bullet(-100, -100, Direction.NONE);

        JFrame frame = new JFrame("BATTLE FIELD, DAY 7");
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
        defender.turn(Direction.DOWN);
        defender.fire();
        defender.turn(Direction.LEFT);
        agressor.fire();
        agressor.fire();
        agressor.turn(Direction.UP);


    }

    private void processAction(Action a, AbstractTank tank) throws Exception {
        if (a == Action.MOVE) {
            processMove(tank);
        } else if (a == Action.FIRE) {
            processTurn();
            processFire(tank.fire());
        }
    }

    public void processTurn() throws Exception {
        repaint();
    }

    public void processMove(AbstractTank abstractTank) throws Exception {

        this.defender = (T34) abstractTank;
        Direction direction = defender.getDirection();

        int step = 1;
        int covered = 0;

        // check limits x: 0, 513; y: 0, 513

        if ((direction == Direction.UP && abstractTank.getY() == 0) || (direction == Direction.DOWN && abstractTank.getY() >= 512) ||
                (direction == Direction.LEFT && abstractTank.getX() == 0) || (direction == Direction.RIGHT && abstractTank.getX() >= 512)) {
            System.out.println("[Illegal move] direction " + direction + "Tank X: " + abstractTank.getX() + "Tank Y: " + abstractTank.getY());
            return;
        }
        abstractTank.turn(direction);

        while (covered < 64) {
            if (direction == Direction.UP) {
                abstractTank.updateY(-step);
                System.out.println("[move up] direction: " + direction + " tankX: " +
                        abstractTank.getX() + "tankY: " + abstractTank.getY());
            } else if (direction == Direction.DOWN) {
                abstractTank.updateY(step);
                System.out.println("[move down] direction: " + direction + " tankX: " +
                        abstractTank.getX() + "tankY: " + abstractTank.getY());
            } else if (direction == Direction.LEFT) {
                abstractTank.updateX(-step);
                System.out.println("[move left] direction: " + direction + " tankX: " +
                        abstractTank.getX() + "tankY: " + abstractTank.getY());
            } else {
                abstractTank.updateX(step);
                System.out.println("[move right] direction: " + direction + " tankX: " +
                        abstractTank.getX() + "tankY: " + abstractTank.getY());
            }
            covered += step;
            repaint();
            Thread.sleep(abstractTank.getSpeed());
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
                bullet.selfDestroy();
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


            if (!battleField.scanQuadrant(y, x).getClass().getName().contains("Empty")) {
                battleField.updateQuadrant(y, x, empty);
                return true;
            }

            if (processInterceptionCheck(defenderCoordinates, bulletCoordinates)) {
                defender.selfDestroy();
                return true;
            }

            if (processInterceptionCheck(agressorCoordinates, bulletCoordinates)) {
                if (agressor.getArmor() == 0) {
                    agressor.selfDestroy();
                    processSetNewRandomLocation();
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

    String getQuadrant(int x, int y) {

        return y / 64 + "_" + x / 64;
    }

    void  processSetNewRandomLocation() {
        Random random = new Random();
        String[] predefCoordinate = new String []{"128_256", "256_256", "256_448"};
        int x = Integer.parseInt(predefCoordinate[random.nextInt(predefCoordinate.length)].split("_")[1]);
        int y = Integer.parseInt(predefCoordinate[random.nextInt(predefCoordinate.length)].split("_")[0]);
        agressor = new Tiger(this, battleField, x, y, Direction.DOWN);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        battleField.draw(g);
        defender.draw(g);
        agressor.draw(g);
        bullet.draw(g);
    }
}
