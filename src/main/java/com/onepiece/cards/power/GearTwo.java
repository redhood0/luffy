package com.onepiece.cards.power;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.onepiece.helpers.ModHelper;
import com.onepiece.powers.GearTwoPower;
import com.onepiece.powers.RubberDrivePower;

import static com.onepiece.characters.LuffyChar.PlayerColorEnum.Luffy_RED;
import static com.onepiece.tag.CustomTags.GEAR;
import static com.onepiece.tag.CustomTags.GUMGUM;

public class GearTwo extends CustomCard {
    public static final String ID = ModHelper.makePath("GearTwo");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH = "LuffyModRes/img/cards/GearTwo.png";//todo:改成橡胶果实icon
    private static final int COST = 3;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION; // 读取本地化的描述
    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = Luffy_RED;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public GearTwo() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 5;
        this.tags.add(GUMGUM);
        this.tags.add(GEAR);
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
//            this.upgradeMagicNumber(-2);
            this.upgradeBaseCost(2);
//            this.upgradeBlock(3);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        //获得power
        this.addToBot(new ApplyPowerAction(p, p, new GearTwoPower(p, this.magicNumber), this.magicNumber));

    }
//        CardModifierManager.addModifier(this, new Remov());
//        p.masterDeck.removeCard(this);



    public AbstractCard makeCopy() {
        return new GearTwo();
    }
}
