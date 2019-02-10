package CorruptedMod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

//Gain 1 dex for the turn for each card played.

public class Inefficiency extends AbstractPower {
    public AbstractCreature source;

    public static final String POWER_ID = CorruptedMod.CorruptedBase.makeID("Inefficiency");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public Inefficiency(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.type = PowerType.DEBUFF;
        this.isTurnBased = false;
        this.loadRegion("focus");
        this.source = source;
        canGoNegative = false;

    }

    @Override
	/*    */ public void atEndOfTurn(boolean isPlayer)
	/*    */ {
    	
    	if(owner.hasPower(Mana.POWER_ID) && owner.getPower(Mana.POWER_ID).amount <= 3) {
    		
    		AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    	}else {
    		
    		flash();
    	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner,owner,new Mana(owner,owner, -this.amount),-this.amount));
    }}

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount <= 0 || this.owner.getPower(Mana.POWER_ID).amount <=3 || !this.owner.hasPower(Mana.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
        }
        
    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
    	this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

}
