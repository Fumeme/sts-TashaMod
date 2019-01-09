package defaultmod.powers;

import defaultmod.cards.ShortTerm;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;

import defaultmod.DefaultMod;

//Gain 1 dex for the turn for each card played.

public class UndyingPower extends AbstractPower {
	public AbstractCreature source;

	public static final String POWER_ID = defaultmod.DefaultMod.makeID("Mana");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	public UndyingPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		this.updateDescription();
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.loadRegion("focus");
		this.source = source;

	}

	// At the end of the turn, Remove gained dexterity.
	@Override
	/*    */ public int onAttacked(DamageInfo info, int damageAmount)
	/*    */ {
		/* 30 */ if ((info.type != DamageInfo.DamageType.HP_LOSS) && (info.owner != null) && (info.owner != this.owner)
				&& (damageAmount > 0) &&
				/* 31 */ (!this.owner.hasPower("Buffer"))) {
			if (this.owner.currentHealth <= damageAmount) {
				/* 32 */ flash();
				damageAmount = 0;
				AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
				flashWithoutSound();
				/*    */ }
		}
		/* 37 */ return damageAmount;
		/*    */ }

	@Override
	public void stackPower(int stackAmount) {
		this.fontScale = 8.0F;
		this.amount += stackAmount;
		if (this.amount <= 0) {
			AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
		}
	}

	// Update the description when you apply this power. (i.e. add or remove an "s"
	// in keyword(s))
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount;
	}

}