package defaultmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;

import defaultmod.DefaultMod;
import defaultmod.patches.AbstractCardEnum;
import defaultmod.powers.Mana;


public class Focusfire extends CustomCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     * 
     * Strike Deal 7(9) damage.
     */

    // TEXT DECLARATION

    public static final String ID = defaultmod.DefaultMod.makeID("Focusfire");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.DEFAULT_COMMON_ATTACK);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/

    
    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
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
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    	this.isMultiDamage = true;

  		 AbstractDungeon.actionManager
         .addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m,
                 new DamageInfo(p, this.damage, this.damageTypeForTurn),
                 AbstractGameAction.AttackEffect.FIRE));
    		 
    		 AbstractDungeon.actionManager
             .addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m,
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
        	bonus += 2;
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_DMG);
            this.initializeDescription();
        }
    }
}