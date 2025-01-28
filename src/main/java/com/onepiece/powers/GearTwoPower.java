package com.onepiece.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.onepiece.helpers.ModHelper;
import com.onepiece.tag.CustomTags;

import static com.onepiece.tag.CustomTags.GUMGUM_ATK;

public class GearTwoPower extends AbstractPower {
    // 能力的ID
    public static final String POWER_ID = ModHelper.makePath("GearTwoPower");
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public GearTwoPower(AbstractCreature owner, int Amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = Amount;

        // 添加一大一小两张能力图
        String path128 = "LuffyModRes/img/powers/GearTwoPower84.png";
        String path48 = "LuffyModRes/img/powers/GearTwoPower32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        // 首次添加能力更新描述
        this.updateDescription();

        Texture newTexture = new Texture("LuffyModRes/img/char/luffy_gear2.png");
        AbstractDungeon.player.img = newTexture;
    }



    // 能力在更新时如何修改描述
    public void updateDescription() {
//        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        this.description = DESCRIPTIONS[0] + this.amount / 5 + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    }

//    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {

    /// /        if(card.hasTag(GUMGUM_ATK)){
    /// /            damage += 2;
    /// /        }
//        //多打一段？
//        if (card.hasTag(GUMGUM_ATK)) {
//            this.addToBot(new DamageAction());
//        }
//        return this.atDamageGive(damage, type);
//    }
    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        // 检查卡牌是否带有"GUMGUM"标签
        if (card.tags.contains(GUMGUM_ATK)) {
            flash(); // 播放一个视觉效果
            // 触发额外攻击
            for (int i = 0; i < this.amount / 5; i++) {
                AbstractDungeon.actionManager.addToBottom(
                        new DamageAction(
                                action.target,
                                new DamageInfo(this.owner, card.damage, DamageInfo.DamageType.NORMAL),
                                AbstractGameAction.AttackEffect.BLUNT_HEAVY
                        )
                );
            }

        }
    }


    public void onVictory() {
        Texture newTexture = new Texture("LuffyModRes/img/char/luffy_gear1.png");

       AbstractDungeon.player.img = newTexture;
    }


    @Override
    public void atEndOfTurn(boolean isPlayer) {
        // 在回合结束时3点伤害
        if (isPlayer) {
            flash(); // 播放闪光效果
            this.addToBot(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, this.amount));
        }
    }
}
