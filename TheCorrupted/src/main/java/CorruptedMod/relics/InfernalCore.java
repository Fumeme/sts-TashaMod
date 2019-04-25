package CorruptedMod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import CorruptedMod.CorruptedBase;
import DiamondMod.powers.DecayPower;
import DiamondMod.powers.Mana;
import basemod.abstracts.CustomRelic;

public class InfernalCore extends CustomRelic {
    
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     * 
     * Gain 1 energy.
     */

    // ID, images, text.
    public static final String ID = CorruptedMod.CorruptedBase.makeID("InfernalCore");
    public static final String IMG = CorruptedBase.makePath(CorruptedBase.PLACEHOLDER_RELIC);
    public static final String OUTLINE = CorruptedBase.makePath(CorruptedBase.PLACEHOLDER_RELIC_OUTLINE);

    public InfernalCore() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.STARTER, LandingSound.MAGICAL);
    }
    
@Override
    public void atBattleStart() {}
    
@Override
    public void atTurnStart() {
    	
    	if(AbstractDungeon.player.hasPower(DecayPower.POWER_ID) && (AbstractDungeon.player.getPower(DecayPower.POWER_ID).amount >0)) { 
    		
    		flash();
    		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new Mana(AbstractDungeon.player, AbstractDungeon.player, 1), 1));
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
        return new InfernalCore();
    }
}
