package com.kademika.boberskiy.battlefield;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by YB on 22.11.2015.
 */
public class Water extends BattleFieldAbstractObject {

    public Water(int x, int y) {
        super(x, y);
        objectColor = new Color(0, 150, 255);
        try {
            objectImage = ImageIO.read(new File("C:\\Users\\YB\\IdeaProjects\\TanksGameRefactored\\resources\\Water.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedImage tmpImg = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) tmpImg.getGraphics();
        g2d.setComposite(AlphaComposite.SrcOver.derive(0.5f));
        g2d.drawImage(objectImage,0,0,null);
        objectImage=tmpImg;

    }
}
