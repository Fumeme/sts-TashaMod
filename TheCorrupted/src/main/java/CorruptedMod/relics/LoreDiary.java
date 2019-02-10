package CorruptedMod.relics;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.relics.BetterOnSmithRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.TreasureRoom;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import CorruptedMod.CorruptedBase;
import CorruptedMod.patches.LoreDiaryReward;
import basemod.abstracts.CustomRelic;

public class LoreDiary extends CustomRelic implements BetterOnSmithRelic{
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     * 
     * At the start of each combat, gain 1 strenght (i.e. Varja)
     */
    
    // ID, images, text.
    public static final String ID = CorruptedMod.CorruptedBase.makeID("LoreDiary");
    public static final String IMG = CorruptedBase.makePath(CorruptedBase.PLACEHOLDER_RELIC);
    public static final String OUTLINE = CorruptedBase.makePath(CorruptedBase.PLACEHOLDER_RELIC_OUTLINE_2);

    private static ArrayList<AbstractCard> cardsToShow = new ArrayList<AbstractCard>();
    private AbstractPlayer p = AbstractDungeon.player;
    
    public LoreDiary() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SPECIAL, LandingSound.FLAT);
    }

@Override
	/*    */ public void justEnteredRoom(AbstractRoom room)
	/*    */ {
		/* 30 */ if ((room instanceof TreasureRoom)) {
			/* 31 */ flash();
			/* 32 */ this.pulse = true;
			/*    */ } else {
			/* 34 */ this.pulse = false;
			/*    */ }
		/*    */ }
    
@Override
	/*    */ public void onChestOpen(boolean bossChest)
	/*    */ {
		/* 44 */ if (!bossChest) { /*    */

			flash();
			AbstractDungeon.getCurrRoom().rewards.add(new LoreDiaryReward());
		
		}
}
public void betterOnSmith(AbstractCard c)
{
	
	ArrayList<AbstractCard> nonlore = new ArrayList<AbstractCard>();
	 
	 // Create an array list called "upgradable nonlore cards"
    for ( AbstractCard check : AbstractDungeon.player.masterDeck.getUpgradableCards().group){
        // if c. has tag lore, add it to the array
    	if (!c.hasTag(CorruptedBase.Lore)){
    		
    	nonlore.add(check);
    	}
    }
    // get a random card from the array and upgrade it
    
    
	
	
//	System.out.println(p.masterDeck.getUpgradableCards());
//  if ((c.hasTag(CorruptedBase.Lore)))
// {
 AbstractCard card = p.masterDeck.getUpgradableCards().getRandomCard(true);
	/*  while(card.hasTag(CorruptedBase.Lore)) {
		  
		  System.out.println("checking for if the card isnt a lore card" );
		  card = p.masterDeck.getUpgradableCards().getRandomCard(true);
	  }
*/ card = nonlore.get(AbstractDungeon.cardRng.random(nonlore.size() -1));
	  
    card.upgrade();
    cardsToShow.add(card);
  }


public static void cardEffects()
{
  for (AbstractCard c : cardsToShow)
  {
    float x = MathUtils.random(0.4F, 0.9F) * Settings.WIDTH;
    float y = MathUtils.random(0.6F, 0.8F) * Settings.HEIGHT;
    AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), x, y));
    AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(x, y));
  }
  cardsToShow.clear();
}

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    // Which relic to return on making a copy of this relic.
    @Override
    public AbstractRelic makeCopy() {
        return new LoreDiary();
    }
}
