package com.onepiece.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.onepiece.powers.SuperVulnerablePower;
import com.onepiece.powers.SuperWeakPower;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class YohohohoAction extends AbstractGameAction {

    // 随机的 Debuff 列表
    private static final List<Class<? extends AbstractPower>> DEBUFFS = new ArrayList<>();

    static {

        DEBUFFS.add(WeakPower.class);      // 虚弱
        DEBUFFS.add(VulnerablePower.class); // 易伤
        DEBUFFS.add(PoisonPower.class);    // 中毒
        DEBUFFS.add(StrengthPower.class);    // -1攻
        DEBUFFS.add(ConstrictedPower.class);    //缠绕
//        DEBUFFS.add(SuperVulnerablePower.class);    //超级易伤
//        DEBUFFS.add(SuperWeakPower.class);    //超级虚弱
        // 你可以根据需要添加更多 Debuff，以后可以加个点燃
    }

    public YohohohoAction() {
        this.actionType = ActionType.DEBUFF; // 设置 Action 类型
    }

    @Override
    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true; // 如果怪物已经死亡，结束 Action
            return;
        }

        // 遍历所有怪物
        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!monster.isDeadOrEscaped()) {
                AbstractPower debuff;
                // 随机选择一个 Debuff
                int index = AbstractDungeon.cardRandomRng.random(DEBUFFS.size() - 1);
                switch (index) {
                    case 0: {
                        debuff = new WeakPower(monster, 1, false);
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, AbstractDungeon.player, debuff, 1));
                        break;
                    }
                    case 1: {
                        debuff = new VulnerablePower(monster, 1, false);
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, AbstractDungeon.player, debuff, 1));

                        break;
                    }
                    case 2: {
                        debuff = new PoisonPower(AbstractDungeon.player, monster, 1);
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, AbstractDungeon.player, debuff, 1));

                        break;
                    }
                    case 3: {
                        debuff = new StrengthPower(monster, -1);
                        this.addToBot(new ApplyPowerAction(monster, AbstractDungeon.player, new StrengthPower(monster, -1), -1));
                        break;
                    }
                    case 4: {
                        debuff = new ConstrictedPower(monster,AbstractDungeon.player, 1);
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, AbstractDungeon.player, debuff, 1));

                    }
//                    case 5: {
//                        debuff = new SuperVulnerablePower(monster, 1);
//                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, AbstractDungeon.player, debuff, 1));
//
//                    }
//                    case 6: {
//                        debuff = new SuperWeakPower(monster, 1);
//                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, AbstractDungeon.player, debuff, 1));
//
//                    }
                    default: {
                        debuff = null;
                        break;
                    }

                }
                if (debuff != null) {

                }
            }
        }

        this.isDone = true; // 标记 Action 完成
    }
}
