package com.onepiece.cards.skill;

import basemod.AutoAdd;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.unique.DiscardPileToTopOfDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
<<<<<<<< HEAD:src/main/java/com/onepiece/cards/power/SakuraFlow.java
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.onepiece.helpers.ModHelper;
import com.onepiece.powers.SakuraFlowPower;
========
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.onepiece.helpers.ModHelper;
import com.onepiece.powers.RubberDrivePower;
>>>>>>>> origin/master:src/main/java/com/onepiece/cards/skill/RubberDrive.java

import static com.onepiece.characters.LuffyChar.PlayerColorEnum.Luffy_RED;
import static com.onepiece.tag.CustomTags.GUMGUM;
@AutoAdd.Ignore
public class SakuraFlow extends CustomCard {

    public static final String ID = ModHelper.makePath("SakuraFlow");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
<<<<<<<< HEAD:src/main/java/com/onepiece/cards/power/SakuraFlow.java
    private static final String IMG_PATH = "LuffyModRes/img/cards/SakuraFlow.png";//todo:改成橡胶果实icon
    private static final int COST = 2;
========
    private static final String IMG_PATH = "LuffyModRes/img/cards/RubberDrive.png";//todo:改成橡胶果实icon
    private static final int COST = 1;
>>>>>>>> origin/master:src/main/java/com/onepiece/cards/skill/RubberDrive.java
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION; // 读取本地化的描述
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = Luffy_RED;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int CostELA = 8;

    public SakuraFlow() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = CostELA;
        this.tags.add(GUMGUM);
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
<<<<<<<< HEAD:src/main/java/com/onepiece/cards/power/SakuraFlow.java
//            this.upgradeMagicNumber(-2);
            this.upgradeBaseCost(1);
//            this.upgradeBlock(3);
========
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
>>>>>>>> origin/master:src/main/java/com/onepiece/cards/skill/RubberDrive.java
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        //消耗霸气

        //获得power
<<<<<<<< HEAD:src/main/java/com/onepiece/cards/power/SakuraFlow.java
        this.addToBot(new ApplyPowerAction(p, p, new SakuraFlowPower(p), 1));
========
//        this.addToBot(new ApplyPowerAction(p, p, new RubberDrivePower(p, 1), 1));
//        this.addToBot(new DrawCardAction(p, this.magicNumber));

        if(!upgraded){
            this.addToBot(new DrawCardAction(p, 2));
        }else {
            this.addToBot(new DrawCardAction(p, 3));
        }

        if (ModHelper.HasEnoughElasticPower(AbstractDungeon.player, CostELA)) {
            this.addToBot(new GainEnergyAction(1));
        }
>>>>>>>> origin/master:src/main/java/com/onepiece/cards/skill/RubberDrive.java

    }
//        CardModifierManager.addModifier(this, new Remov());
//        p.masterDeck.removeCard(this);


    public AbstractCard makeCopy() {
        return new SakuraFlow();
    }
}
