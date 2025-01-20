package com.onepiece.cards.power;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.onepiece.action.TransformCardAction;
import com.onepiece.cards.basic.GumGumFruit;
import com.onepiece.cards.basic.GumStrike;
import com.onepiece.cards.basic.Strike;
import com.onepiece.helpers.ModHelper;
import com.onepiece.powers.GumGumFruitPower;
import com.onepiece.powers.RubberDrivePower;
import com.onepiece.relic.GumGumFruitRelic;

import java.util.ArrayList;

import static com.onepiece.characters.LuffyChar.PlayerColorEnum.Luffy_RED;
import static com.onepiece.tag.CustomTags.GUMGUM;

public class RubberDrive extends CustomCard {
    public static final String ID = ModHelper.makePath("RubberDrive");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH = "LuffyModRes/img/cards/RubberDrive.png";//todo:改成橡胶果实icon
    private static final int COST = 2;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION; // 读取本地化的描述
    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = Luffy_RED;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public RubberDrive() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 4;
        this.tags.add(GUMGUM);
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(-2);
//            this.upgradeBaseCost(1);
//            this.upgradeBlock(3);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        //获得power
        this.addToBot(new ApplyPowerAction(p, p, new RubberDrivePower(p, 1), 1));

    }
//        CardModifierManager.addModifier(this, new Remov());
//        p.masterDeck.removeCard(this);


    public AbstractCard makeCopy() {
        return new RubberDrive();
    }
}
