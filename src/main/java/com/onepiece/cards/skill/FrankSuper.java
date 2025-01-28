package com.onepiece.cards.skill;

import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.onepiece.action.FrankSuperAction;
import com.onepiece.action.UpgradeCardInDeckAction;
import com.onepiece.helpers.ModHelper;
import com.onepiece.powers.BusoshokuHakiPower;

import java.util.ArrayList;

import static com.onepiece.characters.LuffyChar.PlayerColorEnum.Luffy_RED;
import static com.onepiece.tag.CustomTags.BUDYS;

//随机升级一张手牌，消耗20金币
public class FrankSuper extends CustomCard {
    public static final String ID = ModHelper.makePath("FrankSuper");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH = "LuffyModRes/img/cards/FrankSuper.png";
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION; // 读取本地化的描述
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = Luffy_RED;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public FrankSuper() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
//        this.baseBlock = 14;
        this.magicNumber = this.baseMagicNumber = 2;
        this.exhaust = true;
        this.tags.add(BUDYS);

    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        //消耗钱，随机升级
        if (upgraded) {
//            this.addToBot(new FrankSuperAction(1, false));
            ArrayList<AbstractCard> chooseList = new ArrayList<>();
            for (AbstractCard c : p.hand.group) {
                if (c.canUpgrade()) {
                    chooseList.add(c);
                }
            }
            if (chooseList.size() <= 0) {
                return;
            }
            // 创建一个选择卡牌的动作
            addToBot(new SelectCardsAction(
                    chooseList, // 可选的卡牌列表
                    1, // 选择的数量
                    CARD_STRINGS.EXTENDED_DESCRIPTION[0], // 提示文本
                    (cards) -> { // 选择后的回调函数
                        for (AbstractCard card : cards) {
                            if (card.canUpgrade()) {
                                card.upgrade(); // 如果卡牌可以升级，则升级
                                this.addToBot(new UpgradeCardInDeckAction(card));
                            }
                        }
                    }
            ));
        } else {
            ArrayList<AbstractCard> upgradableCards = new ArrayList<>(); // 存储可升级的卡牌

            // 遍历手牌，筛选出可以升级的卡牌
            for (AbstractCard card : p.hand.group) {
                if (card.canUpgrade()) {
                    upgradableCards.add(card);
                }
            }

            // 如果没有可升级的卡牌，直接返回
            if (upgradableCards.isEmpty()) {
                return;
            }
            // 随机选择一张卡牌
            AbstractCard cardToUpgrade = upgradableCards.get(AbstractDungeon.cardRandomRng.random(upgradableCards.size() - 1));

            // 升级选中的卡牌
            cardToUpgrade.upgrade();
            cardToUpgrade.superFlash();
            this.addToBot(new UpgradeCardInDeckAction(cardToUpgrade));
        }


        //消耗钱，指定升级


//       AbstractCard a = p.getStartCardForEvent();
//        a.upgrade(); // 升级卡牌
//        a.superFlash(); // 播放升级特效
    }

    // 处理卡牌选择后的逻辑
    public static void onCardSelected(AbstractCard card) {
        if (card.canUpgrade()) {
            card.upgrade(); // 如果卡牌可以升级，则升级
        } else if (card.cost > 0) {
            card.updateCost(-1); // 如果卡牌已升级，则减少1点费用
        }
    }

    public AbstractCard makeCopy() {
        return new FrankSuper();
    }
}
