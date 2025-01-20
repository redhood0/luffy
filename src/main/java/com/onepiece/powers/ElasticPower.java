package com.onepiece.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.onepiece.helpers.ModHelper;

public class ElasticPower extends AbstractPower {
    // 能力的ID
    public static final String POWER_ID = ModHelper.makePath("ElasticPower");
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ElasticPower(AbstractCreature owner, int Amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = Amount;

        // 添加一大一小两张能力图
        String path128 = "LuffyModRes/img/powers/Test84.png";
        String path48 = "LuffyModRes/img/powers/Test32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        // 首次添加能力更新描述
        this.updateDescription();
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
//        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        this.description = DESCRIPTIONS[0]+DESCRIPTIONS[1];
    }

    public static void addAmount(int amount) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ElasticPower(AbstractDungeon.player, amount), amount)
        );
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        // 在回合结束时移除该 Power
        if(isPlayer){
            flash(); // 播放闪光效果
            AbstractDungeon.actionManager.addToBottom(
                    new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID)
            );
        }
    }
}
