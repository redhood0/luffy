package com.onepiece.cards.basic;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.onepiece.helpers.ModHelper;
import com.onepiece.powers.ElasticPower;

import static com.onepiece.characters.LuffyChar.PlayerColorEnum.Luffy_RED;
import static com.onepiece.tag.CustomTags.GUMGUM;
import static com.onepiece.tag.CustomTags.GUMGUM_ATK;

public class GumStrike extends CustomCard {
    public static final String ID = ModHelper.makePath("GumStrike");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH = "LuffyModRes/img/cards/GumStrike.png";
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION; // 读取本地化的描述 "造成 !D! 点伤害。";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = Luffy_RED;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final int DAMAGE = 7;
    private static final int UP_DAMAGE = 3;
    private static final int MAGIC = 1;
    private static final int UP_MAGIC = 1;

    public GumStrike() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = MAGIC;

        this.tags.add(CardTags.STARTER_STRIKE);
        this.tags.add(CardTags.STRIKE);
        this.tags.add(GUMGUM);
        this.tags.add(GUMGUM_ATK);
    }

//    @Override
//    public void calculateCardDamage(AbstractMonster mo) {
//        if (ModHelper.HasEnoughElasticPower(AbstractDungeon.player, this.magicNumber)) {
//            this.baseDamage += this.magicNumber; // 根据条件动态调整伤害值
//        }
//        super.calculateCardDamage(mo); // 调用父类方法计算基础伤害值
//
//    }

//    @Override
//    public void applyPowers(){
//        float tmp = (float)this.baseDamage;
//        AbstractPlayer player = AbstractDungeon.player;
//        tmp = player.stance.atDamageGive(tmp, this.damageTypeForTurn, this);
//        super.applyPowers();
//    }

    // 这些方法怎么写，之后再讨论
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
            this.upgradeMagicNumber(1);
            // 加上以下两行就能使用UPGRADE_DESCRIPTION了（如果你写了的话）
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

//    public float atDamageGive(float damage, DamageInfo.DamageType type) {
//        return type == DamageInfo.DamageType.NORMAL ? damage + (float)this.amount : damage;
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
//        if (ModHelper.HasEnoughElasticPower(AbstractDungeon.player, this.magicNumber)) {
//            this.baseDamage += this.magicNumber; // 根据条件动态调整伤害值
//        }
//        this.calculateCardDamage(m);
        this.addToBot(new ApplyPowerAction(p, p, new ElasticPower(p, this.magicNumber), this.magicNumber));
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));

    }

    public AbstractCard makeCopy() {
        return new GumStrike();
    }
}
