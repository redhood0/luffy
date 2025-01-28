package com.onepiece.cards.attack;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.onepiece.helpers.ModHelper;

import static com.onepiece.characters.LuffyChar.PlayerColorEnum.Luffy_RED;
import static com.onepiece.tag.CustomTags.GUMGUM;
import static com.onepiece.tag.CustomTags.GUMGUM_ATK;

public class WhipLv1 extends CustomCard {
    public static final String ID = ModHelper.makePath("WhipLv1");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH = "LuffyModRes/img/cards/WhipLv1.png";
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION; // 读取本地化的描述 "造成 !D! 点伤害。";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = Luffy_RED;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final int DAMAGE = 7;
    private static final int UP_DAMAGE = 3;

    public WhipLv1() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = 6;
        this.isMultiDamage = true;
        // 添加自定义标签
        this.tags.add(GUMGUM);
        this.tags.add(GUMGUM_ATK);
    }


    // 这些方法怎么写，之后再讨论
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UP_DAMAGE);
//            this.upgradeMagicNumber(1);
            // 加上以下两行就能使用UPGRADE_DESCRIPTION了（如果你写了的话）
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

//    @Override
//    public void calculateCardDamage(AbstractMonster mo) {
//
//        if (ModHelper.HasEnoughElasticPower(AbstractDungeon.player, this.magicNumber)) {
//            this.baseDamage += this.magicNumber; // 根据条件动态调整伤害值
//        }
//
//        super.calculateCardDamage(mo); // 调用父类方法计算基础伤害值
//
//    }

//    @Override
//    public void applyPowers() {
//        if (ModHelper.HasEnoughElasticPower(AbstractDungeon.player, this.magicNumber)) {
//            this.baseDamage += this.magicNumber; // 根据条件动态调整伤害值
//        }
//        super.applyPowers();
//    }

    public void onMoveToDiscard() {
        if (!this.upgraded) {
            this.baseDamage = DAMAGE;
            this.rawDescription = DESCRIPTION;
            this.initializeDescription();
        } else {
            this.baseDamage = DAMAGE + UP_DAMAGE;
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }

    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (ModHelper.HasEnoughElasticPower(AbstractDungeon.player, this.magicNumber)) {
            this.baseDamage += this.magicNumber/2; // 根据条件动态调整伤害值
        }
        this.calculateCardDamage(m);
//        if (ModHelper.HasEnoughElasticPower(p, this.magicNumber)) {
//            this.baseDamage += this.magicNumber;
////            this.upgradeDamage(this.magicNumber);
//        }

        this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    public AbstractCard makeCopy() {
        return new WhipLv1();
    }
}
