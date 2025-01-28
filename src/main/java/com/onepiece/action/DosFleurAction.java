package com.onepiece.action;

import basemod.cardmods.RetainMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DosFleurAction extends AbstractGameAction {

    public boolean upgraded = false;
    public String desc = "";

    public DosFleurAction(boolean upgraded) {
        this.target = target;
//        this.info = info;
        this.upgraded = upgraded;
    }

    public DosFleurAction(boolean upgraded,String desc) {
        this.target = target;
//        this.info = info;
        this.upgraded = upgraded;
        this.desc = desc;
    }

    @Override
    public void update() {
        // 获取刚抽到的卡（需延迟一帧确保抽卡完成）
        for (AbstractCard card : DrawCardAction.drawnCards) {
            if (!card.selfRetain && !card.retain) {
                if (upgraded) {
                    CardModifierManager.addModifier(card, new RetainMod());
                } else {
                    card.retain = true;
//                    card.rawDescription = card.rawDescription+" NL " + desc;
//                    card.initializeDescription(); // 更新卡牌描述显示"保留"
//                    card.onMoveToDiscard();
                }
            }
        }

        this.isDone = true;
    }
}
