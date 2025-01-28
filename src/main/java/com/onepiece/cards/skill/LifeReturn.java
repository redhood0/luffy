package com.onepiece.cards.skill;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.onepiece.helpers.ModHelper;
import com.onepiece.powers.FullPower;

import static com.onepiece.characters.LuffyChar.PlayerColorEnum.Luffy_RED;
import static com.onepiece.tag.CustomTags.FOOD;

public class LifeReturn extends CustomCard {
    public static final String ID = ModHelper.makePath("LifeReturn");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH = "LuffyModRes/img/cards/LifeReturn.png";
    private static final int COST = 2;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION; // 读取本地化的描述
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = Luffy_RED;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public LifeReturn() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
//        this.baseBlock = 14;
        this.magicNumber = this.baseMagicNumber = 3;
        this.exhaust = true;
//        this.tags.add(FOOD);

    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
//            this.upgradeMagicNumber(1);
            this.updateCost(-1);
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {

//        this.addToBot(new GainBlockAction(p, p, this.block));
//        this.addToBot(new RemoveSpecificPowerAction(p, p, ElasticPower.POWER_ID));
//        this.addToBot(new ApplyPowerAction(p, p, new FullPower(p, this.magicNumber), this.magicNumber));
        heal(p);
//        if (this.upgraded) {
//            heal(p,)
//        } else {
//
//        }
    }

    public void heal(AbstractPlayer p) {
        if (p.hasPower(FullPower.POWER_ID)) {
            int fullNum = p.getPower(FullPower.POWER_ID).amount;

            // 获得能量
            int energyToGain = fullNum;
            if (energyToGain > 0) {
                AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(energyToGain));
            }

            // 获得手牌，不超过手牌上限
            int cardsToDraw = Math.min(fullNum, 10 - p.gameHandSize);
            if (cardsToDraw > 0) {
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, cardsToDraw));
            }
            this.addToBot(new RemoveSpecificPowerAction(p, p, FullPower.POWER_ID));
        }
    }

//    @Override
//    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
//        // 检查玩家生命值是否低于 20点
//        if (p.currentHealth > 20) {
//            this.cantUseMessage = CARD_STRINGS.EXTENDED_DESCRIPTION[0]; // 设置无法使用的提示信息,最好用本地化
//            return false;
//        }
//        return super.canUse(p, m);
//    }

    public AbstractCard makeCopy() {
        return new LifeReturn();
    }
}
