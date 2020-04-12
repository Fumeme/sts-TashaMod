package MagicalMod.cards;

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

import MagicalMod.MagicalBase;
import MagicalMod.actions.ManaBlightTriggerAction;
import MagicalMod.patches.AbstractCardEnum;
import MagicalMod.powers.Decay;
import MagicalMod.powers.Mana;

public class SpecializedShotLore extends AbstractCorrCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     * 
     * Strike Deal 7(9) damage.
     */

    // TEXT DECLARATION

    public static final String ID = MagicalBase.makeID("SpecShot");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = MagicalBase.makePath(MagicalBase.DEFAULT_COMMON_ATTACK);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/

    
    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;

    private static final int COST = 0;
    private static final int DAMAGE = 2;
    private static final int UPGRADE_PLUS_DMG = 1;
    private int AMOUNT = 2;
    
    // /STAT DECLARATION/

    public SpecializedShotLore() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.baseMagicNumber = this.magicNumber = AMOUNT;
        tags.add(MagicalBase.Ammo);
        tags.add(MagicalBase.Lore);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager
                .addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m,
                        new DamageInfo(p, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.FIRE));
        
        
        if(magic((short) 3)) {AbstractDungeon.actionManager.addToBottom(new ManaBlightTriggerAction(m, p, 1,1));
        	
        	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p,
                    new PoisonPower(m, p, this.magicNumber), this.magicNumber));
        	
        	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p,
                    new Decay(m, p, 1), 1));
        	
        	if(magic((short) 5)) {AbstractDungeon.actionManager.addToBottom(new ManaBlightTriggerAction(m, p, 1,1));
        		
        		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p,
                        new PoisonPower(m, p, this.magicNumber), this.magicNumber));

        		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p,
                        new WeakPower(m, this.magicNumber-1, false), this.magicNumber));
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
        return new SpecializedShotLore();
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