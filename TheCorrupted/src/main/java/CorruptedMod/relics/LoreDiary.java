package CorruptedMod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import CorruptedMod.CorruptedBase;
import CorruptedMod.patches.LoreDiaryReward;
import basemod.abstracts.CustomRelic;

public class LoreDiary extends CustomRelic {
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     * 
     * At the start of each combat, gain 1 strenght (i.e. Varja)
     */
    
    // ID, images, text.
    public static final String ID = CorruptedMod.CorruptedBase.makeID("LoreDiary");
    public static final String IMG = CorruptedBase.makePath(CorruptedBase.PLACEHOLDER_RELIC);
    public static final String OUTLINE = CorruptedBase.makePath(CorruptedBase.PLACEHOLDER_RELIC_OUTLINE_2);

    public LoreDiary() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SPECIAL, LandingSound.FLAT);
    }


@Override
	/*    */ public void onChestOpen(boolean bossChest)
	/*    */ {
		/* 44 */ if (!bossChest) { /*    */

			flash();
			AbstractDungeon.getCurrRoom().rewards.add(new LoreDiaryReward());
		}
}

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    // Which relic to return on making a copy of this relic.
    @Override
    public AbstractRelic makeCopy() {
        return new LoreDiary();
    }
}
