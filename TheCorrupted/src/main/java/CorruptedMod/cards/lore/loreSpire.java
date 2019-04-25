package CorruptedMod.cards.lore;

import CorruptedMod.cards.AbstractCorrCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import CorruptedMod.CorruptedBase;
import basemod.abstracts.CustomCard;

public class loreSpire extends AbstractCorrCard {

	/*
	 * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
	 * 
	 * Strike Deal 7(9) damage.
	 */

	// TEXT DECLARATION

	public static final String ID = CorruptedMod.CorruptedBase.makeID("loreSpire");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = CorruptedBase.makePath(CorruptedBase.InfernalForm);

	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] LORE = cardStrings.EXTENDED_DESCRIPTION;

	// /TEXT DECLARATION/

	// STAT DECLARATION

	private static final CardRarity RARITY = CardRarity.SPECIAL;
	private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = AbstractCard.CardColor.COLORLESS;

	// /STAT DECLARATION/

	public loreSpire() {
		super(ID, NAME, IMG, -2, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		
        tags.add(CorruptedBase.Lore);
        
		this.damage = this.baseDamage = this.baseBlock = this.block = 5;
		this.isMultiDamage = true;
		
		this.rawDescription = DESCRIPTION + loreSpire.LORE[0];
	}

	/*    */ public void triggerWhenDrawn()
	/*    */ {
		applyPowers();
		/* 41 */ flash();
		/*    */ AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.block));

		AbstractDungeon.actionManager.addToBottom(
				new DamageAllEnemiesAction(AbstractDungeon.player, this.multiDamage, DamageType.THORNS, AbstractGameAction.AttackEffect.SHIELD));

	}

	// Actions the card should do.
	@Override
	/*    */ public void use(AbstractPlayer p, AbstractMonster m)
	/*    */ {
	}

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() {
		return new loreSpire();
	}
//OnCardDrawPower 
	@Override
	public void upgrade() {
		if(this.timesUpgraded <= 5) {
            this.timesUpgraded++;
			this.upgraded = true;
		this.name = (NAME + "+" + this.timesUpgraded);
		this.upgradeDamage(3);
		this.upgradeBlock(3);
		this.rawDescription = DESCRIPTION;
		for(int i = 0; i <= this.timesUpgraded; i++) {
		this.rawDescription += loreSpire.LORE[i];
		}
		this.initializeTitle();
		this.initializeDescription();
	}}

}