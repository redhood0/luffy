package com.onepiece.cards.skill;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.onepiece.action.DosFleurAction;
import com.onepiece.cards.skill.BloodAcceleration;
import com.onepiece.helpers.ModHelper;
import com.onepiece.powers.ElasticPower;

import static com.onepiece.characters.LuffyChar.PlayerColorEnum.Luffy_RED;
import static com.onepiece.tag.CustomTags.BUDYS;

public class DosFleur extends CustomCard {
    public static final String ID = ModHelper.makePath("DosFleur");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH = "LuffyModRes/img/cards/DosFleur.png";
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION; // 读取本地化的描述
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = Luffy_RED;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public DosFleur() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
//        this.baseBlock = 14;
//        this.magicNumber = this.baseMagicNumber = 2;
//        this.exhaust = true;
        this.tags.add(BUDYS);

    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
//            this.upgradeMagicNumber(2);
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        // 抽2张卡
        if(upgraded){
            addToBot(new DrawCardAction(2,new DosFleurAction(true),true));
        }else {
            addToBot(new DrawCardAction(2,new DosFleurAction(false,CARD_STRINGS.EXTENDED_DESCRIPTION[0]),true));
        }


    }

    public void triggerWhenDrawn(){

    }

    public AbstractCard makeCopy() {
        return new DosFleur();
    }
}
