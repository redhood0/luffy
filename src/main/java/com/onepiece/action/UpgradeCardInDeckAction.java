package com.onepiece.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class UpgradeCardInDeckAction extends AbstractGameAction {

    private AbstractCard cardToUpgrade; // 需要升级的卡牌

    public UpgradeCardInDeckAction(AbstractCard cardToUpgrade) {
        this.cardToUpgrade = cardToUpgrade;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        // 升级手牌中的卡牌
        cardToUpgrade.upgrade();
        cardToUpgrade.superFlash();

        // 升级卡组中的对应卡牌
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
            if (card.cardID.equals(cardToUpgrade.cardID) && card.canUpgrade()) {
                card.upgrade();
                break; // 找到对应卡牌后退出循环
            }
        }

        this.isDone = true; // 标记动作为完成
    }
}