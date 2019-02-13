package CorruptedMod.cards;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import CorruptedMod.CorruptedBase;
import CorruptedMod.patches.AbstractCardEnum;
import CorruptedMod.powers.DecayPower;
import CorruptedMod.powers.Mana;
import CorruptedMod.powers.ManaBlightPower;
import basemod.abstracts.CustomCard;

public class SpecializedShot extends CustomCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     * 
     * Strike Deal 7(9) damage.
     */

    // TEXT DECLARATION

    public static final String ID = CorruptedMod.CorruptedBase.makeID("SpecShot");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = CorruptedBase.makePath(CorruptedBase.DEFAULT_COMMON_ATTACK);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private int magictimes = 0;

    // /TEXT DECLARATION/

    
    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;

    private static final int COST = 0;
    private static final int DAMAGE = 2;
    private static final int UPGRADE_PLUS_DMG = 1;
    private int AMOUNT = 2;
    
    // /STAT DECLARATION/

    public SpecializedShot() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.baseMagicNumber = this.magicNumber = AMOUNT;
        tags.add(CorruptedBase.Ammo);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager
                .addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m,
                        new DamageInfo(p, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.FIRE));
        
        
        if(magic((short) 3)) { AbstractDungeon.player.getPower(Mana.POWER_ID).flash();
        	this.magictimes++;
        	
        	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p,
                    new PoisonPower(m, p, this.magicNumber), this.magicNumber));
        	
        	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p,
                    new DecayPower(m, p, 1), 1));
        	
        	if(magic((short) 5)) {AbstractDungeon.player.getPower(Mana.POWER_ID).flash();
        		this.magictimes++;
        		
        		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p,
                        new PoisonPower(m, p, this.magicNumber), this.magicNumber));

        		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p,
                        new WeakPower(m, this.magicNumber-1, false), this.magicNumber));
        	}
        }
        
		if(m.hasPower(ManaBlightPower.POWER_ID) && m.getPower(ManaBlightPower.POWER_ID).amount>0) {
	        if (AbstractDungeon.player.getPower(Mana.POWER_ID) instanceof TwoAmountPower) {
	            ((TwoAmountPower)AbstractDungeon.player.getPower(Mana.POWER_ID)).amount2 = this.magictimes;;
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
        return new SpecializedShot();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_DMG);
            this.upgradeMagicNumber(UPGRADE_PLUS_DMG);
            this.initializeDescription();
        }
    }
}