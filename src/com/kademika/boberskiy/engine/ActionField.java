package com.kademika.boberskiy.engine;

import com.kademika.boberskiy.battlefield.BattleField;
import com.kademika.boberskiy.battlefield.BattleFieldAbstractObject;
import com.kademika.boberskiy.battlefield.Eagle;
import com.kademika.boberskiy.battlefield.Empty;
import com.kademika.boberskiy.tanks.*;
import javax.swing.*;
import java.awt.*;

public class ActionField extends JPanel {

    private BattleField battleField;
    private T34 defender;
    private Tiger agressor;
    private Bullet bullet;

    public ActionField() throws Exception {
        battleField = new BattleField();
        defender = new T34(this, battleField);
        agressor = new Tiger(this, battleField, 448, 128, Direction.DOWN);
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

        agressor.destroyEagleScenario();
        while (true) {
            if (!agressor.isDestroyed() && !defender.isDestroyed() && battleField.scanQuadrant(7,1).getClass().getName().contains("Eagle")) {
                processAction(agressor.setUp(), agressor);
            } else {
                break;
            }
        }

//        for (Object t : agressor.getTankBehaviorScenario()) {
//            System.out.println(t.toString());
//        }
    }

    private void processAction(Actions a, AbstractTank tank) throws Exception {
        if (a == Actions.MOVE) {
            processMove(tank);
        } else if (a == Actions.FIRE) {
            processTurn(tank);
            processFire(tank.fire(), tank);
        }
    }

    public void processTurn(AbstractTank tank) throws Exception {
        repaint();
    }

    private void processMove(AbstractTank tank) throws Exception {
        if (!tank.isDestroyed()) {
            processTurn(tank);
            Direction direction = tank.getDirection();
            int step = 1;

            for (int i = 0; i < tank.getMovePath(); i++) {
                int covered = 0;

                String tankQuadrant = getQuadrant(tank.getX(), tank.getY());
                int y = Integer.parseInt(tankQuadrant.split("_")[0]);
                int x = Integer.parseInt(tankQuadrant.split("_")[1]);

                if ((direction == Direction.UP && tank.getY() == 0) ||
                        (direction == Direction.DOWN && tank.getY() >= 512) ||
                        (direction == Direction.LEFT && tank.getX() == 0) ||
                        (direction == Direction.RIGHT && tank.getX() >= 512)) {
                    return;
                }

                if (direction == Direction.UP) {
                    y++;
                } else if (direction == Direction.DOWN) {
                    y--;
                } else if (direction == Direction.RIGHT) {
                    x++;
                } else if (direction == Direction.LEFT) {
                    x--;
                }

                BattleFieldAbstractObject bfAbstrObject = battleField.scanQuadrant(y, x);
                if (!(bfAbstrObject instanceof Empty) && !bfAbstrObject.isDestroyed()) {
                    return;
                }

                while (covered < 64) {
                    if (direction == Direction.UP) {
                        tank.updateY(-step);
                    } else if (direction == Direction.DOWN) {
                        tank.updateY(step);
                    } else if (direction == Direction.LEFT) {
                        tank.updateX(-step);
                    } else {
                        tank.updateX(step);
                    }
                    covered += step;

                    repaint();
                    Thread.sleep(tank.getSpeed());
                }
            }
        }
    }

    public void processFire(Bullet bullet, AbstractTank tank) throws Exception {
        if (!tank.isDestroyed()) {
            this.bullet = bullet;
            int step = 1;

            while ((bullet.getX() > -14 && bullet.getX() <= 590) && (bullet.getY() > -14
                    && bullet.getY() <= 590)) {
                if (bullet.getDirection() == Direction.UP) {
                    bullet.updateY(-step);
                } else if (bullet.getDirection() == Direction.DOWN) {
                    bullet.updateY(step);
                } else if (bullet.getDirection() == Direction.LEFT) {
                    bullet.updateX(-step);
                } else {
                    bullet.updateX(step);
                }
                if (processInterception()) {
                    bullet.selfDestroy();
                }
                repaint();
                Thread.sleep(bullet.getSpeed());
                if (bullet.isDestroyed()) {
                    break;
                }
            }
        }
    }

    boolean processInterception() {

        String bulletCoordinates = getQuadrant(bullet.getX(), bullet.getY());
        String defenderCoordinates = getQuadrant(defender.getX(), defender.getY());
        String agressorCoordinates = getQuadrant(agressor.getX(), agressor.getY());

        int y = Integer.parseInt(bulletCoordinates.split("_")[0]);
        int x = Integer.parseInt(bulletCoordinates.split("_")[1]);

        if (y >= 0 && y < 9 && x >= 0 && x < 9) {

            if (!battleField.scanQuadrant(y, x).isDestroyed() && !(battleField.scanQuadrant(y, x) instanceof Empty)) {
                battleField.destroyObject(y, x);
                return true;
            }

            if (!defender.isDestroyed() && processInterceptionCheck(defenderCoordinates, bulletCoordinates)) {
                defender.selfDestroy();
                return true;
            }

            if (!agressor.isDestroyed() && processInterceptionCheck(agressorCoordinates, bulletCoordinates)) {
                agressor.selfDestroy();
                return true;
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
                return true;
            }
        }
        return false;
    }

    String getQuadrant(int x, int y) {
        return y / 64 + "_" + x / 64;
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
