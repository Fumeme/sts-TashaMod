package MagicalMod.cards.Mana;

import MagicalMod.cards.AbstractCorrCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import MagicalMod.MagicalBase;
import MagicalMod.patches.AbstractCardEnum;
import MagicalMod.powers.Mana;

public class ManaBlade extends AbstractCorrCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     * 
     * Strike Deal 7(9) damage.
     */

    // TEXT DECLARATION

    public static final String ID = MagicalBase.makeID("ManaBlade");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = MagicalBase.makePath(MagicalBase.DEFAULT_COMMON_ATTACK);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/

    
    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 11;
    int MIN = 4;

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
        int CurrMin = baseMagicNumber;
        int CurrMax = baseDamage;
        super.applyPowers();

        baseDamage = CurrMin;
        super.applyPowers(); // takes baseDamage and applies things like Strength or Pen Nib to set damage

        magicNumber = damage; // magic number holds the first condition's modified damage, so !M! will work
        isMagicNumberModified = magicNumber != baseMagicNumber;

        // repeat so damage holds the second condition's damage
        baseDamage = CurrMax;
       // super.applyPowers();
         ManaCheck();
        super.applyPowers();
        ManaCheck();

        this.initializeDescription();


    }
    @Override
    public void calculateCardDamage(final AbstractMonster mo) {

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
        super.calculateCardDamage(mo); // takes baseDamage and applies things like Strength or Pen Nib to set damage


        ManaCheck();
        super.calculateCardDamage(mo);
        ManaCheck();

        this.initializeDescription();

    }
    public void ManaCheck(){
        AbstractPlayer p = AbstractDungeon.player;

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

    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
            this.upgradeDamage(4);
            this.initializeDescription();
        }
    }
}