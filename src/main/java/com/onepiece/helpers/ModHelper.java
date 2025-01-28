package com.onepiece.helpers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.onepiece.powers.BusoshokuHakiPower;
import com.onepiece.powers.ElasticPower;

public class ModHelper {

    public static String makePath(String id) {
        return "LuffyMod:" + id;
    }

    public static boolean HasEnoughElasticPower(AbstractPlayer p, int need) {

        if (p.hasPower(ElasticPower.POWER_ID)) {
            int num = p.getPower(ElasticPower.POWER_ID).amount;
            if (num >= need) {
                AbstractDungeon.actionManager.addToBottom(
                        new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ElasticPower(AbstractDungeon.player, -need), -need)
                );
//                p.getPower(ElasticPower.POWER_ID).amount = num - need;
//                EventBus eventBus = MyMod.getEventBus();
//                eventBus.publish(new CustomEvent("Player gained energy!"));
                return true;
            }
        }
        return false;
    }

    public static int GetElasticPowerNum(AbstractPlayer p) {

        if (p.hasPower(ElasticPower.POWER_ID)) {
            int num = p.getPower(ElasticPower.POWER_ID).amount;
            return num;
        }
        return 0;
    }

    public static int UseHaki(AbstractPlayer p,int need) {
        if (p.hasPower(BusoshokuHakiPower.POWER_ID)) {
            int num = p.getPower(BusoshokuHakiPower.POWER_ID).amount;
            if (num >= need) {
                AbstractDungeon.actionManager.addToBottom(
                        new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BusoshokuHakiPower(AbstractDungeon.player, -need), -need)
                );
//                p.getPower(ElasticPower.POWER_ID).amount = num - need;
//                EventBus eventBus = MyMod.getEventBus();
//                eventBus.publish(new CustomEvent("Player gained energy!"));
                return need;
            }else {
                AbstractDungeon.actionManager.addToBottom(
                        new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BusoshokuHakiPower(AbstractDungeon.player, -num), -num)
                );
                return num;
            }
        }else {
            return 0;
        }

    }

//     if (this.amount == 0) {
//        this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, "FreeAttackPower"));
//    }
}
