package com.onepiece.monster;

import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.ShakeScreenAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;
import com.megacrit.cardcrawl.monsters.exordium.GremlinFat;
import com.megacrit.cardcrawl.monsters.exordium.GremlinWarrior;
import com.megacrit.cardcrawl.monsters.exordium.JawWorm;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.onepiece.cards.skill.BuggyBoom;
import com.onepiece.helpers.ModHelper;
import com.onepiece.monster.Power.BuggyBoomPower;

import java.util.ArrayList;

import static com.onepiece.modcore.LuffyMod.imagePath;

public class BuggyBoss extends CustomMonster {
    // 怪物ID（此处的ModHelper在“04 - 本地化”中提到）
    public static final String ID = ModHelper.makePath("BuggyBoss");
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    // 怪物的图片，请自行添加
    public static final String IMG = imagePath("monsters/boss/") + BuggyBoss.class.getSimpleName() + ".png";
    public AbstractMonster[] gremlins = new AbstractMonster[3];
    public int boomDamge = 25;
    public int count = 3;
    private boolean firstTurn = true;
    public static String[] DIALOG = monsterStrings.DIALOG;


    public BuggyBoss(float x, float y) {
        // 参数的作用分别是：
        // 名字
        // ID
        // 最大生命值，由于在之后还会设置可以随意填写
        // hitbox偏移量 - x方向
        // hitbox偏移量 - y方向
        // hitbox大小 - x方向（会影响血条宽度）
        // hitbox大小 - y方向
        // 图片
        // 怪物位置（x,y）
        super(NAME, ID, 140, 0.0F, 0.0F, 250.0F, 270.0F, IMG, x, y);

        // 如果你要做进阶改变血量和伤害意图等，这样写
        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(220, 220);
        } else {
            this.setHp(200, 200);
        }

        // 怪物伤害意图的数值
        int slashDmg;
        int multiDmg;
        if (AbstractDungeon.ascensionLevel >= 2) {
            slashDmg = 20;
            multiDmg = 4;
        } else {
            slashDmg = 18;
            multiDmg = 3;
        }
        // 意图0的伤害
        this.damage.add(new DamageInfo(this, slashDmg));
        // 意图1的伤害
        this.damage.add(new DamageInfo(this, multiDmg));


    }

    public static ArrayList<MonsterGroup> getEncounter(String encounterKey) {
        ArrayList<MonsterGroup> encounters = new ArrayList<>();
        switch (encounterKey) {
            case "MyCustomPool":
                encounters.add(new MonsterGroup(
                        new AbstractMonster[]{
                                new Cultist(-400.0F, 0.0F),
                                new JawWorm(-200.0F, 0.0F),
                                new GremlinFat(0.0F, 0.0F)
                        }
                ));
                break;
            default:
                break;
        }
        return encounters;
    }

    // 战斗开始时
    public void usePreBattleAction() {
        //音乐相关
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("BuggyBgm.MP3");

        AbstractMonster m1 = new GremlinWarrior(-300.0F, 0.0F);
        AbstractMonster m2 = new GremlinWarrior(-500.0F, 0.0F);
//        AbstractMonster m2 = new Cultist(-100.0F, 0.0F);
        addToBot(new SpawnMonsterAction(m1, false));
        addToBot(new SpawnMonsterAction(m2, false));

//        this.gremlins[0] = BuggyBoss.getEncounter("MyCustomPool").get(0).monsters.get(0);
////        this.gremlins[1] = (AbstractMonster)AbstractDungeon.getMonsters().monsters.get(1);
//        this.gremlins[2] = null;

//        for (AbstractMonster m : this.gremlins) {
//            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new MinionPower(this)));
//        }
//        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new RitualPower(this, 5, false)));

    }


    // 当怪物roll意图的时候，这里设置其意图。num是一个0~99的随机数。
    @Override
    public void getMove(int num) {
        // 有40%的概率执行意图0，60%执行意图1
        int move;
        if (this.firstTurn) {
            this.firstTurn = false;
//            this.setMove(STICKY_NAME, (byte) 4, Intent.STRONG_DEBUFF);
            this.setMove("boom him!", (byte) 0, Intent.UNKNOWN);
        }
        //无论如何进来都要先执行炸弹一次


//        if (num < 40) {
//            this.setMove((byte)0, Intent.ATTACK, damage.get(0).base);
//        } else {
//            this.setMove((byte)1, Intent.BUFF, damage.get(1).base, 3, true);
//        }

    }

    private void playSfx() {
        int roll = MathUtils.random(1);
        //todo：后续换成巴基的音效
        if (roll == 0) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_SLIMEBOSS_1A"));
        } else {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_SLIMEBOSS_1B"));
        }

    }

    // 执行动作
    @Override
    public void takeTurn() {
        // nextMove就是roll到的意图，0就是意图0，1就是意图1
        switch (this.nextMove) {
            case 0:
                //第一步，布置炸弹
                AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, DIALOG[0], 1.0F, 2.0F));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new BuggyBoomPower(this, count, boomDamge), count));
//                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new BuggyBoom(), 1, true, true));
//                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new BuggyBoom(), true));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new BuggyBoom(), 1));


                this.setMove((byte) 1, Intent.ATTACK, damage.get(1).base, 4, true);
//                this.createIntent();
//                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                break;
            case 1:
                //第二步，三连击
//                this.playSfx();
                //todo：后续换成巴基音效
                AbstractDungeon.actionManager.addToBottom(new SFXAction("luffy:BuggySmile"));
                AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, DIALOG[1], 1.0F, 2.0F));
                AbstractDungeon.actionManager.addToBottom(new ShakeScreenAction(0.3F, ScreenShake.ShakeDur.LONG, ScreenShake.ShakeIntensity.LOW));
                for (int i = 0; i < 3; i++) {
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                }
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
//                this.setMove("Hide", (byte) 2, Intent.UNKNOWN, ((DamageInfo) this.damage.get(1)).base);
                this.setMove("Hide", (byte) 2, Intent.UNKNOWN);
                break;
            case 2:
                //无实体，躲1轮
                if (!this.hasPower("Intangible")) {
                    AbstractDungeon.actionManager.addToBottom(new SFXAction("luffy:BuggyBaraBara"));
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new IntangiblePower(this, 1)));
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 1), 1));
//PainfulStabsPower
                }
                this.setMove("boom him!", (byte) 0, Intent.UNKNOWN);
                break;

        }

        // 要加一个rollmove的action，重roll怪物的意图
//        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
//        this.setMove((byte)1,Intent.BUFF);
//        this.setMove((byte)1, Intent.ATTACK, ((DamageInfo)this.damage.get(2)).base, 6, true);
    }
}
