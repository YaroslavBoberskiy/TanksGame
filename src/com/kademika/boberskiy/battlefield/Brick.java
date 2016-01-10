package com.kademika.boberskiy.battlefield;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by YB on 22.11.2015.
 */

public class Brick extends BattleFieldAbstractObject {

    public Brick (int x, int y) {
        super(x, y);
        objectColor = new Color(253, 116, 66);
        try {
            objectImage = ImageIO.read(new File("resources/Brick.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
