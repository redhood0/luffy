package com.onepiece.cards.attack;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.BloodForBlood;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.onepiece.helpers.ModHelper;

import static com.onepiece.characters.LuffyChar.PlayerColorEnum.Luffy_RED;
import static com.onepiece.tag.CustomTags.GUMGUM;
import static com.onepiece.tag.CustomTags.GUMGUM_ATK;

public class ReddeHookuLv2 extends CustomCard {
    public static final String ID = ModHelper.makePath("ReddeHookuLv2");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH = "LuffyModRes/img/cards/ReddeHookuLv2.png";
    private static final int COST = 5;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION; // 读取本地化的描述 "造成 !D! 点伤害。";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = Luffy_RED;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final int DAMAGE = 30;
    private static final int UP_DAMAGE = 5;

    public int costBlood; // 自定义参数 1


    public ReddeHookuLv2() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = 2;
        this.costBlood = 2;

        // 添加自定义标签
        this.tags.add(GUMGUM);
        this.tags.add(GUMGUM_ATK);
    }

    public void tookDamage() {
        this.updateCost(-1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            if (this.cost < 5) {
                this.upgradeBaseCost(this.cost - 1);
                if (this.cost < 0) {
                    this.cost = 0;
                }
            } else {
                this.upgradeBaseCost(4);
            }
            this.upgradeDamage(UP_DAMAGE);
        }
    }

    public AbstractCard makeCopy() {
        AbstractCard tmp = new ReddeHookuLv2();
        if (AbstractDungeon.player != null) {
            tmp.updateCost(-AbstractDungeon.player.damagedThisCombat);
        }

        return tmp;
    }

}
