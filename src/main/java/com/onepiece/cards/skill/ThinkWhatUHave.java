package com.onepiece.cards.skill;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.unique.CalculatedGambleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.onepiece.helpers.ModHelper;

import static com.onepiece.characters.LuffyChar.PlayerColorEnum.Luffy_RED;
import static com.onepiece.tag.CustomTags.BUDYS;
import static com.onepiece.tag.CustomTags.FOOD;

public class ThinkWhatUHave extends CustomCard {
    public static final String ID = ModHelper.makePath("ThinkWhatUHave");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH = "LuffyModRes/img/cards/ThinkWhatUHave.png";
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION; // 读取本地化的描述
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = Luffy_RED;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public ThinkWhatUHave() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
//        this.baseBlock = 14;
        this.magicNumber = this.baseMagicNumber = 3;

        this.tags.add(BUDYS);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {

        this.addToBot(new GainBlockAction(p, p, checkBudys() * this.magicNumber));
    }

    //查询一下卡组中的budys的含量
    public int checkBudys() {
        int num = 0;

        if (AbstractDungeon.player.drawPile.group.size() > 0) {
            for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                if (c.hasTag(BUDYS)) {
                    num++;
                }
            }
        }

        if (AbstractDungeon.player.hand.group.size() > 0) {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c.hasTag(BUDYS)) {
                    num++;
                }
            }
        }

        return num;
    }


    public void applyPowers() {
        this.baseBlock = checkBudys() * this.magicNumber;
//        if (this.upgraded) {
//            this.baseBlock += 3;
//        }

        super.applyPowers();

        if (!this.upgraded) {
            this.rawDescription = CARD_STRINGS.DESCRIPTION;
        } else {
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
        }

        this.rawDescription = this.rawDescription + CARD_STRINGS.EXTENDED_DESCRIPTION[0] + this.baseBlock + CARD_STRINGS.EXTENDED_DESCRIPTION[1];
        this.initializeDescription();
    }

    public AbstractCard makeCopy() {
        return new ThinkWhatUHave();
    }
}
