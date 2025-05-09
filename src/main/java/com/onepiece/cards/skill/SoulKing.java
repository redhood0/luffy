package com.onepiece.cards.skill;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.BetterDiscardPileToHandAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.onepiece.action.SoulKingAction;
import com.onepiece.helpers.ModHelper;
import com.onepiece.powers.FullPower;

import static com.onepiece.characters.LuffyChar.PlayerColorEnum.Luffy_RED;
import static com.onepiece.tag.CustomTags.BUDYS;
import static com.onepiece.tag.CustomTags.FOOD;

public class SoulKing extends CustomCard {
    public static final String ID = ModHelper.makePath("SoulKing");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH = "LuffyModRes/img/cards/SoulKing.png";
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION; // 读取本地化的描述
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = Luffy_RED;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public SoulKing() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
//        this.baseBlock = 14;
        this.magicNumber = this.baseMagicNumber = 2;
        this.exhaust = true;
        this.tags.add(BUDYS);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
          this.exhaust = false;
//            this.upgradeBaseCost(0);
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {

//        this.addToBot(new GainBlockAction(p, p, this.block));
//        this.addToBot(new RemoveSpecificPowerAction(p, p, ElasticPower.POWER_ID));
        // 打开弃牌堆选择界面
//        AbstractDungeon.gridSelectScreen.open(p.discardPile, 1, "Choose a card to add to your hand.", false);
        this.addToBot( new BetterDiscardPileToHandAction(this.magicNumber));
//        this.addToBot(new SoulKingAction(p,CARD_STRINGS.EXTENDED_DESCRIPTION ));
    }



    public AbstractCard makeCopy() {
        return new SoulKing();
    }
}
