package CorruptedMod.cards.Decay;

import CorruptedMod.CorruptedBase;
import CorruptedMod.patches.AbstractCardEnum;
import CorruptedMod.powers.DecayPurge;
import DiamondMod.powers.DecayPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PurgingBlade extends CustomCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Strike Deal 7(9) damage.
     */

    // TEXT DECLARATION

    public static final String ID = CorruptedBase.makeID("PurgingBlade");
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
    private static final int DAMAGE = 8;
    private static final int MAGIC = 3;

    // /STAT DECLARATION/

    public PurgingBlade() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = MAGIC;
        tags.add(CardTags.STRIKE);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
 
        
        
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DecayPurge(p, p, this.magicNumber), this.magicNumber));
        
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
                new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL));


    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new PurgingBlade();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
this.upgradeMagicNumber(2);
            this.initializeDescription();
        }
    }
}