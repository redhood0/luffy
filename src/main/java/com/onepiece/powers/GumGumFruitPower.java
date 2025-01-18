package com.onepiece.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.onepiece.helpers.ModHelper;

public class GumGumFruitPower extends AbstractPower {
    // 能力的ID
    public static final String POWER_ID = ModHelper.makePath("GumGumFruitPower");
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public GumGumFruitPower(AbstractCreature owner, int Amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = Amount;

        // 添加一大一小两张能力图
        String path128 = "LuffyModRes/img/powers/GumGumFruitPower84.png";
        String path48 = "LuffyModRes/img/powers/GumGumFruitPower32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        // 首次添加能力更新描述
        this.updateDescription();
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
//        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        this.description = DESCRIPTIONS[0];
    }

    @Override
    // 被攻击时
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.owner != null && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS) {


            // 如果没有受到伤害，表示格挡住了
            if (damageAmount <= 0) {
                flash(); // 播放闪光效果
                AbstractDungeon.actionManager.addToBottom(
                        new ApplyPowerAction(this.owner, this.owner, new ElasticPower(this.owner, info.output), info.output)
                );
            }
        }
        return damageAmount;
    }




//    @Override
//    public int onAttackToChangeDamage(DamageInfo info, int damageAmount) {
//        if (info.owner != null && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS) {
//            // 如果玩家成功格挡伤害
//            if (damageAmount <= this.owner.currentBlock) {
//                flash(); // 播放闪光效果
//                AbstractDungeon.actionManager.addToBottom(
//                        new ApplyPowerAction(this.owner, this.owner, new ElasticPower(this.owner, this.owner.currentBlock), 1)
//                );
//            }
//        }
//        return damageAmount;
//    }
}