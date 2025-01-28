package com.onepiece.powers;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.onepiece.helpers.ModHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GiantFusenLv3Power extends AbstractPower {

    // 能力的ID
    public static final String POWER_ID = ModHelper.makePath("GiantFusenLv3Power");
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Logger log = LoggerFactory.getLogger(GiantFusenLv3Power.class);

    public GiantFusenLv3Power(AbstractCreature owner, int Amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = AbstractPower.PowerType.BUFF;

        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = Amount;

        // 添加一大一小两张能力图
        String path128 = "LuffyModRes/img/powers/GiantFusenLv3Power84.png";
        String path48 = "LuffyModRes/img/powers/GiantFusenLv3Power32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        // 首次添加能力更新描述
        this.updateDescription();
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
//        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        this.description = DESCRIPTIONS[0]+ this.amount + DESCRIPTIONS[1];
    }
    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        // 检查是否是敌人造成的伤害
        if (info.owner != null && info.type == DamageInfo.DamageType.NORMAL && info.owner != this.owner) {
            this.flash(); // 播放视觉效果
            // 对敌人造成等量固定伤害
            AbstractDungeon.actionManager.addToTop(new DamageAction(info.owner, new DamageInfo(this.owner, info.output, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SHIELD));
            // 减少自身层数
            this.addToTop(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
            // 如果层数减到 0，移除能力
            if (this.amount <= 1) {
                this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
            }
        }
        return damageAmount; // 返回原始伤害值
    }

//    public void atEndOfRound() {
//        this.addToBot(new ReducePowerAction(this.owner, this.owner, GearThreePower.POWER_ID, 1));
//
//    }


}
