/*    */ package CorruptedMod.actions;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
/*    */ import com.megacrit.cardcrawl.core.AbstractCreature;

import CorruptedMod.powers.Mana;
import CorruptedMod.powers.ManaBlightPower;

/*    */
/*    */ public class ManaBlightTriggerAction extends AbstractGameAction
/*    */ {
int am = 0;
	/*    */
	/*    */ public ManaBlightTriggerAction(AbstractCreature target, AbstractCreature source, int am)
	/*    */ {
		/* 26 */ this.actionType = ActionType.SPECIAL;
		/* 27 */ this.duration = 0.1f;
		this.target = target;
		this.source = source;
		this.am = am;
		/*    */ }

	/*    */
	/*    */ public void update()
	/*    */ {
if(source.hasPower(Mana.POWER_ID)) {
	source.getPower(Mana.POWER_ID).flash();
}
System.out.println("adding " + am + "to " + ManaBlightPower.NAME);
        if(target.hasPower(ManaBlightPower.POWER_ID)) {
            if (target.getPower(ManaBlightPower.POWER_ID) instanceof TwoAmountPower) {
                ((TwoAmountPower)target.getPower(ManaBlightPower.POWER_ID)).amount2 += am;;
              } }

		isDone = true;
		/*    */ }
}