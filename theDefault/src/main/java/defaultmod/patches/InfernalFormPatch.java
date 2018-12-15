package defaultmod.patches;


import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.unique.PoisonLoseHpAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.PoisonPower;

import defaultmod.powers.Mana;

@SpirePatch(cls="com.megacrit.cardcrawl.powers.PoisonPower", method="atStartOfTurn")


public class InfernalFormPatch {

	@SpireInsertPatch(
			loc=69
			
		)


	public static void Insert(PoisonPower __instance) {
		if (AbstractDungeon.player.hasPower(PoisonPower.POWER_ID)) {
		 AbstractDungeon.actionManager.addToBottom(new PoisonLoseHpAction(AbstractDungeon.player,AbstractDungeon.player , AbstractDungeon.player.getPower(PoisonPower.POWER_ID).amount, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.POISON));
		}
	}
	}

