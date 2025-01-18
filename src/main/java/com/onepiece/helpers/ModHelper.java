package com.onepiece.helpers;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.onepiece.powers.ElasticPower;

public class ModHelper {

    public static String makePath(String id) {
        return "LuffyMod:" + id;
    }

    public static boolean HasEnoughElasticPower(AbstractPlayer p,int need) {

        if(p.hasPower(ElasticPower.POWER_ID)){
            int num =  p.getPower(ElasticPower.POWER_ID).amount;
            if(num >= need){
                p.getPower(ElasticPower.POWER_ID).amount = num - need;
                return true;
            }
        }
        return false;
    }
}
