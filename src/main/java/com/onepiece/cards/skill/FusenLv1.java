package com.onepiece.cards.skill;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.onepiece.cards.basic.Defend;
import com.onepiece.cards.basic.Strike;
import com.onepiece.helpers.ModHelper;
import com.onepiece.powers.ElasticPower;

import static com.onepiece.characters.LuffyChar.PlayerColorEnum.Luffy_RED;
import static com.onepiece.tag.CustomTags.GUMGUM;

public class FusenLv1 extends CustomCard {
    public static final String ID = ModHelper.makePath("FusenLv1");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH = "LuffyModRes/img/cards/FusenLv1.png";
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION; // 读取本地化的描述
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = Luffy_RED;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public FusenLv1() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
//        this.baseBlock = 14;
//        this.magicNumber = this.baseMagicNumber = 14;
        this.exhaust = true;
        this.tags.add(GUMGUM);

    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void applyPowers() {
        double times = 1;
        if (this.upgraded) {
            times = 1.5;
        }
        this.baseBlock = (int) (ModHelper.GetElasticPowerNum(AbstractDungeon.player) * times);
        super.applyPowers();
//        if (this.upgraded) {
//            this.baseBlock += 3;
//        }

//        super.applyPowers();
//        if (!this.upgraded) {
//            this.rawDescription = cardStrings.DESCRIPTION;
//        } else {
//            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
//        }
//
//        this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0];
//        this.initializeDescription();
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {

        this.addToBot(new GainBlockAction(p, p, this.block));
        this.addToBot(new RemoveSpecificPowerAction(p, p, ElasticPower.POWER_ID));
    }

    public AbstractCard makeCopy() {
        return new FusenLv1();
    }

}
