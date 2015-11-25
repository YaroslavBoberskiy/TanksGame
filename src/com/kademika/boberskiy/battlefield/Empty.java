package com.kademika.boberskiy.battlefield;

import java.awt.*;

/**
 * Created by YB on 23.11.2015.
 */
public class Empty extends BattleFieldAbstractObject {

    protected Color emptyColor = new Color(255, 255, 255);

    public Empty() {
        objectColor = emptyColor;
    }

}
