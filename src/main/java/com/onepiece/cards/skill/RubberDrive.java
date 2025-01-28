package com.onepiece.cards.skill;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.onepiece.helpers.ModHelper;

import static com.onepiece.characters.LuffyChar.PlayerColorEnum.Luffy_RED;
import static com.onepiece.tag.CustomTags.GUMGUM;

public class RubberDrive extends CustomCard {
    public static final String ID = ModHelper.makePath("RubberDrive");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH = "LuffyModRes/img/cards/RubberDrive.png";//todo:改成橡胶果实icon
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION; // 读取本地化的描述
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = Luffy_RED;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int CostELA = 8;

    public RubberDrive() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = CostELA;
        this.tags.add(GUMGUM);
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        //获得power
//        this.addToBot(new ApplyPowerAction(p, p, new RubberDrivePower(p, 1), 1));
//        this.addToBot(new DrawCardAction(p, this.magicNumber));

        if(!upgraded){
            this.addToBot(new DrawCardAction(p, 2));
        }else {
            this.addToBot(new DrawCardAction(p, 3));
        }

        if (ModHelper.HasEnoughElasticPower(AbstractDungeon.player, CostELA)) {
            if(upgraded){
                this.addToBot(new GainEnergyAction(2));
            }else {
                this.addToBot(new GainEnergyAction(1));

            }

        }

    }
//        CardModifierManager.addModifier(this, new Remov());
//        p.masterDeck.removeCard(this);


    public AbstractCard makeCopy() {
        return new RubberDrive();
    }
}
