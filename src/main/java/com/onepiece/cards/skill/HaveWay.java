package com.onepiece.cards.skill;

import basemod.abstracts.CustomCard;
import basemod.patches.com.megacrit.cardcrawl.relics.AbstractRelic.ObtainRelicGetHook;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.WingBoots;
import com.megacrit.cardcrawl.vfx.RelicAboveCreatureEffect;
import com.onepiece.helpers.ModHelper;
import com.onepiece.powers.ElasticPower;

import static com.onepiece.characters.LuffyChar.PlayerColorEnum.Luffy_RED;
import static com.onepiece.tag.CustomTags.BUDYS;
import static com.onepiece.tag.CustomTags.GUMGUM;

public class HaveWay extends CustomCard {
    public static final String ID = ModHelper.makePath("HaveWay");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH = "LuffyModRes/img/cards/HaveWay.png";
    private static final int COST = 2;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION; // 读取本地化的描述
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = Luffy_RED;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public HaveWay() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
//        this.baseBlock = 14;
//        this.magicNumber = this.baseMagicNumber = 14;
        this.exhaust = true;
        this.tags.add(BUDYS);

    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.updateCost(-1);
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
//        AbstractDungeon.player.obtainRelic();
        // 创建 Winged Boots 实例
        WingBoots wingedBoots = new WingBoots();
        // 设置计数为 1
        wingedBoots.counter = 1;
        wingedBoots.tips.clear();
        wingedBoots.tips.add(new PowerTip(wingedBoots.name, CARD_STRINGS.EXTENDED_DESCRIPTION[0] + wingedBoots.counter));
//        // 将遗物添加到玩家遗物列表
//        AbstractDungeon.player.relics.add(wingedBoots);
//        // 触发遗物的装备效果
//        wingedBoots.onEquip();
        if (!p.hasRelic(WingBoots.ID)) {
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), wingedBoots);
        } else {
            // 遍历玩家遗物列表
            for (AbstractRelic relic : p.relics) {
                if (relic.relicId.equals(WingBoots.ID)) {
                    // 重置 usedUp 状态
                    relic.usedUp = false;
                    relic.counter = 1;
                    relic.usedUp();
                    relic.grayscale = false;
                    relic.tips.clear();
                    relic.tips.add(new PowerTip(relic.name, CARD_STRINGS.EXTENDED_DESCRIPTION[0] + wingedBoots.counter));
                    // 刷新遗物描述（显示剩余次数）
                    relic.description = CARD_STRINGS.EXTENDED_DESCRIPTION[0] + wingedBoots.counter;
                    // 触发视觉反馈（可选）
                    AbstractDungeon.effectList.add(new RelicAboveCreatureEffect((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), relic));
                    break;
                }
            }
        }

    }
//        CardCrawlGame.screenShake.mildRumble(5.0F);


    public AbstractCard makeCopy() {
        return new HaveWay();
    }
}
