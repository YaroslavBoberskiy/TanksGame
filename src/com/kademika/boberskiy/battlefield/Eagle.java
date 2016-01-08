package com.kademika.boberskiy.battlefield;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by YB on 22.11.2015.
 */
public class Eagle extends BattleFieldAbstractObject {

    public Eagle(int x, int y) {
        super(x, y);
        objectColor = new Color(255, 200, 0);
        try {
            objectImage = ImageIO.read(new File("C:\\Users\\YB\\IdeaProjects\\TanksGameRefactored\\resources\\eagle.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
