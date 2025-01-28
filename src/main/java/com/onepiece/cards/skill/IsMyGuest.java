package com.onepiece.cards.skill;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.onepiece.helpers.ModHelper;
import com.onepiece.powers.ElasticPower;
import com.onepiece.powers.FullPower;

import java.util.ArrayList;
import java.util.Collections;

import static com.onepiece.characters.LuffyChar.PlayerColorEnum.Luffy_RED;
import static com.onepiece.tag.CustomTags.BUDYS;
import static com.onepiece.tag.CustomTags.FOOD;

public class IsMyGuest extends CustomCard {
    public static final String ID = ModHelper.makePath("IsMyGuest");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH = "LuffyModRes/img/cards/IsMyGuest.png";
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION; // 读取本地化的描述
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = Luffy_RED;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public IsMyGuest() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
//        this.baseBlock = 14;
        this.magicNumber = this.baseMagicNumber = 2;
//        this.retain = true;
        this.tags.add(BUDYS);

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
        if (!p.hasPower(FullPower.POWER_ID)) {
            for (int i = 0; i < this.magicNumber; i++) {
                addRandomFoodCardsToHand();
            }

        }
    }

    public void addRandomFoodCardsToHand() {
        // 获取所有带有 "food" 标签的卡牌
        ArrayList<AbstractCard> foodCards = new ArrayList<>();
        for (AbstractCard card : CardLibrary.getAllCards()) {
            if (card.hasTag(FOOD)) {
                foodCards.add(card);
            }
        }

        if (!foodCards.isEmpty()) {
            AbstractCard card = foodCards.get(AbstractDungeon.cardRandomRng.random(foodCards.size() - 1));
            AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(card.makeStatEquivalentCopy()));
        }
    }


    public AbstractCard makeCopy() {
        return new IsMyGuest();
    }
}
