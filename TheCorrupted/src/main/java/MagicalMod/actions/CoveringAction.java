package MagicalMod.actions;

import MagicalMod.MagicalBase;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.BetterDiscardPileToHandAction;
import com.megacrit.cardcrawl.actions.defect.DiscardPileToHandAction;
import com.megacrit.cardcrawl.actions.unique.RandomCardFromDiscardPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;


public class CoveringAction extends AbstractGameAction {
    private AbstractCard card;
    ArrayList<AbstractCard> Ammo = new ArrayList<>();
    private AbstractPlayer p = AbstractDungeon.player;

    public CoveringAction(int amount) {
        this.amount = amount;

    }
    public CoveringAction() {
        this.amount = 1;

    }

    public void update() {

        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c.hasTag(MagicalBase.Ammo)) {

                this.Ammo.add(c);
            }
        }
        for (AbstractCard c : this.Ammo) {
            this.p.hand.addToTop(c);
        }

        AbstractCard thecard = p.hand.getTopCard();
        this.p.hand.addToHand(thecard);
        this.p.discardPile.removeCard(thecard);
        thecard.applyPowers();


        this.isDone = true;
    }

}