package com.onepiece.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.onepiece.helpers.ModHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static com.onepiece.tag.CustomTags.BUDYS;

public class TwoYearSabaodyPower  extends AbstractPower {
    // 能力的ID
    public static final String POWER_ID = ModHelper.makePath("TwoYearSabaodyPower");
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Logger log = LoggerFactory.getLogger(TwoYearSabaodyPower.class);

    public TwoYearSabaodyPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = AbstractPower.PowerType.BUFF;

        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = 1;

        // 添加一大一小两张能力图
        String path128 = "LuffyModRes/img/powers/TwoYearSabaodyPower84.png";
        String path48 = "LuffyModRes/img/powers/TwoYearSabaodyPower32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        // 首次添加能力更新描述
        this.updateDescription();
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
//        this.description = DESCRIPTIONS[0]+ this.amount ;
    }

    public void atStartOfTurn() {
        for(int i = 0; i< this.amount; i++) {
            pickBudysCard();
        }

    }

    public void pickBudysCard() {

        List<WeightedCard> weightedCards = getWeightedBudysCards();

        if (!weightedCards.isEmpty()) {
            // 根据权重随机选择一张卡牌
            AbstractCard card = getRandomWeightedCard(weightedCards);

            // 将卡牌添加到玩家手牌中
            AbstractDungeon.actionManager.addToBottom(
                    new MakeTempCardInHandAction(card)
            );
        } else {
            System.out.println("No cards with 'budys' tag found!");
        }
    }


    // 获取所有含有 "budys" 标签的卡牌
    private List<AbstractCard> getBudysCards() {
        List<AbstractCard> budysCards = new ArrayList<>();

        // 遍历 CardLibrary 中的所有卡牌
        for (AbstractCard card : CardLibrary.getAllCards()) {
            // 假设 "budys" 标签存储在 card.tags 中
            if (card.tags.contains(BUDYS)) {
                budysCards.add(card);
            }
        }

        return budysCards;
    }

    // 从列表中随机选择一张卡牌
    private AbstractCard getRandomBudysCard(List<AbstractCard> budysCards) {
        int index = AbstractDungeon.cardRandomRng.random(budysCards.size() - 1);
        return budysCards.get(index).makeCopy(); // 返回卡牌的副本
    }

    // 获取所有含有 "budys" 标签的卡牌及其权重
    private List<WeightedCard> getWeightedBudysCards() {
        List<WeightedCard> weightedCards = new ArrayList<>();

        // 遍历 CardLibrary 中的所有卡牌
        for (AbstractCard card : CardLibrary.getAllCards()) {
            // 假设 "budys" 标签存储在 card.tags 中
            if (card.tags.contains(BUDYS)) {
                // 根据稀有度分配权重
                int weight = getWeightByRarity(card.rarity);
                weightedCards.add(new WeightedCard(card, weight));
            }
        }

        return weightedCards;
    }


    // 根据稀有度分配权重
    private int getWeightByRarity(AbstractCard.CardRarity rarity) {
        switch (rarity) {
//            case COMMON:
//                return 5; // 普通卡牌权重最高
            case UNCOMMON:
                return 4; // 罕见卡牌权重中等
            case RARE:
                return 1; // 稀有卡牌权重最低
            default:
                return 1; // 其他稀有度默认权重
        }
    }

    // 根据权重随机选择一张卡牌
    private AbstractCard getRandomWeightedCard(List<WeightedCard> weightedCards) {
        // 计算总权重
        int totalWeight = weightedCards.stream().mapToInt(wc -> wc.weight).sum();

        // 生成随机数
        int randomWeight = AbstractDungeon.cardRandomRng.random(totalWeight - 1);

        // 根据权重选择卡牌
        int currentWeight = 0;
        for (WeightedCard weightedCard : weightedCards) {
            currentWeight += weightedCard.weight;
            if (randomWeight < currentWeight) {
                return weightedCard.card.makeCopy(); // 返回卡牌的副本
            }
        }

        // 默认返回第一张卡牌
        return weightedCards.get(0).card.makeCopy();
    }


    // 内部类：用于存储卡牌及其权重
    private static class WeightedCard {
        AbstractCard card;
        int weight;

        WeightedCard(AbstractCard card, int weight) {
            this.card = card;
            this.weight = weight;
        }
    }
}
