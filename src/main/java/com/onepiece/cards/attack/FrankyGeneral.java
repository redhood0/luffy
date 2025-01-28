package com.onepiece.cards.attack;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import com.onepiece.helpers.ModHelper;

import static com.onepiece.characters.LuffyChar.PlayerColorEnum.Luffy_RED;
import static com.onepiece.tag.CustomTags.*;

public class FrankyGeneral extends CustomCard {

    public static final String ID = ModHelper.makePath("FrankyGeneral");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH = "LuffyModRes/img/cards/FrankyGeneral.png";
    private static final int COST = 2;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION; // 读取本地化的描述 "造成 !D! 点伤害。";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = Luffy_RED;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    public FrankyGeneral() {
        this(0);
    }

    public FrankyGeneral(int upgrades) {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        //最好别用默认值
        this.baseDamage = 5;
        this.baseBlock = 5;
        this.baseMagicNumber = 1;
        this.timesUpgraded = upgrades;
        this.isMultiDamage = true;
        // 添加自定义标签
        this.tags.add(BUDYS);
    }


    @Override
    public void upgrade() {
        this.upgradeDamage(2 + this.timesUpgraded);
        this.upgradeBlock(2 + this.timesUpgraded);
//        AbstractDungeon.cardRandomRng.random(3);//随机r
//        rollAndUpdate(3);
        ++this.timesUpgraded;
        this.upgraded = true;
        this.name = CARD_STRINGS.NAME + "+" + this.timesUpgraded;
        this.initializeTitle();
    }

    public boolean canUpgrade() {
        return true;
    }

    private void rollAndUpdate(int time) {
        for (int i = 0; i < time; i++) {
            int index = AbstractDungeon.cardRandomRng.random(3);
            switch (index) {
                case 0:
                    this.baseDamage++;
                    break;
                case 1:
                    this.baseBlock++;
                    break;
                case 2:
                    this.baseMagicNumber++;
                    break;
            }
        }

    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

//        DamageInfo info = new DamageInfo(AbstractDungeon.player, this.damage, DamageInfo.DamageType.THORNS);
////        info.applyEnemyPowersOnly(target);
////        this.addToBot(new DamageAction(m, info, AbstractGameAction.AttackEffect.LIGHTNING));
//
//
//        AbstractMonster target = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster) null, true, AbstractDungeon.cardRandomRng);
//        if (target != null) {
//            this.calculateCardDamage(target);
//            this.addToTop(new DamageAllEnemiesAction(p info, AbstractGameAction.AttackEffect.LIGHTNING));
//        }
        this.addToBot(new SFXAction("THUNDERCLAP", 0.05F));

        for(AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!mo.isDeadOrEscaped()) {
                this.addToBot(new VFXAction(new LightningEffect(mo.drawX, mo.drawY), 0.05F));
            }
        }

        this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.LIGHTNING));

        this.addToBot(new GainBlockAction(p, p, this.block));
    }

    public AbstractCard makeCopy() {
        return new FrankyGeneral();
    }
}
