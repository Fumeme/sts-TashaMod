/*    */ package defaultmod.powers;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
/*    */ import com.megacrit.cardcrawl.actions.GameActionManager;
/*    */ import com.megacrit.cardcrawl.actions.unique.PoisonLoseHpAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
/*    */ import com.megacrit.cardcrawl.core.AbstractCreature;
/*    */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*    */ import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
/*    */ import com.megacrit.cardcrawl.localization.PowerStrings;
/*    */ import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
/*    */ import com.megacrit.cardcrawl.rooms.AbstractRoom;
/*    */ import com.megacrit.cardcrawl.unlock.UnlockTracker;
/*    */ import com.megacrit.cardcrawl.actions.common.DamageAction;
/*    */ public class DecayPower extends AbstractPower
/*    */ {
    public static final String POWER_ID = defaultmod.DefaultMod.makeID("DecayPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
/*    */   private AbstractCreature source;
/*    */   
/*    */   public DecayPower(AbstractCreature owner, AbstractCreature source, int DecayAmt)
/*    */   {
/* 23 */     this.name = NAME;
/* 25 */     this.owner = owner;
/* 26 */     this.source = source;
/* 27 */     this.amount = DecayAmt;
this.ID = POWER_ID;
this.updateDescription();
/* 34 */     this.loadRegion("poison");
/* 35 */     this.type = AbstractPower.PowerType.DEBUFF;
/*    */     
/* 37 */     this.isTurnBased = true;
/*    */   }
/*    */   
/*    */   public void playApplyPowerSfx()
/*    */   {
/* 42 */     CardCrawlGame.sound.play("POWER_POISON", 0.05F);
/*    */   }
/*    */   
/*    */   public void updateDescription()
/*    */   {
/* 47 */     if ((this.owner == null) || (this.owner.isPlayer)) {
/* 48 */       this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
/*    */     } else {
/* 50 */       this.description = (DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[1]);
/*    */     }
/*    */   }
/*    */   
/*    */   public void stackPower(int stackAmount)
/*    */   {
/* 56 */     super.stackPower(stackAmount);
/*    */     
/* 58 */   
/* 59 */      
/*    */     
/*    */   }
/*    */   
/*    */   public void atEndOfTurn()
/*    */   {
/* 65 */     if (AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
/*    */     {
/* 67 */       if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
/* 68 */         flashWithoutSound();
/* 69 */         AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(owner,
        new DamageInfo(owner, this.amount),
        AbstractGameAction.AttackEffect.POISON));
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\Steam\steamapps\common\SlayTheSpire\desktop-1.0.jar!\com\megacrit\cardcrawl\powers\PoisonPower.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */