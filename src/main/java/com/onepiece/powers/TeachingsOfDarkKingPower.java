package com.onepiece.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.onepiece.action.TeachingsOfDarkKingAction;
import com.onepiece.helpers.ModHelper;

public class TeachingsOfDarkKingPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath("TeachingsOfDarkKingPower");


    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    public TeachingsOfDarkKingPower(AbstractCreature owner, int drawAmount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
//        this.amount = drawAmount;
        this.type = AbstractPower.PowerType.BUFF;
        this.amount = drawAmount;

//        this.loadRegion("tools");
        // 添加一大一小两张能力图
        String path128 = "LuffyModRes/img/powers/TeachingsOfDarkKingPower84.png";
        String path48 = "LuffyModRes/img/powers/TeachingsOfDarkKingPower32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        // 首次添加能力更新描述
        this.updateDescription();
        this.priority = 25;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount * 2 + DESCRIPTIONS[2];


    }

    public void atStartOfTurnPostDraw() {
        this.flash();

        for (int i = 0; i < this.amount; i++) {
            int[] A = {2, 2};
            this.addToBot(new TeachingsOfDarkKingAction(this.owner, new DamageInfo((AbstractCreature) this.owner, 2), A));
        }
    }


    //    static {
//        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Tools Of The Trade");
//        NAME = powerStrings.NAME;
//        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
//    }
}
