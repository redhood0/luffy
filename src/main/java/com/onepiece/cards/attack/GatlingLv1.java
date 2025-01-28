package com.onepiece.cards.attack;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.PummelDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.onepiece.cards.basic.GumGumFruit;
import com.onepiece.helpers.ModHelper;

import static com.onepiece.characters.LuffyChar.PlayerColorEnum.Luffy_RED;
import static com.onepiece.tag.CustomTags.GUMGUM;
import static com.onepiece.tag.CustomTags.GUMGUM_ATK;

public class GatlingLv1 extends CustomCard {
    public static final String ID = ModHelper.makePath("GatlingLv1");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH = "LuffyModRes/img/cards/GatlingLv1.png";
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION; // 读取本地化的描述 "造成 !D! 点伤害。";
    private static final String[] EXTENDED_DESCRIPTION = CARD_STRINGS.EXTENDED_DESCRIPTION; // 读取本地化的描述 "造成 !D! 点伤害。";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = Luffy_RED;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public int costElasticNum; // 自定义参数 1
    public int addATKTimeNum; // 自定义参数 2

    public GatlingLv1() {

        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.baseDamage = 2;
        this.magicNumber = this.baseMagicNumber = 2;
        this.costElasticNum = 8; // 初始化自定义参数 1
        this.addATKTimeNum = 3; // 初始化自定义参数 2
//        this.exhaust = true;

        this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0] + this.costElasticNum + EXTENDED_DESCRIPTION[1] + this.addATKTimeNum + EXTENDED_DESCRIPTION[2];
        this.initializeDescription();
        // 添加自定义标签
        this.tags.add(GUMGUM);
        this.tags.add(GUMGUM_ATK);


    }


    // 这些方法怎么写，之后再讨论
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
//            this.exhaust = false;
            this.upgradeDamage(1);
//            this.upgradeMagicNumber(1);
            // 加上以下两行就能使用UPGRADE_DESCRIPTION了（如果你写了的话）
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION + EXTENDED_DESCRIPTION[0] + this.costElasticNum + EXTENDED_DESCRIPTION[1] + this.addATKTimeNum + EXTENDED_DESCRIPTION[2];;
            this.initializeDescription();
        }
    }

//    @Override
//    public void calculateCardDamage(AbstractMonster mo) {
//        if (ModHelper.HasEnoughElasticPower(AbstractDungeon.player, this.magicNumber)) {
//            this.baseDamage += this.magicNumber; // 根据条件动态调整伤害值
//        }
//        super.calculateCardDamage(mo); // 调用父类方法计算基础伤害值
//
//    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        if (ModHelper.HasEnoughElasticPower(AbstractDungeon.player, this.costElasticNum)) {
            this.magicNumber += this.addATKTimeNum; // 根据条件动态调整攻击次数
        }

        for (int i = 1; i < this.magicNumber; ++i) {
            this.addToBot(new PummelDamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
        }

        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    public AbstractCard makeCopy() {
        return new GatlingLv1();
    }

}
