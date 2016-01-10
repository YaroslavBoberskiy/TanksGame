package com.kademika.boberskiy.battlefield;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by YB on 23.11.2015.
 */
public class Empty extends BattleFieldAbstractObject {

    public Empty(int x, int y) {
        super(x, y);
        objectColor = new Color(255, 255, 255, 0);
        try {
            objectImage = ImageIO.read(new File("resources/Empty.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
