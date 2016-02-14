package com.kademika.boberskiy.engine;

import com.kademika.boberskiy.tanks.AbstractTank;
import com.kademika.boberskiy.tanks.Actions;
import com.kademika.boberskiy.tanks.T34;
import com.kademika.boberskiy.tanks.Tiger;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by YB on 10.02.2016.
 */
public class ActionsRecorder {

    public FileWriter writer;
    public File file;

    ArrayList<Object> tankBehaviorScenario;

    public ActionsRecorder(ArrayList<Object> tankBehaviorScenario) {
        this.tankBehaviorScenario = tankBehaviorScenario;
    }

    public void writeActions(AbstractTank tank) {
        if (tank instanceof Tiger) {
            file = new File("resources/agressor_actions.txt");
        }
        if (tank instanceof T34) {
            file = new File("resources/defender_actions.txt");
        }
        if (file.isFile() && !file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (file.isFile() && file.exists()) {
            try {
                writer = new FileWriter(file);
                for (Object str : tankBehaviorScenario) {
                    writer.write(str.toString() + "\n");
                }
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Object> restoreScenarioFromFile(AbstractTank tank) {
        if (tank instanceof Tiger) {
            System.out.println("Restore Tiger Actions");
            file = new File("resources/agressor_actions.txt");
        }
        if (tank instanceof T34) {
            System.out.println("Restore T-34 Actions");
            file = new File("resources/defender_actions.txt");
        }
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("MOVE")) {
                    tankBehaviorScenario.add(Actions.MOVE);
                } else if (line.contains("FIRE")) {
                    tankBehaviorScenario.add(Actions.FIRE);
                } else if (line.contains("DOWN")) {
                    tankBehaviorScenario.add(Direction.DOWN);
                } else if (line.contains("UP")) {
                    tankBehaviorScenario.add(Direction.UP);
                } else if (line.contains("LEFT")) {
                    tankBehaviorScenario.add(Direction.LEFT);
                } else if (line.contains("RIGHT")) {
                    tankBehaviorScenario.add(Direction.RIGHT);
                }
            }
            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("RETURN RESTORED BEH SCEN");
        return tankBehaviorScenario;
    }

}
