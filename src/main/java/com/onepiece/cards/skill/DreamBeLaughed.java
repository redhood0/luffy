package com.onepiece.cards.skill;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Injury;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.onepiece.helpers.ModHelper;

import static com.onepiece.characters.LuffyChar.PlayerColorEnum.Luffy_RED;
import static com.onepiece.tag.CustomTags.BUDYS;

public class DreamBeLaughed extends CustomCard {
    public static final String ID = ModHelper.makePath("DreamBeLaughed");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH = "LuffyModRes/img/cards/DreamBeLaughed.png";
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION; // 读取本地化的描述
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = Luffy_RED;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public DreamBeLaughed() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = 16;
//        this.magicNumber = this.baseMagicNumber = 4;
        this.tags.add(BUDYS);

    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
//            this.updateCost(-1);
            this.upgradeBlock(5);
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        this.addToBot(new GainBlockAction(p, p, this.block));

        AbstractDungeon.actionManager.addToTop(new MakeTempCardInDrawPileAction(
                new Injury(), // 要添加的卡牌
                1,        // 数量
                false,     // 是否随机位置（false 表示放在顶部）
                true      // 是否自动触发卡牌效果
        ));

//        AbstractDungeon.actionManager.addToBottom(
//                new MakeTempCardInDrawPileAction(new Injury(), 1, true, true)
//        );

    }


    public AbstractCard makeCopy() {
        return new DreamBeLaughed();
    }
}
