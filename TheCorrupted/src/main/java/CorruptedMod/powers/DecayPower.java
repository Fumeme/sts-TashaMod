/*    */ package CorruptedMod.powers;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
/*    */ import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
/*    */ import com.megacrit.cardcrawl.core.AbstractCreature;
/*    */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*    */ import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
/*    */ import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
/*    */ import com.megacrit.cardcrawl.rooms.AbstractRoom;

/*    */ public class DecayPower extends AbstractPower implements HealthBarRenderPower
/*    */ {
	public static final String POWER_ID = CorruptedMod.CorruptedBase.makeID("DecayPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	/*    */
	/*    */ public DecayPower(AbstractCreature owner, AbstractCreature source, int DecayAmt)
	/*    */ {
		/* 23 */ this.name = NAME;
		/* 25 */ this.owner = owner;
		/* 27 */ this.amount = DecayAmt;
		canGoNegative = false;
		this.ID = POWER_ID;
		this.updateDescription();
		/* 34 */ this.loadRegion("poison");
		/* 35 */ this.type = AbstractPower.PowerType.DEBUFF;
		/*    */
		/* 37 */ this.isTurnBased = true;
		this.priority = 10;
		/*    */ }

	@Override
	public int getHealthBarAmount() {
		int HpLoss = 0;
		if (!this.owner.hasPower(BlackArmorPower.POWER_ID)) {
			HpLoss = this.amount - this.owner.currentBlock;
			if (this.owner.hasPower(MetallicizePower.POWER_ID)
					&& this.owner.getPower(MetallicizePower.POWER_ID).amount > 0) {
				HpLoss -= this.owner.getPower(MetallicizePower.POWER_ID).amount;
			}
			if (this.owner.hasPower(PlatedArmorPower.POWER_ID)
					&& this.owner.getPower(PlatedArmorPower.POWER_ID).amount > 0) {
				HpLoss -= this.owner.getPower(PlatedArmorPower.POWER_ID).amount;
			}
		}
		if (HpLoss < 0 || this.owner.hasPower(BlackArmorPower.POWER_ID)) {
			HpLoss = 0;
		}
		return HpLoss;
	}

	@Override
	public Color getColor() {
		if (this.owner.isPlayer) {
			return Color.DARK_GRAY;
		} else {
			return Color.FIREBRICK;
		}
	}

	/*    */
	/*    */ public void updateDescription()
	/*    */ {
		/* 47 */ if ((this.owner == null) || (this.owner.isPlayer)) {
			/* 48 */ this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
			/*    */ } else {
			/* 50 */ this.description = (DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[1]);
			/*    */ }
		/*    */ }

	/*    */ @Override
	/*    */ public void stackPower(int stackAmount)
	/*    */ {
		/* 56 */ super.stackPower(stackAmount);
		/*    */
		/* 58 */
		/* 59 */ if (this.amount <= 0) {
			AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
		}
		/*    */
		/*    */ }

	/*    */ @Override
	/*    */ public void atEndOfTurn(boolean isPlayer)
	/*    */ {
		/* 65 */ if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)
		/*    */ {
			/* 67 */

			/* 68 */ flashWithoutSound();
			if (!this.owner.hasPower(BlackArmorPower.POWER_ID)) {
				if (!this.owner.hasPower(InfernalFormPower.POWER_ID)) {

					System.out.println("Damaging " + this.owner + " for " + this.amount + " damage");
					flash();
					/* 69 */ AbstractDungeon.actionManager.addToBottom(
							new DamageAction(this.owner, new DamageInfo(this.owner, this.amount, DamageType.THORNS),
									AbstractGameAction.AttackEffect.POISON));
				}
			} else {

				AbstractDungeon.actionManager
						.addToBottom(new GainBlockAction(this.owner, this.owner, this.amount, isPlayer));

				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner,
						new BlackArmorPower(this.owner, this.owner, -1), -1));
			}

			if (!this.owner.hasPower(EvilCloudPower.POWER_ID)
					|| this.owner.getPower(EvilCloudPower.POWER_ID).amount <= 0) {
				System.out.println("minus one decay on " + this.owner);
				AbstractDungeon.actionManager.addToBottom(
						new ApplyPowerAction(this.owner, this.owner, new DecayPower(this.owner, this.owner, -1), -1));
				/*    */ } else {
				AbstractDungeon.actionManager.addToBottom(
						new ApplyPowerAction(this.owner, this.owner, new DecayPower(this.owner, this.owner, 1), 1));
			}
			/*    */ }
		/*    */ }
	/*    */ }

/*
 * Location: C:\Program Files
 * (x86)\Steam\steamapps\common\SlayTheSpire\desktop-1.0.jar!\com\megacrit\
 * cardcrawl\powers\PoisonPower.class Java compiler version: 8 (52.0) JD-Core
 * Version: 0.7.1
 */