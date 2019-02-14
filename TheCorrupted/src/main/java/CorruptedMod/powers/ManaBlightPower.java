package CorruptedMod.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

//Gain 1 dex for the turn for each card played.

public class ManaBlightPower extends TwoAmountPower {

	public AbstractCreature source;
	public int amount2;

    public static final String POWER_ID = CorruptedMod.CorruptedBase.makeID("ManaBlightPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static int m = 0 ;

    public ManaBlightPower(final AbstractCreature owner, final AbstractCreature source, final int amount, final int initTimes) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        
        this.amount2 = initTimes;
        
        this.type = PowerType.DEBUFF;
        this.isTurnBased = false;
        this.loadRegion("frail");
        this.source = source;
        canGoNegative = false;
        this.updateDescription();

    }

    
public void blighten(int times) {
	
	for(int i = 0; i<= times; i++) {
	AbstractDungeon.actionManager.addToBottom(new DamageAction(this.owner,
			new DamageInfo(this.owner, this.amount, DamageType.THORNS),
			AbstractGameAction.AttackEffect.POISON));

	this.amount2 =0;
	}
}


@Override
public void atStartOfTurn() {
	
	blighten(this.amount2);
	

}
    
    
public void update(int slot)
{
  super.update(slot);
}
    	    

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount <= 0) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
        }
        
    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
    	this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount2;
    }




}
