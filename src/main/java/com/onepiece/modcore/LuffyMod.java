package com.onepiece.modcore;

import basemod.BaseMod;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.blue.Defend_Blue;
import com.megacrit.cardcrawl.cards.red.Defend_Red;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.onepiece.cards.Strike;
import com.onepiece.characters.LuffyChar;

import static com.onepiece.characters.LuffyChar.PlayerColorEnum.Luffy;
import static com.onepiece.characters.LuffyChar.PlayerColorEnum.Luffy_RED;
@SpireInitializer
public class LuffyMod implements EditStringsSubscriber,EditCardsSubscriber, EditCharactersSubscriber {

    // 人物选择界面按钮的图片
    private static final String MY_CHARACTER_BUTTON = "LuffyModRes/img/char/Character_Button.png";
    // 人物选择界面的立绘
    private static final String MY_CHARACTER_PORTRAIT = "LuffyModRes/img/char/Character_Portrait.png";
    // 攻击牌的背景（小尺寸）
    private static final String BG_ATTACK_512 = "LuffyModRes/img/512/bg_attack_512.png";
    // 能力牌的背景（小尺寸）
    private static final String BG_POWER_512 = "LuffyModRes/img/512/bg_power_512.png";
    // 技能牌的背景（小尺寸）
    private static final String BG_SKILL_512 = "LuffyModRes/img/512/bg_skill_512.png";
    // 在卡牌和遗物描述中的能量图标
    private static final String SMALL_ORB = "LuffyModRes/img/char/small_orb.png";
    // 攻击牌的背景（大尺寸）
    private static final String BG_ATTACK_1024 = "LuffyModRes/img/1024/bg_attack.png";
    // 能力牌的背景（大尺寸）
    private static final String BG_POWER_1024 = "LuffyModRes/img/1024/bg_power.png";
    // 技能牌的背景（大尺寸）
    private static final String BG_SKILL_1024 = "LuffyModRes/img/1024/bg_skill.png";
    // 在卡牌预览界面的能量图标
    private static final String BIG_ORB = "LuffyModRes/img/char/card_orb.png";
    // 小尺寸的能量图标（战斗中，牌堆预览）
    private static final String ENEYGY_ORB = "LuffyModRes/img/char/cost_orb.png";
    public static final Color MY_COLOR = new Color(79.0F / 255.0F, 185.0F / 255.0F, 9.0F / 255.0F, 1.0F);

    public LuffyMod() {
        BaseMod.subscribe(this); // 告诉basemod你要订阅事件

        // 这里注册颜色
        BaseMod.addColor(Luffy_RED, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR,BG_ATTACK_512,BG_SKILL_512,BG_POWER_512,ENEYGY_ORB,BG_ATTACK_1024,BG_SKILL_1024,BG_POWER_1024,BIG_ORB,SMALL_ORB);
    }

    // 注解需要调用的方法，必须写
    public static void initialize() {
        new LuffyMod();
    }

    // 当开始添加人物时，调用这个方法
    @Override
    public void receiveEditCharacters() {
        // 向basemod注册人物
        BaseMod.addCharacter(new LuffyChar(CardCrawlGame.playerName), MY_CHARACTER_BUTTON, MY_CHARACTER_PORTRAIT, Luffy);
    }

    @Override
    public void receiveEditCards() {
        // TODO 这里写添加你卡牌的代码
        // 向basemod注册卡牌
        BaseMod.addCard(new Strike());
        BaseMod.addCard(new Defend_Blue());
        BaseMod.addCard(new Defend_Red());
    }

    @Override
    public void receiveEditStrings() {
        String lang;
        if (Settings.language == Settings.GameLanguage.ZHS) {
            lang = "ZHS"; // 如果语言设置为简体中文，则加载ZHS文件夹的资源
        }
        else {
            lang = "ENG"; // 如果没有相应语言的版本，默认加载英语
        }
        BaseMod.loadCustomStringsFile(CardStrings.class, "LuffyModRes/localization/" + lang + "/cards.json"); // 加载相应语言的卡牌本地化内容。
        // 如果是中文，加载的就是"ExampleResources/localization/ZHS/cards.json"
        // 这里添加注册本地化文本
        BaseMod.loadCustomStringsFile(CharacterStrings.class, "LuffyModRes/localization/" + lang + "/characters.json");
    }
}
