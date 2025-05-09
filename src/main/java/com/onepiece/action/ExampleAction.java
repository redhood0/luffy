package com.onepiece.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ExampleAction extends AbstractGameAction {
    // 伤害信息
    public DamageInfo info;

    public ExampleAction(AbstractMonster target, DamageInfo info) {
        this.target = target;
        this.info = info;
    }

    @Override
    public void update() {
        this.target.damage(this.info);
        if ((this.target.isDying || this.target.currentHealth <= 0) && !this.target.halfDead
                && !this.target.hasPower("Minion")) {
            this.addToTop(new DrawCardAction(1));
        }
        this.isDone = true;
    }
}
