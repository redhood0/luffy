package com.onepiece.cards.attack;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.onepiece.helpers.ModHelper;
import com.onepiece.powers.GearThreePower;
import com.onepiece.powers.SuperWeakPower;

import static com.onepiece.characters.LuffyChar.PlayerColorEnum.Luffy_RED;
import static com.onepiece.tag.CustomTags.*;

public class GiantThrustLv3 extends CustomCard {

    public static final String ID = ModHelper.makePath("GiantThrustLv3");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH = "LuffyModRes/img/cards/GiantThrustLv3.png";
    private static final int COST = 3;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION; // 读取本地化的描述 "造成 !D! 点伤害。";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = Luffy_RED;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final int DAMAGE = 0;
    private static final int UP_DAMAGE = 5;
    private boolean costReduced = false; // 用于跟踪费用是否已经被减少

    public int baseCostELA; // 自定义参数 1


    public GiantThrustLv3() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.baseDamage = DAMAGE;
        this.baseBlock = 16;
        this.magicNumber = this.baseMagicNumber = this.baseCostELA = 24;


        // 添加自定义标签
        this.tags.add(GUMGUM);
        this.tags.add(GUMGUM_ATK);
        this.tags.add(GEAR_3);
    }


    // 这些方法怎么写，之后再讨论
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
//            this.upgradeDamage(UP_DAMAGE);
//            this.upgradeMagicNumber(1);
            this.upgradeBlock(6);
            // 加上以下两行就能使用UPGRADE_DESCRIPTION了（如果你写了的话）
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        this.addToBot(new GainBlockAction(p, p, this.block));

//        this.addToBot(new LoseHPAction(p, p, costBlood));

        //如果处于3档，则弹力消耗减少
        int CostELA = 0;
        if (AbstractDungeon.player.hasPower(GearThreePower.POWER_ID)) {
            CostELA = this.baseCostELA / 2;
        } else {
            CostELA = this.baseCostELA;
        }

        if (ModHelper.HasEnoughElasticPower(AbstractDungeon.player, CostELA)) {
            //对敌人造成伤害，抄肚皮
            this.baseDamage = p.currentBlock + this.block;
            this.calculateCardDamage(m);
            this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SHIELD));
        }


//        this.addToBot(new ApplyPowerAction(p, p, new ElasticPower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if (AbstractDungeon.player.hasPower(GearThreePower.POWER_ID) && !costReduced) {
            this.updateCost(-2); // 如果玩家有脆弱状态且费用未被减少，减少2点费用
            costReduced = true; // 标记费用已被减少
        } else if (!AbstractDungeon.player.hasPower(GearThreePower.POWER_ID) && costReduced) {
            this.updateCost(2); // 如果玩家没有脆弱状态且费用已被减少，恢复费用
            costReduced = false; // 标记费用已恢复
        }
    }

    public void onMoveToDiscard() {
        if (this.costReduced) {
            this.upgradeBaseCost(COST);
//            this.updateCost(2);
            costReduced = false;
        }

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

    public AbstractCard makeCopy() {
        return new GiantThrustLv3();
    }
}
