package com.onepiece.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.onepiece.helpers.ModHelper;

import static com.onepiece.tag.CustomTags.GUMGUM_ATK;

public class GearThreePower extends AbstractPower {

    // 能力的ID
    public static final String POWER_ID = ModHelper.makePath("GearThreePower");
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean justApplied = false;

    public GearThreePower(AbstractCreature owner, int Amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = Amount;
        this.isTurnBased = true;
        // 添加一大一小两张能力图
        String path128 = "LuffyModRes/img/powers/GearThreePower84.png";
        String path48 = "LuffyModRes/img/powers/GearThreePower32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        // 首次添加能力更新描述
        this.updateDescription();

        Texture newTexture = new Texture("LuffyModRes/img/char/luffy_gear3.png");
        AbstractDungeon.player.img = newTexture;
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
//        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
//        this.description = "sbsbsbsbs";
    }

    @Override
    public void onRemove() {
        AbstractPlayer p = AbstractDungeon.player;
        this.addToBot(new ApplyPowerAction(p, p, new VulnerablePower(p, 2, false), 2));
        this.addToBot(new ApplyPowerAction(p, p, new WeakPower(p, 2, false), 2));
    }

    public void atEndOfRound() {
        if (this.justApplied) {
            this.justApplied = false;
        } else {
            if (this.amount == 0) {
                this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, GearThreePower.POWER_ID));
            } else {
                this.addToBot(new ReducePowerAction(this.owner, this.owner, GearThreePower.POWER_ID, 1));
            }

        }
    }

    public void onVictory() {
        Texture newTexture = new Texture("LuffyModRes/img/char/luffy_gear1.png");

        AbstractDungeon.player.img = newTexture;
    }

}
