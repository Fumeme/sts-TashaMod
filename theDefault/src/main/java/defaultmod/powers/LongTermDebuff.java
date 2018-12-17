package defaultmod.powers;

import defaultmod.cards.ShortTerm;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;

import defaultmod.DefaultMod;

//Gain 1 dex for the turn for each card played.

public class LongTermDebuff extends AbstractPower {
    public AbstractCreature source;

    public static final String POWER_ID = defaultmod.DefaultMod.makeID("LongTermDebuff");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
int currM;
	

    public LongTermDebuff(final AbstractCreature owner, final AbstractCreature source, final int amount, final int M) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.loadRegion("flex");
        this.source = source;
        this.currM = M;
    }

    @Override
    public void atStartOfTurn() {
    
    	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner,
                new Mana(owner, owner, (owner.getPower(Mana.POWER_ID).amount)/2), (owner.getPower(Mana.POWER_ID).amount)/2));
    
    }
    // At the end of the turn, Remove gained dexterity.
    @Override
    public void atEndOfTurn(final boolean isPlayer) {
if(this.amount <1) {
	
	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner,
            new LongTermBuff(owner, owner, 3, currM ), 3));
	
	AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
}
    }
    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;

        }
        
    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
    	this.description = DESCRIPTIONS[0];
    }

}
