package com.onepiece.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.onepiece.powers.BusoshokuHakiPower;

import java.util.Iterator;

public class TeachingsOfDarkKingAction extends AbstractGameAction {
    /* 84 */   private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("RecycleAction");
    /* 85 */   public static final String[] TEXT = uiStrings.TEXT;
    /*    */   private float startingDuration;
    /*    */   private DamageInfo info;
    /*    */   private AbstractPlayer p;
    /*    */ int[] base;
    /*    */ boolean extra_draw;

    public TeachingsOfDarkKingAction(AbstractCreature target, DamageInfo info, int[] baseDamage) {
        /* 33 */
        this.info = info;
        /* 34 */
        setValues(target, info);
        /* 35 */
        this.actionType = AbstractGameAction.ActionType.WAIT;
        /* 36 */
        this.attackEffect = AttackEffect.NONE;
        /* 37 */
        this.startingDuration = Settings.ACTION_DUR_FAST;
        /* 38 */
        this.duration = this.startingDuration;
        /* 39 */
        this.base = baseDamage;
        /* 40 */
        this.p = AbstractDungeon.player;
        /*    */
    }


    @Override
    public void update() {

        /* 44 */
        if (this.duration == Settings.ACTION_DUR_FAST) {
            /* 45 */
            if (this.p.hand.isEmpty()) {
//                /* 46 */         addToTop((AbstractGameAction)new DamageAction(this.target, this.info, AbstractGameAction.AttackEffect.FIRE));
                /* 47 */
                this.isDone = true;
                /* 48 */
            } else if (this.p.hand.size() == 1) {
                /* 49 */
                this.p.hand.moveToExhaustPile(this.p.hand.getBottomCard());
//                this.addToBot(new DrawCardAction(this.p, 1));
                this.addToBot(new ApplyPowerAction(p,p,new BusoshokuHakiPower(p,2),2));
                /*    */
                tickDuration();
                /*    */
            } else {
                /* 58 */
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false);
                /* 59 */
                tickDuration();
                /*    */
            }
            /*    */
        } else {
            /* 62 */
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                /* 63 */
                if (!AbstractDungeon.handCardSelectScreen.selectedCards.group.isEmpty())
                    /*    */ {
                    /* 65 */
                    for (Iterator<AbstractCard> var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator(); var1.hasNext(); ) {
                        /* 66 */
                        AbstractCard c = var1.next();
                        /* 67 */
                        this.p.hand.moveToExhaustPile(c);
//                        this.addToBot(new DrawCardAction(this.p, 1));
                        this.addToBot(new ApplyPowerAction(p,p,new BusoshokuHakiPower(p,2),2));
                        /*    */

                        /*    */
                    }
                    /*    */
                }
                /* 75 */
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                /* 76 */
                AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
                /*    */
            }
            /* 79 */
            tickDuration();
            /*    */
        }


//        if (this.duration == Settings.ACTION_DUR_FAST) {
//            /* 45 */       if (this.p.hand.isEmpty()) {
////                /* 46 */         addToTop((AbstractGameAction)new DamageAction(this.target, this.info, AbstractGameAction.AttackEffect.FIRE));
//                /* 47 */         this.isDone = true;
//                /* 48 */       } else if (this.p.hand.size() == 1) {
//                /* 49 */         if ((this.p.hand.getBottomCard()).color == AbstractCard.CardColor.CURSE) {
//                                       addToBot((AbstractGameAction)new HealAction(this.p,this.p,base[0]));
//
//
//
////                    /* 50 */           addToBot((AbstractGameAction)new DamageAllEnemiesAction((AbstractCreature)this.p, this.base, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE));
//                    /*    */         } else {
//                                    addToBot((AbstractGameAction)new HealAction(this.target,this.target,base[0]));
//                    /*    */         }
//                /*    */
//                /* 55 */         this.p.hand.moveToExhaustPile(this.p.hand.getBottomCard());
//                /* 56 */         tickDuration();
//                /*    */       } else {
//                /* 58 */         AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false);
//                /* 59 */         tickDuration();
//                /*    */       }
//            /*    */     } else {
//            /* 62 */       if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
//                /* 63 */         if (!AbstractDungeon.handCardSelectScreen.selectedCards.group.isEmpty())
//
//                    /*    */         {
//
//                    /* 65 */           for (Iterator<AbstractCard> var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator(); var1.hasNext(); this.p.hand.moveToExhaustPile(this.p.hand.getBottomCard())) {
//                        /* 66 */             AbstractCard c = var1.next();
//                        /* 67 */             if (c.color == AbstractCard.CardColor.CURSE) {
//                            addToBot((AbstractGameAction)new HealAction(this.p,this.p,base[0]));
////                            /* 68 */               addToBot((AbstractGameAction)new DamageAllEnemiesAction((AbstractCreature)this.p, this.base, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE));
//                            /*    */             } else {
//                            addToBot((AbstractGameAction)new HealAction(this.p,this.p,base[0]));
////                            /* 70 */               addToTop((AbstractGameAction)new DamageAction(this.target, this.info, AbstractGameAction.AttackEffect.FIRE));
//                            /*    */             }
//                        /*    */           }
//                    /*    */         }
//                /*    */
//                /* 75 */         AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
//                /* 76 */         AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
//                /*    */       }
//            /*    */
//            /* 79 */       tickDuration();
//            /*    */     }
    }
}
