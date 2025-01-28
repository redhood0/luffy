package com.onepiece.cards.skill;

import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import basemod.interfaces.ISubscriber;
import basemod.interfaces.OnPlayerDamagedSubscriber;
import basemod.interfaces.PostDeathSubscriber;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.RelicAboveCreatureEffect;
import com.onepiece.helpers.ModHelper;
import com.onepiece.powers.FullPower;

import static com.onepiece.characters.LuffyChar.PlayerColorEnum.Luffy_RED;
import static com.onepiece.tag.CustomTags.BUDYS;
import static com.onepiece.tag.CustomTags.FOOD;

public class NothingHappen extends CustomCard {
    public static final String ID = ModHelper.makePath("NothingHappen");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH = "LuffyModRes/img/cards/NothingHappen.png";
    private static final int COST = -2;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION; // 读取本地化的描述
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = Luffy_RED;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
//    private static boolean triggered = false; // 防止重复触发

    public NothingHappen() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
//        this.baseBlock = 14;
        this.magicNumber = this.baseMagicNumber = 3;
//        this.exhaust = true;

        this.tags.add(BUDYS);

    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = CARD_STRINGS.EXTENDED_DESCRIPTION[0];
        return false;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
//            this.upgradeMagicNumber(2);
            this.selfRetain = true;
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    public static void initialize() {
        // 订阅玩家死亡前事件
        BaseMod.subscribe(new DeathTriggerSubscriber(), OnPlayerDamagedSubscriber.class);
//        BaseMod.subscribeToOnPlayerDeathPre(DeathTriggerCard::onPlayerDeathPre);
    }

    private static class DeathTriggerSubscriber implements OnPlayerDamagedSubscriber {
        @Override
        public int receiveOnPlayerDamaged(int var1, DamageInfo var2) {
//            if (triggered) return var1;

            AbstractPlayer player = AbstractDungeon.player;
            // 检查玩家是否濒死（当前HP ≤ 0 且未被标记为死亡）

            if (player != null && (player.currentHealth - var1) < 1 && !player.isDead) {
                // 检查手牌中是否有此卡
                for (AbstractCard card : player.hand.group) {
                    if (card instanceof NothingHappen) {
                        player.currentHealth = 0;
                        card.flash();
                        triggerEffect((NothingHappen) card);

//                        triggered = true; // 防止重复触发
                        return 0;
                    }
                }
            }
            return var1;
        }
    }

    private static void triggerEffect(NothingHappen card) {
        // 示例：恢复1点生命避免死亡
//        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        int healAmt = AbstractDungeon.player.maxHealth / 2;
        if (healAmt < 1) {
            healAmt = 1;
        }
        AbstractDungeon.player.heal(healAmt, true);
//        AbstractDungeon.player.heal(1, true);
        // 移除此卡
        AbstractDungeon.player.hand.removeCard(card);

        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.cardID.equals(NothingHappen.ID)) {
                AbstractDungeon.player.masterDeck.removeCard(c);
                break;
            }
        }
        // 显示特效
        card.flash();
//        card.addToBot(new MakeTempCardInDrawPileAction(new Wound(), 1, true, true));
//        AbstractDungeon.effectList.add(new RelicAboveCreatureEffect(
//                AbstractDungeon.player, card
//        ));
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {


    }

    public AbstractCard makeCopy() {
        return new NothingHappen();
    }
}
