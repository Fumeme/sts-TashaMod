package CorruptedMod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import CorruptedMod.CorruptedBase;
import CorruptedMod.patches.AbstractCardEnum;
import DiamondMod.powers.DecayPower;
import DiamondMod.powers.Mana;
import basemod.abstracts.CustomCard;

public class InnerHeaven extends AbstractCorrCard {

	/*
	 * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
	 * 
	 * Hold Place Gain 1(2) Keywords(s).
	 */

	// TEXT DECLARATION

	public static final String ID = CorruptedMod.CorruptedBase.makeID("InnerHeaven");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = CorruptedBase.makePath(CorruptedBase.DEFAULT_COMMON_SKILL);

	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

	// /TEXT DECLARATION/

	// STAT DECLARATION

	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;

	private static final int COST = 1;
	private static final int MAGIC = 10;
	private static final int UPGRADE_MAGIC = 10;
private  int Manathres = 5;
private  int Energythres = 10;
	// /STAT DECLARATION/

	public InnerHeaven() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.magicNumber = this.baseMagicNumber = MAGIC;
	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (p.hasPower(DecayPower.POWER_ID)) {

			if (p.getPower(DecayPower.POWER_ID).amount >= this.magicNumber) {

				AbstractDungeon.actionManager.addToBottom(
						new ApplyPowerAction(p, p, new DecayPower(p, p, -this.magicNumber), -this.magicNumber));

				AbstractDungeon.actionManager.addToBottom(
						new ApplyPowerAction(p, p, new Mana(p, p, this.magicNumber / this.Manathres), this.magicNumber / this.Manathres));
				
				AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.magicNumber/this.Energythres));

			}else {
				
				AbstractDungeon.actionManager.addToBottom(
						new ApplyPowerAction(p, p, new DecayPower(p, p, -p.getPower(DecayPower.POWER_ID).amount), -p.getPower(DecayPower.POWER_ID).amount));

				AbstractDungeon.actionManager.addToBottom(
						new ApplyPowerAction(p, p, new Mana(p, p, p.getPower(DecayPower.POWER_ID).amount / this.Manathres), p.getPower(DecayPower.POWER_ID).amount / this.Manathres));
				
				AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(p.getPower(DecayPower.POWER_ID).amount/this.Energythres));
				
			}
		}
	}

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() {
		return new InnerHeaven();
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			--this.Manathres;
			--this.Energythres;
			this.upgradeMagicNumber(UPGRADE_MAGIC);
            this.rawDescription = UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
}