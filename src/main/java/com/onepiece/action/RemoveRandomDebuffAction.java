package com.onepiece.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;
import java.util.List;

public class RemoveRandomDebuffAction extends AbstractGameAction {
    public RemoveRandomDebuffAction(AbstractCreature target) {
        this.target = target; // 目标通常是玩家自己
        this.actionType = ActionType.DEBUFF;
    }

    @Override
    public void update() {
        if (this.target == null || this.target.isDeadOrEscaped()) {
            this.isDone = true;
            return;
        }

        // 获取目标的所有Debuff
        List<AbstractPower> debuffs = new ArrayList<>();
        for (AbstractPower power : this.target.powers) {
            if (power.type == AbstractPower.PowerType.DEBUFF) {
                debuffs.add(power);
            }
        }

        // 随机移除一个Debuff
        if (!debuffs.isEmpty()) {
            AbstractPower debuffToRemove = debuffs.get(AbstractDungeon.cardRandomRng.random(debuffs.size() - 1));
            this.target.powers.remove(debuffToRemove);
            debuffToRemove.onRemove(); // 触发移除时的效果
        }

        this.isDone = true;
    }
}
