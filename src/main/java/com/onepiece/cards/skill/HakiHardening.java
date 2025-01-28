package com.onepiece.cards.skill;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.onepiece.helpers.ModHelper;

import static com.onepiece.characters.LuffyChar.PlayerColorEnum.Luffy_RED;
import static com.onepiece.tag.CustomTags.BUDYS;

public class HakiHardening  extends CustomCard {
    public static final String ID = ModHelper.makePath("HakiHardening");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH = "LuffyModRes/img/cards/HakiHardening.png";
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION; // 读取本地化的描述
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = Luffy_RED;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public HakiHardening() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = 4;
        this.magicNumber = this.baseMagicNumber = 4;


    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
//            this.updateCost(-1);
            this.upgradeBlock(1);
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        this.addToBot(new GainBlockAction(p, p, this.block));

        int count = ModHelper.UseHaki(p,this.magicNumber);
        if(count > 0){
            this.addToBot(new ApplyPowerAction(p,p,new MetallicizePower(p,count),count));
        }
//        for (int i = 0; i < this.magicNumber; i++) {
//            this.addToBot(new GainBlockAction(p, p, this.block));
//        }
    }


    public AbstractCard makeCopy() {
        return new HakiHardening();
    }
}
