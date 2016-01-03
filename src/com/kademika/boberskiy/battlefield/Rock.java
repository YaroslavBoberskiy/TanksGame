package com.kademika.boberskiy.battlefield;

import java.awt.*;

/**
 * Created by YB on 22.11.2015.
 */
public class Rock extends BattleFieldAbstractObject {

    private final Color rockColor = new Color(150, 100, 100);

    public Rock(int x, int y) {
        super(x, y);
        objectColor = rockColor;
    }

}
