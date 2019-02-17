package CorruptedMod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.DamageAllButOneEnemyAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

import CorruptedMod.CorruptedBase;
import CorruptedMod.patches.AbstractCardEnum;
import CorruptedMod.powers.Mana;
import CorruptedMod.powers.ManaBlightPower;
import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;

public class EnergyCannon extends CustomCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     * 
     * Strike Deal 7(9) damage.
     */

    // TEXT DECLARATION

    public static final String ID = CorruptedMod.CorruptedBase.makeID("EnergyCannon");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = CorruptedBase.makePath(CorruptedBase.DEFAULT_COMMON_ATTACK);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/

    
    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;

    private static final int COST = 1;

    // /STAT DECLARATION/

    public EnergyCannon() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = 20;
        this.magicNumber = 5;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    	if(p.hasPower(Mana.POWER_ID) && p.getPower(Mana.POWER_ID).amount >= this.magicNumber) {
    	
        AbstractDungeon.actionManager
                .addToBottom(new DamageAction(m,
                        new DamageInfo(p, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.SMASH));
        
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new Mana(p, p, -this.magicNumber), -this.magicNumber));
        
        if(this.upgraded) {

        	 int count = 0;
        	 /* 43 */     for (AbstractMonster mon : AbstractDungeon.getMonsters().monsters) {
        	 /* 44 */       if ((!mon.isDeadOrEscaped())) {
        	 /* 45 */         count++;
        	 /*    */       }
        	 /*    */     }

        	int[] aDamage;
        	aDamage = new int[count];
        	
       	 /* 48 */     for (int i = 1; i < count; i++) {
       	 /* 49 */       //thing
       		 
       		 			aDamage[i] = 5;
       	 /*    */     }
        	
        	 AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
           AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new CleaveEffect(), 0.2F));  //fx
           

        	AbstractDungeon.actionManager
            .addToBottom(new DamageAllButOneEnemyAction(p,m , aDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        
    }}}

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new EnergyCannon();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
        	this.isMultiDamage = true;
            this.upgradeName();
            this.rawDescription = UPGRADE;
            this.upgradeDamage(15);
            this.upgradeMagicNumber(-1);
            this.initializeDescription();
        }
    }
}