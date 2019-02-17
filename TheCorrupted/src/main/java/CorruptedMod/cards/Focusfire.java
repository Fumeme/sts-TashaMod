package CorruptedMod.cards;

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
import CorruptedMod.actions.ManaBlightTriggerAction;
import CorruptedMod.patches.AbstractCardEnum;
import CorruptedMod.powers.Mana;
import basemod.abstracts.CustomCard;


public class Focusfire extends CustomCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     * 
     * Strike Deal 7(9) damage.
     */

    // TEXT DECLARATION

    public static final String ID = CorruptedMod.CorruptedBase.makeID("Focusfire");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = CorruptedBase.makePath(CorruptedBase.DEFAULT_COMMON_ATTACK);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EFFECTS = cardStrings.EXTENDED_DESCRIPTION;

    // /TEXT DECLARATION/

    
    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;

    private static final int COST = 0;
    private static int DAMAGE = 2;
    private static final int UPGRADE_PLUS_DMG = 1;
    private static int bonus = 1;

    // /STAT DECLARATION/

    public Focusfire() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = bonus;
        
        this.rawDescription = DESCRIPTION + Focusfire.EFFECTS[0];
        
        this.initializeDescription();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    	if(magic((short)3)) {
    		AbstractDungeon.actionManager.addToBottom(new ManaBlightTriggerAction(m, p, 1,1));
    	}
    	
  		 AbstractDungeon.actionManager
         .addToBottom(new DamageAction(m,
                 new DamageInfo(p, this.damage, this.damageTypeForTurn),
                 AbstractGameAction.AttackEffect.FIRE));
    		 
    		 AbstractDungeon.actionManager
             .addToBottom(new DamageAction(m,
                     new DamageInfo(p, this.damage, this.damageTypeForTurn),
                     AbstractGameAction.AttackEffect.FIRE));
    		 

    }
    
    boolean magic (short min) {
    	if (AbstractDungeon.player.hasPower(Mana.POWER_ID)) {

    		 return AbstractDungeon.player.getPower(Mana.POWER_ID).amount >= min;
    		
    	}
    	return false;
    }
    @Override
    public void applyPowers(){
    	
    	int tmp = this.baseDamage;
    	if (magic((short) 3)){
    	    this.baseDamage += bonus;
    	}
    	super.applyPowers();
    	this.baseDamage = tmp;
    	this.isDamageModified = this.baseDamage != this.damage;
        
    	if(magic((short) 3)) {
    		
    		this.rawDescription = DESCRIPTION + Focusfire.EFFECTS[1];
    	}else {
    		
            this.rawDescription = DESCRIPTION + Focusfire.EFFECTS[0];
    	}
    	
        this.initializeDescription();
    }
    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int tmp = this.baseDamage;
        if (magic((short) 3)){
            this.baseDamage += bonus;
        }
        super.calculateCardDamage(mo);
        this.baseDamage = tmp;
        this.isDamageModified = this.baseDamage != this.damage;
}
    
    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new Focusfire();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
        	this.upgradeMagicNumber(2);
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_DMG);
            this.rawDescription = DESCRIPTION + Focusfire.EFFECTS[0];
            this.initializeDescription();
        }
    }
}