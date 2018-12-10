package defaultmod.powers;


import com.megacrit.cardcrawl.localization.PowerStrings;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;

import defaultmod.DefaultMod;
import defaultmod.cards.ShortTerm;

//Gain 1 dex for the turn for each card played.

public class ShortTermPower extends AbstractPower {
    public AbstractCreature source;

    public static final String POWER_ID = defaultmod.DefaultMod.makeID("Mana");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ShortTermPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        this.loadRegion("flex");
        this.source = source;

    }


    // At the end of the turn, Remove gained dexterity.
    public void atStartOfTurn(final boolean isPlayer) {


    	
    	AbstractDungeon.actionManager
    	.addToBottom(new com.megacrit.cardcrawl.actions.common
    	.RemoveSpecificPowerAction(this.owner, this.owner, "ShortTermPower"));
    }
    
    public void atEndOfTurn(final boolean isPlayer) {
    
    	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner,
                new Mana(owner, owner, -(this.amount)), -(this.amount)));
    	
    }
    

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
    	this.description = DESCRIPTIONS[0];
    }

}
