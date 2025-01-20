package com.onepiece.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class TransformCardAction extends AbstractGameAction {
    private String oldCardID;
    private AbstractCard newCard;

    public TransformCardAction(String oldCardID, AbstractCard newCard) {
        this.oldCardID = oldCardID;
        this.newCard = newCard;
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;

        // 在手牌中替换所有匹配的卡牌
        for (AbstractCard card : new ArrayList<>(p.hand.group)) {
            if (card.cardID.equals(oldCardID)) {
                p.hand.removeCard(card);
                p.hand.addToTop(newCard.makeCopy());
            }
        }

        // 在抽牌堆中替换所有匹配的卡牌
        for (AbstractCard card : new ArrayList<>(p.drawPile.group)) {
            if (card.cardID.equals(oldCardID)) {
                p.drawPile.removeCard(card);
                p.drawPile.addToRandomSpot(newCard.makeCopy());
            }
        }

        // 在弃牌堆中替换所有匹配的卡牌
        for (AbstractCard card : new ArrayList<>(p.discardPile.group)) {
            if (card.cardID.equals(oldCardID)) {
                p.discardPile.removeCard(card);
                p.discardPile.addToTop(newCard.makeCopy());
            }
        }

        this.isDone = true;
    }
}
