package CorruptedMod.cards;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;

import CorruptedMod.CorruptedBase;
import CorruptedMod.patches.AbstractCardEnum;
import DiamondMod.powers.DecayPower;

public class SealTheWounds extends AbstractCorrCard {

	/*
	 * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
	 * 
	 * Hold Place Gain 1(2) Keywords(s).
	 */

	// TEXT DECLARATION

	public static final String ID = CorruptedMod.CorruptedBase.makeID("SealTheWounds");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = CorruptedBase.makePath(CorruptedBase.DEFAULT_COMMON_SKILL);

	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

	// /TEXT DECLARATION/

	// STAT DECLARATION

	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;

	private static final int COST = 2;
	public SealTheWounds() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.exhaust = true;
	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {

		if (p.hasPower(DecayPower.POWER_ID)) {

			AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, DecayPower.POWER_ID));
			
			
			}
		}
	

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() {
		return new SealTheWounds();
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.updateCost(-1);
			this.initializeDescription();
		}
	}
}