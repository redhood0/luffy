package com.onepiece.action;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class OIkamaWayAction extends AbstractGameAction {
    // 伤害信息
    public DamageInfo info;
    private AbstractPlayer player;
    private int numberOfCards;
    private boolean optional;
    private int newCost;
    private boolean setCost;
    public static final String[] TEXT;

    public OIkamaWayAction(int numberOfCards, boolean optional) {
        this.newCost = 0;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.player = AbstractDungeon.player;
        this.numberOfCards = numberOfCards;
        this.optional = optional;
        this.setCost = false;
    }

    public OIkamaWayAction(int numberOfCards) {
        this(numberOfCards, false);
    }


    public void update() {
        if (this.duration == this.startDuration) {
            if (!this.player.drawPile.isEmpty() && this.numberOfCards > 0) {
                if (this.player.drawPile.size() <= this.numberOfCards && !this.optional) {
                    ArrayList<AbstractCard> cardsToMove = new ArrayList();

                    for (AbstractCard c : this.player.drawPile.group) {
                        cardsToMove.add(c);
                    }

                    for (AbstractCard c : cardsToMove) {
                        if (this.player.hand.size() < 10) {
                            AbstractCard copy = c.makeCopy();
                            if(c.upgraded){
                                copy.upgrade();
                            }
                            CardModifierManager.addModifier(copy, new ExhaustMod());
                            this.player.hand.addToHand(copy);
                            if (this.setCost) {
                                c.setCostForTurn(this.newCost);
                            }
//todo：这里可能需要复制
//                            this.player.drawPile.removeCard(c);
                        }

                        c.lighten(false);
                        c.applyPowers();
                    }

                    this.isDone = true;
                } else {
                    if (this.numberOfCards == 1) {
                        if (this.optional) {
                            AbstractDungeon.gridSelectScreen.open(this.player.drawPile, this.numberOfCards, true, TEXT[0]);
                        } else {
                            AbstractDungeon.gridSelectScreen.open(this.player.drawPile, this.numberOfCards, TEXT[0], false);
                        }
                    } else if (this.optional) {
                        AbstractDungeon.gridSelectScreen.open(this.player.drawPile, this.numberOfCards, true, TEXT[1] + this.numberOfCards + TEXT[2]);
                    } else {
                        AbstractDungeon.gridSelectScreen.open(this.player.drawPile, this.numberOfCards, TEXT[1] + this.numberOfCards + TEXT[2], false);
                    }

                    this.tickDuration();
                }
            } else {
                this.isDone = true;
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                    if (this.player.hand.size() < 10) {

                        AbstractCard copy = c.makeCopy();
                        if(c.upgraded){
                            copy.upgrade();
                        }
                        CardModifierManager.addModifier(copy, new ExhaustMod());
                        this.player.hand.addToHand(copy);

                        if (this.setCost) {
                            c.setCostForTurn(this.newCost);
                        }
//todo：这里可能需要额外增加
//                        this.player.drawPile.removeCard(c);

                    }

                    c.lighten(false);
                    c.unhover();
                    c.applyPowers();
                }

                for (AbstractCard c : this.player.drawPile.group) {
                    c.unhover();
                    c.target_x = (float) CardGroup.DISCARD_PILE_X;
                    c.target_y = 0.0F;
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }

            this.tickDuration();
            if (this.isDone) {
                for (AbstractCard c : this.player.hand.group) {
                    c.applyPowers();
                }
            }

        }
    }

    static {
        TEXT = new String[]{
                "choose a card", "", "", ""
        };
    }
}
