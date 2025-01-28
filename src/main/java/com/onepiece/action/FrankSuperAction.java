package com.onepiece.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

//随机将一张手牌升级
public class FrankSuperAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    private boolean isRandom;
    private boolean anyNumber;
    private boolean canPickZero;
    public static int numExhausted;

    public FrankSuperAction(int amount, boolean isRandom, boolean anyNumber, boolean canPickZero) {
        this.anyNumber = anyNumber;
        this.p = AbstractDungeon.player;
        this.canPickZero = canPickZero;
        this.isRandom = isRandom;
        this.amount = amount;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public FrankSuperAction(AbstractCreature target, AbstractCreature source, int amount, boolean isRandom, boolean anyNumber) {
        this(amount, isRandom, anyNumber);
        this.target = target;
        this.source = source;
    }

    public FrankSuperAction(AbstractCreature target, AbstractCreature source, int amount, boolean isRandom) {
        this(amount, isRandom, false, false);
        this.target = target;
        this.source = source;
    }

    public FrankSuperAction(AbstractCreature target, AbstractCreature source, int amount, boolean isRandom, boolean anyNumber, boolean canPickZero) {
        this(amount, isRandom, anyNumber, canPickZero);
        this.target = target;
        this.source = source;
    }

    public FrankSuperAction(boolean isRandom, boolean anyNumber, boolean canPickZero) {
        this(99, isRandom, anyNumber, canPickZero);
    }

    public FrankSuperAction(int amount, boolean canPickZero) {
        this(amount, false, false, canPickZero);
    }

    public FrankSuperAction(int amount, boolean isRandom, boolean anyNumber) {
        this(amount, isRandom, anyNumber, false);
    }

    public FrankSuperAction(int amount, boolean isRandom, boolean anyNumber, boolean canPickZero, float duration) {
        this(amount, isRandom, anyNumber, canPickZero);
        this.duration = this.startDuration = duration;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (this.p.hand.size() == 0) {
                this.isDone = true;
                return;
            }

            if (!this.anyNumber && this.p.hand.size() <= this.amount) {
                this.amount = this.p.hand.size();
                numExhausted = this.amount;
                int tmp = this.p.hand.size();

                for (int i = 0; i < tmp; ++i) {
                    AbstractCard c = this.p.hand.getTopCard();

                    this.addToBot(new UpgradeCardInDeckAction(c));
                }

                return;
            }

            if (!this.isRandom) {
                numExhausted = this.amount;
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, this.anyNumber, this.canPickZero);
                this.tickDuration();
                return;
            }

            for (int i = 0; i < this.amount; ++i) {

                AbstractCard c = this.p.hand.getTopCard();
                this.addToBot(new UpgradeCardInDeckAction(c));
            }


        }
//        !AbstractDungeon.handCardSelectScreen.wereCardsRetrieved

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {

                this.addToBot(new UpgradeCardInDeckAction(c));
                //玩家手里再加一张升级的C
                c.upgrade();
//                c.superFlash();
                // 将升级后的卡牌置入玩家手牌中
                AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(c));

            }


//            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        this.isDone = true;
        this.tickDuration();
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
        TEXT = new String[]{"choose a card to level up!"};
    }
}
