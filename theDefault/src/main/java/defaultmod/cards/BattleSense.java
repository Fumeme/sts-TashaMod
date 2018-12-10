package defaultmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
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
import defaultmod.powers.Mana;

public class BattleSense extends CustomCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     * 
     * Hold Place Gain 1(2) Keywords(s).
     */


    // TEXT DECLARATION 

    public static final String ID = defaultmod.DefaultMod.makeID("BattleSense");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.DEFAULT_UNCOMMON_POWER);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION 	

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;

    private static final int COST = 1;
    private static final int MAGIC = 1;

    // /STAT DECLARATION/


    public BattleSense() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = MAGIC;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    	com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.
    	addToBottom(new com.megacrit.cardcrawl.actions.common.
    			ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber));
    	
    	com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.
    	addToBottom(new com.megacrit.cardcrawl.actions.common.
    			ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
    	
    	if (this.upgraded) {
    		
        	com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.
        	addToBottom(new com.megacrit.cardcrawl.actions.common.
        			ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber));
    		
    	}
    }


    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new BattleSense();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.initializeDescription();
            
        }
    }
}