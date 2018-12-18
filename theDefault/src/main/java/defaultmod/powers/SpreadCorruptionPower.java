package defaultmod.powers;

import defaultmod.cards.ShortTerm;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
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

public class SpreadCorruptionPower extends AbstractPower {
	public AbstractCreature source;

	public static final String POWER_ID = defaultmod.DefaultMod.makeID("SpreadCorruptionPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	public SpreadCorruptionPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		this.updateDescription();
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.loadRegion("corruption");
		this.source = source;

	}

	// At the end of the turn, Remove gained dexterity.
	@Override
	public void atStartOfTurn() {

		if (this.owner.hasPower(Mana.POWER_ID)) {
			if (this.owner.getPower(defaultmod.powers.Mana.POWER_ID).amount >= this.amount) {
				AbstractDungeon.actionManager.addToBottom(
						new ApplyPowerAction(owner, owner, new Mana(owner, owner, -this.amount), -this.amount));

				AbstractDungeon.actionManager.addToBottom(
						new ApplyPowerAction(owner, owner, new PoisonPower(owner, owner, this.amount), this.amount));


				for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
					if ((!monster.isDead) && (!monster.isDying)) {
						AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, owner,
								new PoisonPower(monster, owner, this.amount), this.amount));

						AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, owner,
								new DecayPower(monster, owner, this.amount), this.amount));
					}
				}
			}else {
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner,
						new Mana(owner, owner, -this.owner.getPower(defaultmod.powers.Mana.POWER_ID).amount),
						-this.owner.getPower(defaultmod.powers.Mana.POWER_ID).amount));

				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner,
						new PoisonPower(owner, owner, this.owner.getPower(defaultmod.powers.Mana.POWER_ID).amount),
						this.owner.getPower(defaultmod.powers.Mana.POWER_ID).amount));

				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner,
						new DecayPower(owner, owner, this.owner.getPower(defaultmod.powers.Mana.POWER_ID).amount),
						this.owner.getPower(defaultmod.powers.Mana.POWER_ID).amount));

				for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
					if ((!monster.isDead) && (!monster.isDying)) {
						AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, owner,
								new PoisonPower(monster, owner,
										this.owner.getPower(defaultmod.powers.Mana.POWER_ID).amount),
								this.owner.getPower(defaultmod.powers.Mana.POWER_ID).amount));

						AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, owner,
								new DecayPower(monster, owner, this.owner.getPower(defaultmod.powers.Mana.POWER_ID).amount),
								this.owner.getPower(defaultmod.powers.Mana.POWER_ID).amount));

					}
				}
			}
		} 

	}

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
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[3];
	}

}
