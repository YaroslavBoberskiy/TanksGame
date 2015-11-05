package com.kademika.boberskiy.tanksgame;

/**
 * Created by YB on 27.10.2015.
 */
public class Tiger extends Tank {

    private int armor;

    public Tiger(ActionField af, BattleField bf) {
        super(af, bf);
        armor = 1;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

}
