package CorruptedMod.cards.Mana;

import CorruptedMod.cards.AbstractCorrCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import CorruptedMod.CorruptedBase;
import CorruptedMod.patches.AbstractCardEnum;
import CorruptedMod.powers.Mana;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class ManaBlade extends AbstractCorrCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     * 
     * Strike Deal 7(9) damage.
     */

    // TEXT DECLARATION

    public static final String ID = CorruptedMod.CorruptedBase.makeID("ManaBlade");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = CorruptedBase.makePath(CorruptedBase.DEFAULT_COMMON_ATTACK);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/

    
    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 10;
    int MIN = 3;

    // /STAT DECLARATION/

    public ManaBlade() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.baseMagicNumber = MIN;
        this.SecondMagicNumber = 0;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    	
    	/* 
    	 * this.damage is max damage done, magicNumber is Min damage, both affected by dmg modifiers
    	 *
    	 * 
    	 */

    	
        AbstractDungeon.actionManager
                .addToBottom(new DamageAction(m,
                        new DamageInfo(p, this.SecondMagicNumber, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.SHIELD));
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new ManaBlade();
    }


    @Override
    public void applyPowers() {
        AbstractPlayer p = AbstractDungeon.player;
        int CurrMin = baseMagicNumber;
        int CurrMax = baseDamage;
        super.applyPowers();

        baseDamage = CurrMin;
        super.applyPowers(); // takes baseDamage and applies things like Strength or Pen Nib to set damage

        magicNumber = damage; // magic number holds the first condition's modified damage, so !M! will work
        isMagicNumberModified = magicNumber != baseMagicNumber;

        // repeat so damage holds the second condition's damage
        baseDamage = CurrMax;
        super.applyPowers();

        if (p.hasPower(Mana.POWER_ID)) {
            if (p.getPower(Mana.POWER_ID).amount >= this.damage) {

                this.SecondMagicNumber = this.damage;
            } else if (p.getPower(Mana.POWER_ID).amount <= this.magicNumber) {

                this.SecondMagicNumber = this.magicNumber;
            } else {

                this.SecondMagicNumber = p.getPower(Mana.POWER_ID).amount;
            }
        }else {this.SecondMagicNumber = this.magicNumber;}
        isSecondMagicNumberModified = true;
        super.applyPowers();

        this.initializeDescription();


    }
    @Override
    public void calculateCardDamage(final AbstractMonster mo) {
        AbstractPlayer p = AbstractDungeon.player;

        int CurrMin = baseMagicNumber;
        int CurrMax = baseDamage;
        super.applyPowers();

        baseDamage = CurrMin;
        super.calculateCardDamage(mo); // takes baseDamage and applies things like Strength or Pen Nib to set damage

        magicNumber = damage; // magic number holds the first condition's modified damage, so !M! will work
        isMagicNumberModified = magicNumber != baseMagicNumber;

        // repeat so damage holds the second condition's damage
        baseDamage = CurrMax;
        super.applyPowers();

        if (p.hasPower(Mana.POWER_ID)) {
            if (p.getPower(Mana.POWER_ID).amount >= this.damage) {

                this.SecondMagicNumber = this.damage;
            } else if (p.getPower(Mana.POWER_ID).amount <= this.magicNumber) {

                this.SecondMagicNumber = this.magicNumber;
            } else {

                this.SecondMagicNumber = p.getPower(Mana.POWER_ID).amount;
            }
        }else {this.SecondMagicNumber = this.magicNumber;}
        isSecondMagicNumberModified = true;

        super.calculateCardDamage(mo);

        this.initializeDescription();

    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.upgradeDamage(3);
            this.initializeDescription();
        }
    }
}