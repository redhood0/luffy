package com.onepiece.cards.attack;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
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

public class GatlingLv2 extends CustomCard {
    public static final String ID = ModHelper.makePath("GatlingLv2");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH = "LuffyModRes/img/cards/GatlingLv2.png";
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION; // 读取本地化的描述 "造成 !D! 点伤害。";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = Luffy_RED;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final int DAMAGE = 2;
    private static final int UP_DAMAGE = 1;

    public int costBlood; // 自定义参数 1


    public GatlingLv2() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = 8;
        this.costBlood = 5;

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
//            this.upgradeMagicNumber(4);
            // 加上以下两行就能使用UPGRADE_DESCRIPTION了（如果你写了的话）
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new LoseHPAction(p, p, costBlood));
        //先打5下
        for (int i = 0; i < 5; ++i) {
            this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
        //再打
        if (p.hasPower(ElasticPower.POWER_ID)) {
            int num = (int) Math.floor((double) (p.getPower(ElasticPower.POWER_ID).amount / this.magicNumber));
            if (num > 0) {
                for (int i = 0; i < num; ++i) {
                    this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                }
            }
        }
    }

    public int checkNum(){
        int num = 0;
        //再打
        if (AbstractDungeon.player.hasPower(ElasticPower.POWER_ID)) {
            num = (int) Math.floor((double) (AbstractDungeon.player.getPower(ElasticPower.POWER_ID).amount / this.magicNumber));

        }
        return num;
    }

    public void applyPowers() {

        super.applyPowers();

        if (!this.upgraded) {
            this.rawDescription = CARD_STRINGS.DESCRIPTION;
        } else {
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
        }

        this.rawDescription = this.rawDescription + CARD_STRINGS.EXTENDED_DESCRIPTION[0] + checkNum() + CARD_STRINGS.EXTENDED_DESCRIPTION[1];
        this.initializeDescription();
    }

    public AbstractCard makeCopy() {
        return new GatlingLv2();
    }
}
