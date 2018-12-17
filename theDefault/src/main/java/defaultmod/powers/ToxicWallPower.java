package defaultmod.powers;

import com.megacrit.cardcrawl.localization.PowerStrings;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;

import defaultmod.DefaultMod;

//Gain 1 dex for the turn for each card played.

public class ToxicWallPower extends AbstractPower {
    public AbstractCreature source;

    public static final String POWER_ID = defaultmod.DefaultMod.makeID("ToxicWallPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DefaultMod.makePath(DefaultMod.COMMON_POWER);

    public ToxicWallPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;

    }

    // On use card, apply (amount) of dexterity. (Go to the actual power card for the ammount.)
    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != this.owner) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(info.owner, AbstractDungeon.player,
                    new PoisonPower(info.owner, AbstractDungeon.player, this.amount), this.amount));
            
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    new DecayPower(AbstractDungeon.player, AbstractDungeon.player, 1), 1));
        }

        return damageAmount;
    }
    /*    */   @Override
    /*    */   public void atStartOfTurn()
    /*    */   {
    /* 60 */     AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, "Flame Barrier"));
    /*    */   }
    /*    */   
    /*    */ @Override
    /*    */   public void updateDescription()
    /*    */   {
    /* 66 */     this.description = (DESCRIPTIONS[0]);
    /*    */   }
    /*    */ }