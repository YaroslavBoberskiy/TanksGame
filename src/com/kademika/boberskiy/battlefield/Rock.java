package com.kademika.boberskiy.battlefield;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by YB on 22.11.2015.
 */
public class Rock extends BattleFieldAbstractObject {

    private final Color rockColor = new Color(150, 100, 100);

    public Rock(int x, int y) {
        super(x, y);
        objectColor = rockColor;
        try {
            objectImage = ImageIO.read(new File("C:\\Users\\YB\\IdeaProjects\\TanksGameRefactored\\resources\\Rock.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
