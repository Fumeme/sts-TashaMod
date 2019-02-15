/*    */ package CorruptedMod.actions;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
/*    */ import com.megacrit.cardcrawl.core.AbstractCreature;
import CorruptedMod.powers.Mana;
import CorruptedMod.powers.ManaBlightPower;

/*    */
/*    */ public class ManaBlightTriggerAction extends AbstractGameAction
/*    */ {
int apply = 0;
	/*    */
	/*    */ public ManaBlightTriggerAction(AbstractCreature target, AbstractCreature source, int am)
	/*    */ {
		/* 26 */ this.actionType = ActionType.POWER;
		/* 27 */ this.duration = 0.2f;
		this.target = target;
		this.source = source;
		this.apply = am;
		/*    */ }

	/*    */
	/*    */ public void update()
	/*    */ {
		System.out.println("starting action");

        if(target.hasPower(ManaBlightPower.POWER_ID)) {
        	System.out.println("checking if " + target + " has mana blight.");
        	
            if (target.getPower(ManaBlightPower.POWER_ID) instanceof TwoAmountPower) {
            	System.out.println("adding " + this.apply + "to " + target + "'s Mana Blight counter.");
            	
            	target.getPower(ManaBlightPower.POWER_ID).flash();
                ((TwoAmountPower)target.getPower(ManaBlightPower.POWER_ID)).amount2 += this.apply;
                target.getPower(ManaBlightPower.POWER_ID).amount -=1;
              }
            }

		isDone = true;
		/*    */ }
}