package defaultmod.relics;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.abstracts.CustomRelic;
import defaultmod.DefaultMod;

public class OddShapedKey extends CustomRelic {
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     * 
     * At the start of each combat, gain 1 strenght (i.e. Varja)
     */
    
    // ID, images, text.
    public static final String ID = defaultmod.DefaultMod.makeID("OddShapedKey");
    public static final String IMG = DefaultMod.makePath(DefaultMod.PLACEHOLDER_RELIC_2);
    public static final String OUTLINE = DefaultMod.makePath(DefaultMod.PLACEHOLDER_RELIC_OUTLINE_2);

    public OddShapedKey() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.COMMON, LandingSound.MAGICAL);
    }


    
    /*    */   public void justEnteredRoom(AbstractRoom room)
    /*    */   {
    /* 30 */     if ((room instanceof com.megacrit.cardcrawl.rooms.TreasureRoom)) {
    /* 31 */       flash();
    /* 32 */       this.pulse = true;
    /*    */     } else {
    /* 34 */       this.pulse = false;
    /*    */     }
    /*    */   }
    
    @Override
    public void atBattleStartPreDraw() {
        flash();
        
    ArrayList<AbstractCard> colorlessCards = CardLibrary.getCardList(CardLibrary.LibraryType.COLORLESS);
        
        ArrayList<AbstractCard> statusCards = new ArrayList<>();
        
        for(int i = 0; i<this.counter; i++) {
        	
        for (AbstractCard c : colorlessCards)
        {
            if (c.type == CardType.STATUS)
                statusCards.add(c);
        }
        }
    }
    
    /*    */   public void setCounter(int counter)
    /*    */   {
    /* 20 */     this.counter = counter;

    /*    */   }
    
    /*    */   public void onChestOpen(boolean bossChest)
    /*    */   {
    /* 44 */     if (!bossChest) {  /*    */  
    	
    			flash();
    			this.counter ++;
    	
    }
    /*    */   }
    

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    // Which relic to return on making a copy of this relic.
    @Override
    public AbstractRelic makeCopy() {
        return new OddShapedKey();
    }
}
