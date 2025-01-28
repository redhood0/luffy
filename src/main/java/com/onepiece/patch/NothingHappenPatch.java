package com.onepiece.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class NothingHappenPatch {


//    @SpirePatch(clz = AbstractPlayer.class, method = "damage")
    public static class NothingHappenPatch_player {
//        @SpireInsertPatch(rloc = 149)
        public static SpireReturn<Void> Insert(AbstractPlayer _inst, DamageInfo info) {

//            if (_inst.hasRelic(Renpi.ID)) {
//                if (((Renpi) _inst.getRelic(Renpi.ID)).counter == -1) {
//                    _inst.currentHealth = 0;
//                    _inst.getRelic(Renpi.ID).onTrigger();
//
//                    return SpireReturn.Return();
//                }
//            }
            return SpireReturn.Continue();
        }
    }
}