package com.onepiece.modcore;

import basemod.AutoAdd;
import basemod.BaseMod;

import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.*;
import com.onepiece.cards.skill.NothingHappen;
import com.onepiece.characters.LuffyChar;
import com.onepiece.monster.BuggyBoss;
import com.onepiece.relic.GoingMerry;
import com.onepiece.relic.GumGumFruitRelic;
import com.onepiece.relic.MyRelic;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import static com.megacrit.cardcrawl.core.Settings.language;
import static com.onepiece.characters.LuffyChar.PlayerColorEnum.Luffy;
import static com.onepiece.characters.LuffyChar.PlayerColorEnum.Luffy_RED;

@SpireInitializer
public class LuffyMod implements EditStringsSubscriber, EditCardsSubscriber, EditCharactersSubscriber, EditRelicsSubscriber, EditKeywordsSubscriber, PostInitializeSubscriber, AddAudioSubscriber {

    // 人物选择界面按钮的图片
    private static final String MY_CHARACTER_BUTTON = "LuffyModRes/img/char/Character_Button.png";
    // 人物选择界面的立绘
    private static final String MY_CHARACTER_PORTRAIT = "LuffyModRes/img/char/Character_Portrait1.png";
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
    public static final Color MY_COLOR = new Color(255.0F / 255.0F, 39.0F / 255.0F, 9.0F / 255.0F, 1.0F);


    public LuffyMod() {
        BaseMod.subscribe(this); // 告诉basemod你要订阅事件

        // 这里注册颜色
        BaseMod.addColor(Luffy_RED, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, BG_ATTACK_512, BG_SKILL_512, BG_POWER_512, ENEYGY_ORB, BG_ATTACK_1024, BG_SKILL_1024, BG_POWER_1024, BIG_ORB, SMALL_ORB);
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
//        BaseMod.addCard(new Strike());
//        BaseMod.addCard(new NothingHappen());


        new AutoAdd("LuffyMod") // 这里填写你在ModTheSpire.json中写的modid
                .packageFilter("com.onepiece.cards") // 寻找所有和此类同一个包及内部包的类（本例子是所有卡牌）
                .setDefaultSeen(true) // 是否将卡牌标为可见
                .cards(); // 开始批量添加卡牌
        NothingHappen.initialize(); // 初始化事件订阅
    }

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelic(new MyRelic(), RelicType.SHARED); // RelicType表示是所有角色都能拿到的遗物，还是一个角色的独有遗物
        BaseMod.addRelic(new GumGumFruitRelic(), RelicType.SHARED);
        BaseMod.addRelic(new GoingMerry(), RelicType.SHARED);
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String lang = "eng";
        if (language == Settings.GameLanguage.ZHS) {
            lang = "zhs";
        }

        String json = Gdx.files.internal("LuffyModRes/localization/keywords_" + lang + ".json")
                .readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = gson.fromJson(json, Keyword[].class);
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                // 这个id要全小写
                BaseMod.addKeyword(keyword.NAMES[0], keyword.NAMES, keyword.DESCRIPTION);
            }
        }

//        BaseMod.addKeyword(new String[] {"弹力"}, "消耗 #y弹力 可以强化橡胶人的各项能力。");
    }

    @Override
    public void receiveEditStrings() {
        String lang;
        if (language == Settings.GameLanguage.ZHS) {
            lang = "ZHS"; // 如果语言设置为简体中文，则加载ZHS文件夹的资源
        } else {
            lang = "ENG"; // 如果没有相应语言的版本，默认加载英语
        }
        BaseMod.loadCustomStringsFile(CardStrings.class, "LuffyModRes/localization/" + lang + "/cards.json"); // 加载相应语言的卡牌本地化内容。
        // 如果是中文，加载的就是"ExampleResources/localization/ZHS/cards.json"
        // 这里添加注册本地化文本
        BaseMod.loadCustomStringsFile(CharacterStrings.class, "LuffyModRes/localization/" + lang + "/characters.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, "LuffyModRes/localization/" + lang + "/relics.json");
        // 添加power json
        BaseMod.loadCustomStringsFile(PowerStrings.class, "LuffyModRes/localization/" + lang + "/powers.json");

        BaseMod.loadCustomStringsFile(MonsterStrings.class, "LuffyModRes/localization/" + lang + "/monsters.json");

    }

    // 为模组核心添加新的字段
    public static boolean saveField;

    @Override
    public void receivePostInitialize() {
        //初始化xxx
        try {
            // 设置默认值
            Properties defaults = new Properties();
            defaults.setProperty("save_field", "false");
            // defaults.setProperty("save_field_2", "false");

            // 第一个字符串输入你的modid
            SpireConfig config = new SpireConfig("LuffyMod", "Common", defaults);
            // 如果之前有数据，则读取本地保存的数据，没有就使用上面设置的默认数据
            saveField = config.getBool("save_field");
        } catch (IOException var2) {
            var2.printStackTrace();
        }

//        AudioManager.addAudio("CUSTOM_BUTTON_SOUND", "yourModNameResources/audio/custom_button_sound.ogg");

        BaseMod.subscribe(new PostDeathSubscriber() {
            @Override
            public void receivePostDeath() {
                //死亡归零
                LuffyMod.saveField = false;
                try {
                    SpireConfig config = new SpireConfig("LuffyMod", "Common");
                    config.setBool("save_field", false);
                    config.save();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        BaseMod.subscribe(new PostDungeonInitializeSubscriber() {

            @Override
            public void receivePostDungeonInitialize() {
                //地下城初始化归零
                LuffyMod.saveField = false;
                try {
                    SpireConfig config = new SpireConfig("LuffyMod", "Common");
                    config.setBool("save_field", false);
                    config.save();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        receiveMonstor();
    }

    private void receiveMonstor() {

        BaseMod.addMonster(BuggyBoss.ID, () -> new BuggyBoss(0.0F, 0.0F));
        String file= imagePath("monsters/mapicons/") + BuggyBoss.class.getSimpleName() + "_map.png";
        BaseMod.addBoss("Exordium", BuggyBoss.ID , file, file);
        BaseMod.addBoss("Exordium", BuggyBoss.ID , file, file);
        BaseMod.addBoss("Exordium", BuggyBoss.ID , file, file);
//        BaseMod.addBoss("Exordium", BuggyBoss.ID , file, file);
//        BaseMod.addBoss("Exordium", BuggyBoss.ID , file, file);
//        BaseMod.addBoss("Exordium", BuggyBoss.ID , file, file);

//        BossGeneratePatch.addBoss("Exordium", AnonMonster.ID, imagePath("monsters/mapicons/") + AnonMonster.class
//                .getSimpleName() + "_map.png", imagePath("monsters/mapicons/") + AnonMonster.class
//                .getSimpleName() + "_map.png");

//        BaseMod.addMonster(TakiMonster.ID, () -> new TakiMonster(0.0F, 0.0F));
//        BossGeneratePatch.addBoss("TheCity", TakiMonster.ID, imagePath("monsters/mapicons/") + TakiMonster.class
//                .getSimpleName() + "_map.png", imagePath("monsters/mapicons/") + TakiMonster.class
//                .getSimpleName() + "_map.png");
//
//        BaseMod.addMonster(MutsumiMonster.ID, () -> new MutsumiMonster(0.0F, 0.0F));
//        BossGeneratePatch.addBoss("TheBeyond", MutsumiMonster.ID, imagePath("monsters/mapicons/") + UikaMonster.class
//                .getSimpleName() + "_map.png", imagePath("monsters/mapicons/") + UikaMonster.class
//                .getSimpleName() + "_map.png");
//
//        BaseMod.addMonster(UikaMonster.ID, () -> new UikaMonster(0.0F, 0.0F));
//        BossGeneratePatch.addBoss("TheBeyond", UikaMonster.ID, imagePath("monsters/mapicons/") + UikaMonster.class
//                .getSimpleName() + "_map.png", imagePath("monsters/mapicons/") + UikaMonster.class
//                .getSimpleName() + "_map.png");
//
//        BaseMod.addMonster(MutsumiOperatorMonster.ID, () -> new MutsumiOperatorMonster(0.0F, 0.0F));
//        BossGeneratePatch.addBoss("TheEnding", MutsumiOperatorMonster.ID, imagePath("monsters/mapicons/") + SakiShadowMonster.class
//                .getSimpleName() + "_map.png", imagePath("monsters/mapicons/") + SakiShadowMonster.class
//                .getSimpleName() + "_map.png");
    }

    public void receiveAddAudio() {
        BaseMod.addAudio("luffy:luffystart", "LuffyModRes/music/luffy.MP3");
        BaseMod.addAudio("luffy:Yohohoho", "audio/music/Yohohoho.MP3");
        BaseMod.addAudio("luffy:gear2", "audio/music/gear2.MP3");
        BaseMod.addAudio("luffy:gear3", "audio/music/gear3.MP3");
        BaseMod.addAudio("luffy:haki", "audio/music/haki.MP3");
        BaseMod.addAudio("luffy:BuggyBgm", "audio/music/BuggyBgm.MP3");
        BaseMod.addAudio("luffy:BuggySmile", "audio/music/BuggySmile.MP3");
        BaseMod.addAudio("luffy:BuggyBaraBara", "audio/music/BuggyBaraBara.MP3");

//        new AbstractAnimation("PractiseBasketballCritAnimation", "IkunModResources/animation/PractiseBasketballCrit.atlas", "IkunModResources/animation/PractiseBasketballCrit.json", 1.5F * Settings.scale, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, Settings.WIDTH, Settings.HEIGHT, 1.5F * Settings.scale);
//        new AbstractAnimation("PractiseDanceAnimation", "IkunModResources/animation/PractiseDance.atlas", "IkunModResources/animation/PractiseDance.json", 1.5F * Settings.scale, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, Settings.WIDTH, Settings.HEIGHT, Settings.scale);
//        new AbstractAnimation("PractiseHeartAnimation", "IkunModResources/animation/PractiseHeart.atlas", "IkunModResources/animation/PractiseHeart.json", 1.5F * Settings.scale, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, Settings.WIDTH, Settings.HEIGHT, Settings.scale);
//        new AbstractAnimation("PractiseWTFAnimation", "IkunModResources/animation/PractiseWTF.atlas", "IkunModResources/animation/PractiseWTF.json", 1.5F * Settings.scale, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, Settings.WIDTH, Settings.HEIGHT, Settings.scale);
//        new AbstractAnimation("PractiseIronAnimation", "IkunModResources/animation/PractiseIron.atlas", "IkunModResources/animation/PractiseIron.json", 1.5F * Settings.scale, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, Settings.WIDTH, Settings.HEIGHT, 1.5F * Settings.scale);
//        new AbstractAnimation("PractiseSpinTheBallAnimation", "IkunModResources/animation/PractiseSpinTheBall.atlas", "IkunModResources/animation/PractiseSpinTheBall.json", 1.5F * Settings.scale, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, Settings.WIDTH, Settings.HEIGHT, Settings.scale);
//        new AbstractAnimation("PractiseRapAnimation", "IkunModResources/animation/PractiseRap.atlas", "IkunModResources/animation/PractiseRap.json", 1.5F * Settings.scale, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, Settings.WIDTH, Settings.HEIGHT, Settings.scale);
    }

    //    @Override
//    public void receivePostInitialize() {
//        CardTags CUSTOM_TAG = CardTags.create("MyMod:CustomTag");
//        // 注册自定义标签
//        BaseMod.addCardTag("CustomTag", CUSTOM_TAG);
//    }

    public static String imagePath(String file) {
        return "LuffyModRes/img/" + file;
    }
}
