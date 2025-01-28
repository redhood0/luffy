package com.onepiece.cards.attack;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.onepiece.cards.power.GearTwo;
import com.onepiece.helpers.ModHelper;

import static com.onepiece.characters.LuffyChar.PlayerColorEnum.Luffy_RED;
import static com.onepiece.tag.CustomTags.GUMGUM;
import static com.onepiece.tag.CustomTags.GUMGUM_ATK;

public class RiffleLv2 extends CustomCard {
    public static final String ID = ModHelper.makePath("RiffleLv2");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH = "LuffyModRes/img/cards/RiffleLv2.png";
    private static final int COST = 0;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION; // 读取本地化的描述 "造成 !D! 点伤害。";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = Luffy_RED;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private int costBlood;

    public RiffleLv2() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.baseDamage = 7;
        this.magicNumber = this.baseMagicNumber = 2;
        this.costBlood = 2;

        // 添加自定义标签
        this.tags.add(GUMGUM);
        this.tags.add(GUMGUM_ATK);
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
//            this.upgradeMagicNumber(2);
            // 加上以下两行就能使用UPGRADE_DESCRIPTION了（如果你写了的话）
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new LoseHPAction(p, p, costBlood));
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        this.addToBot(new DrawCardAction(1));

    }


    public void triggerOnCardPlayed(AbstractCard cardPlayed) {
        if (cardPlayed instanceof GearTwo) {
            this.addToBot(new DiscardToHandAction(this));
        }
    }

    public AbstractCard makeCopy() {
        return new RiffleLv2();
    }
}
