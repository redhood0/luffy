package com.onepiece.relic;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.ui.campfire.DigOption;
import com.onepiece.helpers.ModHelper;

import java.util.ArrayList;

public class MyRelic extends CustomRelic {
    // 遗物ID（此处的ModHelper在“04 - 本地化”中提到）
    public static final String ID = ModHelper.makePath("MyRelic");
    // 图片路径（大小128x128，可参考同目录的图片）
    private static final String IMG_PATH = "LuffyModRes/img/relics/relic_hat.png";
    // 遗物未解锁时的轮廓。可以不使用。如果要使用，取消注释
    // private static final String OUTLINE_PATH = "LuffyModRes/img/relics/MyRelic_Outline.png";
    // 遗物类型
    private static final RelicTier RELIC_TIER = RelicTier.STARTER;
    // 点击音效
    private static final LandingSound LANDING_SOUND = LandingSound.FLAT;

    public MyRelic() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);

        // 如果你需要轮廓图，取消注释下面一行并注释上面一行，不需要就删除
        // super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(OUTLINE_PATH), RELIC_TIER, LANDING_SOUND);
    }

    // 获取遗物描述，但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

//    public AbstractRelic makeCopy() {
//        return new MyRelic();
//    }

//    public void addCampfireOption(ArrayList<AbstractCampfireOption> options) {
//        options.add(new DigOption());
//    }
    // 核心逻辑：在Boss房胜利时触发
    @Override
    public void onVictory() {
        if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) { // 判断当前房间是否是Boss房
            flash(); // 播放遗物闪光特效

            AbstractDungeon.combatRewardScreen.rewards.add(new RewardItem(AbstractDungeon.returnRandomRelic(AbstractDungeon.returnRandomRelicTier())));
            AbstractDungeon.combatRewardScreen.open();
//            AbstractDungeon.actionManager.addToTop(new AbstractGameAction() {
//                @Override
//                public void update() {
//                    if (!AbstractDungeon.isScreenUp) {
//
////                        AbstractDungeon.combatRewardScreen.rewards.clear();
////                        AbstractDungeon.combatRewardScreen.rewards.add(new RewardItem(AbstractDungeon.returnRandomScreenlessRelic(RelicTier.COMMON)));
////                        AbstractDungeon.combatRewardScreen.rewards.add(new RewardItem(AbstractDungeon.returnRandomScreenlessRelic(RelicTier.UNCOMMON)));
//                        AbstractDungeon.combatRewardScreen.rewards.add(new RewardItem(AbstractDungeon.returnRandomRelic(AbstractDungeon.returnRandomRelicTier())));
//                        AbstractDungeon.combatRewardScreen.positionRewards();
////                        AbstractDungeon.overlayMenu.proceedButton.setLabel(this.DESCRIPTIONS[2]);
//
//                        AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.25F;
//                    }
//
//                }
//            });
        }
    }

    public void onEnterRoom(AbstractRoom room) {
        int currentFloor = AbstractDungeon.floorNum;

        if(room instanceof MonsterRoomBoss|| room.eliteTrigger ){
            if(room.eliteTrigger){
                AbstractRelic relic = AbstractDungeon.returnRandomRelic(RelicTier.COMMON);
                room.rewards.add(new RewardItem(relic));
            }else {
                AbstractRelic relic = AbstractDungeon.returnRandomRelic(RelicTier.RARE);
                room.rewards.add(new RewardItem(relic));
            }
        }
    }
}