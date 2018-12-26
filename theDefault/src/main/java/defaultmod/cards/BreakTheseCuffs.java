package defaultmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import basemod.abstracts.CustomCard;

import defaultmod.DefaultMod;
import defaultmod.patches.AbstractCardEnum;
import defaultmod.powers.DecayPower;

public class BreakTheseCuffs extends CustomCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     * 
     * Strike Deal 7(9) damage.
     */

    // TEXT DECLARATION

    public static final String ID = defaultmod.DefaultMod.makeID("BreakTheseCuffs");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.DEFAULT_COMMON_ATTACK);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/

    
    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;

    private static final int COST = 1;
    private short DecayGain;


    // /STAT DECLARATION/

    public BreakTheseCuffs() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
 
    	if(p.hasPower(VulnerablePower.POWER_ID)) {  
    		this.DecayGain += p.getPower(VulnerablePower.POWER_ID).amount;
    		
    		AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, VulnerablePower.POWER_ID));
    	}
        
        
    	if(p.hasPower(WeakPower.POWER_ID)) {  
    		this.DecayGain += p.getPower(WeakPower.POWER_ID).amount;
    		
    		AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, WeakPower.POWER_ID));
    	}
        

    	if(p.hasPower(FrailPower.POWER_ID)) {  
    		this.DecayGain += p.getPower(FrailPower.POWER_ID).amount;
    		
    		AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, FrailPower.POWER_ID));
    	}
    	
    	
    	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new DecayPower(p, p, this.DecayGain), this.DecayGain));
    	
    	
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new BreakTheseCuffs();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.exhaust = false;
            this.initializeDescription();
        }
    }
}