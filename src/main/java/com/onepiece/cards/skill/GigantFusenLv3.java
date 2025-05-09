package com.onepiece.cards.skill;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.onepiece.helpers.ModHelper;
import com.onepiece.powers.ElasticPower;
import com.onepiece.powers.GearThreePower;
import com.onepiece.powers.GiantFusenLv3Power;
import com.onepiece.powers.SuperWeakPower;

import static com.onepiece.characters.LuffyChar.PlayerColorEnum.Luffy_RED;
import static com.onepiece.tag.CustomTags.*;

public class GigantFusenLv3 extends CustomCard {
    public static final String ID = ModHelper.makePath("GigantFusenLv3");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH = "LuffyModRes/img/cards/GigantFusenLv3.png";
    private static final int COST = 3;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION; // 读取本地化的描述
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = Luffy_RED;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private boolean costReduced = false; // 用于跟踪费用是否已经被减少
    public int baseCostELA; // 自定义参数 1

    public GigantFusenLv3() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = 28;
        this.magicNumber = this.baseMagicNumber = 16;
//        this.baseDamage = 10;
        this.baseCostELA = this.magicNumber;

        this.tags.add(GUMGUM);
        this.tags.add(GEAR_3);
        this.tags.add(GUMGUM_DEF);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(8);
//            this.upgradeMagicNumber(1);
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {

        this.addToBot(new GainBlockAction(p, p, this.block));
        this.addToBot(new ApplyPowerAction(p, p, new GiantFusenLv3Power(p,1), 1));
////如果处于3档，则弹力消耗减少
//        int CostELA = 0;
//
//        if (AbstractDungeon.player.hasPower(GearThreePower.POWER_ID)) {
//            CostELA = this.baseCostELA / 2;
//        } else {
//            CostELA = this.baseCostELA;
//        }
//        if (ModHelper.HasEnoughElasticPower(AbstractDungeon.player, CostELA)) {
//
//            this.addToBot(new ApplyPowerAction(p, p, new GiantFusenLv3Power(p,1), 1));
//        }
//        this.addToBot(new RemoveSpecificPowerAction(p, p, ElasticPower.POWER_ID));
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
//            this.baseDamage = DAMAGE;
            this.rawDescription = DESCRIPTION;
            this.initializeDescription();
        } else {
//            this.baseDamage = DAMAGE + UP_DAMAGE;
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    public AbstractCard makeCopy() {
        return new GigantFusenLv3();
    }
}
