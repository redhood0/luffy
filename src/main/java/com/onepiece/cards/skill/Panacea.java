package com.onepiece.cards.skill;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.onepiece.action.RemoveRandomDebuffAction;
import com.onepiece.helpers.ModHelper;
import com.onepiece.powers.FullPower;

import java.util.ArrayList;

import static com.onepiece.characters.LuffyChar.PlayerColorEnum.Luffy_RED;
import static com.onepiece.tag.CustomTags.BUDYS;
import static com.onepiece.tag.CustomTags.FOOD;

public class Panacea  extends CustomCard {
    public static final String ID = ModHelper.makePath("Panacea");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH = "LuffyModRes/img/cards/Panacea.png";
    private static final int COST = 0;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION; // 读取本地化的描述
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = Luffy_RED;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Panacea() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
//        this.baseBlock = 14;
        this.magicNumber = this.baseMagicNumber = 1;
        this.exhaust = true;
//        this.retain = true;
        this.tags.add(BUDYS);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
//            this.upgradeBaseCost(0);
//            this.exhaust = false;
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {

        if(upgraded){
            this.addToBot(new RemoveRandomDebuffAction(p));
            this.addToBot(new RemoveRandomDebuffAction(p));
        }else {
            this.addToBot(new RemoveRandomDebuffAction(p));
        }


        if (hasNoPotions(p)) {
            this.addToBot(new ObtainPotionAction(AbstractDungeon.returnRandomPotion(true)));
        }
    }


    public boolean hasNoPotions(AbstractPlayer p) {

        // 遍历玩家的药水栏
//        if(p.potions.size() > 0){
//            return false;
//        }
        for (AbstractPotion potion : p.potions) {
            if (!potion.ID.equals("Potion Slot")) {
                return false; // 如果找到一个药水，返回false
            }
        }
        return true; // 如果没有找到任何药水，返回true
    }

    public AbstractCard makeCopy() {
        return new Panacea();
    }
}
