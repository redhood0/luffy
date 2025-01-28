package com.onepiece.cards.basic;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.TransformCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.onepiece.action.TransformCardAction;
import com.onepiece.helpers.ModHelper;
import com.onepiece.modcore.LuffyMod;
import com.onepiece.powers.GumGumFruitPower;
import com.onepiece.relic.GumGumFruitRelic;
import com.onepiece.relic.MyRelic;

import java.io.IOException;
import java.util.ArrayList;

import static com.onepiece.characters.LuffyChar.PlayerColorEnum.Luffy_RED;


//@AutoAdd.Ignore
public class GumGumFruit extends CustomCard {

    public static final String ID = ModHelper.makePath("GumGumFruit");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH = "LuffyModRes/img/cards/GumGumFruit.png";//todo:改成橡胶果实icon
    private static final int COST = 0;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION; // 读取本地化的描述
    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = Luffy_RED;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;

    public GumGumFruit() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
//        this.exhaust = true;
//        this.purgeOnUse = true;
        this.isInnate = true;
//        this.baseBlock = 5;
//        this.tags.add(CardTags.STARTER_DEFEND);
        // 使用后永久移除
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
//            this.upgradeBaseCost(0);
//            this.upgradeBlock(3);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        //获得power
        this.addToBot(new ApplyPowerAction(p, p, new GumGumFruitPower(p, 1), 1));
        //获得遗物
        AbstractRelic relic = new GumGumFruitRelic();
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F, relic);
                this.isDone = true;
            }
        });
        // 战斗中替换 A 卡为 B 卡
        for (AbstractCard card : new ArrayList<>(p.hand.group)) {
            if (card.cardID.equals(Strike.ID)) {
                AbstractDungeon.actionManager.addToBottom(new TransformCardAction(Strike.ID, new GumStrike()));
            }
        }

        //移除卡组中的打击，加入新卡
        for (AbstractCard card : new ArrayList<>(p.masterDeck.group)) {
            if (card.cardID.equals(Strike.ID)) {
                p.masterDeck.removeCard(card);
                p.masterDeck.addToTop(new GumStrike());
            }
            if (card.cardID.equals(GumGumFruit.ID)) {
                p.masterDeck.removeCard(card);
//                p.masterDeck.addToTop(new GumStrike());
            }

        }
//        CardModifierManager.addModifier(this, new Remov());
//        p.masterDeck.removeCard(this);

        // 获取当前玩家的角色
        AbstractPlayer player = AbstractDungeon.player;

        // 加载新的图片资源
        Texture newTexture = new Texture("LuffyModRes/img/char/luffy_gear1.png");

        player.img = newTexture;

        LuffyMod.saveField = true;
        try {
            SpireConfig config = new SpireConfig("LuffyMod", "Common");
            config.setBool("save_field", true);
            config.save();
        } catch (IOException e) {
            e.printStackTrace();
        }


// 替换角色的战斗图片
//        player.loadAnimation("images/characters/your_mod_character/default.atlas", newTexture, 1.0f);
    }

    public AbstractCard makeCopy() {
        return new GumGumFruit();
    }
}
