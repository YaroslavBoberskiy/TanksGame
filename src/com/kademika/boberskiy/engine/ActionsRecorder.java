package com.kademika.boberskiy.engine;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by YB on 10.02.2016.
 */
public class ActionsRecorder {

    public FileWriter writer;
    public File file;

    ArrayList<Object> tankBehaviorScenario;

     public ActionsRecorder (ArrayList<Object> tankBehaviorScenario) {
        this.tankBehaviorScenario = tankBehaviorScenario;
    }

    public void writeAgressorToFile () {
        file = new File("resources/agressor_actions.txt");
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
                for(Object str: tankBehaviorScenario) {
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

    public void writeDefenderToFile () {
        file = new File("resources/defender_actions.txt");
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
                for(Object str: tankBehaviorScenario) {
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
}
