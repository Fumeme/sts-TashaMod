package defaultmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import basemod.abstracts.CustomCard;

import defaultmod.DefaultMod;
import defaultmod.patches.AbstractCardEnum;
import defaultmod.powers.CommonPower;
import defaultmod.powers.HybridRoundsPower;
import defaultmod.powers.Mana;

public class HybridRounds extends CustomCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     * 
     * Hold Place Gain 1(2) Keywords(s).
     */


    // TEXT DECLARATION 

    public static final String ID = defaultmod.DefaultMod.makeID("HybridRounds");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.DEFAULT_UNCOMMON_POWER);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION 	

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;

    private static final int COST = 0;
    private static final int DAMAGE = 2;
    private static final int MAGIC = 2;

    // /STAT DECLARATION/


    public HybridRounds() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = MAGIC;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    	if(magic((short) 2)) {
    		
    		this.baseDamage += 2;
    		
    		if(magic((short) 4)) {
        		
        		this.baseDamage += 2;
        		
        		if(magic((short) 6)) {
            		
        			this.baseDamage += 2;
            	}
            	
        	}
        	
    	}
    	
    	
        AbstractDungeon.actionManager
        .addToTop(new com.megacrit.cardcrawl.actions.common.DamageAction(m,
                new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        

        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new HybridRoundsPower(p, p, this.magicNumber), this.magicNumber));
        
    	if(magic((short) 2)) {
    		
    		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new HybridRoundsPower(p, p, 2), 2));
    		
    		if(magic((short) 4)) {
        		
    			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new HybridRoundsPower(p, p, 2), 2));
        		
        		if(magic((short) 6)) {
            		
        			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new HybridRoundsPower(p, p, 2), 2));
            	}
            	
        	}
        	
    	}
    	
    }

    boolean magic (short min) {
    	if (AbstractDungeon.player.hasPower(Mana.POWER_ID)) {

    		 return AbstractDungeon.player.getPower(Mana.POWER_ID).amount >= min;
    		
    	}
    	return false;
    }
    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new HybridRounds();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
            this.initializeDescription();
            
        }
    }
}