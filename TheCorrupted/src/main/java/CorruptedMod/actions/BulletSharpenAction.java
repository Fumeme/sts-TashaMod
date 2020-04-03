package CorruptedMod.actions;

import CorruptedMod.CorruptedBase;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


public class BulletSharpenAction extends AbstractGameAction {
    private AbstractCard card;

    public BulletSharpenAction(int amount) {
        this.amount = amount;
    }



    public void update() {

        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.hasTag(CorruptedBase.Ammo)) {
                c.baseDamage += this.amount;
                c.applyPowers();
            }
        }

        this.isDone = true;
}

}