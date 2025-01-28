package com.onepiece.powers;

import basemod.AutoAdd;
import basemod.devcommands.power.Power;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PenNibPower;
import com.onepiece.helpers.ModHelper;
public class SakuraFlowPower extends AbstractPower {
    // 能力的ID
    public static final String POWER_ID = ModHelper.makePath("SakuraFlowPower");
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public SakuraFlowPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = -1;

        // 添加一大一小两张能力图
        String path128 = "LuffyModRes/img/powers/SakuraFlowPower84.png";
        String path48 = "LuffyModRes/img/powers/SakuraFlowPower32.png";
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

//    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {

    /// /        addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player,AbstractDungeon.player,SakuraFlowPower.POWER_ID));
//        if (type == DamageInfo.DamageType.NORMAL) {
//
//            return damage * 2.0F;
//        } else {
//            return damage;
//        }
//
//    }
//    public void onUseCard(AbstractCard card, UseCardAction action) {
//        if (card.type == AbstractCard.CardType.ATTACK && !card.purgeOnUse) {
//            this.flash();
//            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, SakuraFlowPower.POWER_ID));
//        }
//    }


    private  AbstractCreature target = null;
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        this.target = target;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, SakuraFlowPower.POWER_ID));
        }

    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if(target != null) {
            return damage * 2;
        }
        return damage * 2;
//        return type == DamageInfo.DamageType.NORMAL ? damage * 2.0F : damage;
    }

}
