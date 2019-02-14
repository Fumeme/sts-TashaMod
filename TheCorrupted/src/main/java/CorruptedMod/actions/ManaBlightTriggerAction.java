/*    */ package CorruptedMod.actions;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.curses.Decay;
/*    */ import com.megacrit.cardcrawl.core.AbstractCreature;
/*    */ import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.PoisonPower;

import CorruptedMod.powers.DecayPower;
import CorruptedMod.powers.ManaBlightPower;

/*    */
/*    */ public class ManaBlightTriggerAction extends com.megacrit.cardcrawl.actions.AbstractGameAction
/*    */ {
int am = 0;
	/*    */
	/*    */ public ManaBlightTriggerAction(AbstractCreature target, AbstractCreature source, int am)
	/*    */ {
		/* 26 */ this.actionType = ActionType.SPECIAL;
		/* 27 */ this.duration = 0.1F;
		this.target = target;
		this.source = source;
		this.am = am;
		/*    */ }

	/*    */
	/*    */ public void update()
	/*    */ {

        if(target.hasPower(ManaBlightPower.POWER_ID)) {
            if (target.getPower(ManaBlightPower.POWER_ID) instanceof TwoAmountPower) {
                ((TwoAmountPower)target.getPower(ManaBlightPower.POWER_ID)).amount2 += am;;
              } 
        }

			/*    */ 
		isDone = true;
		/*    */ }
}