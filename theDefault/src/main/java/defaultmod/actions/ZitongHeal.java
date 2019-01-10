package defaultmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import kobting.friendlyminions.characters.AbstractPlayerWithMinions;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import defaultmod.powers.Mana;
import defaultmod.waifus.zitong.*;

public class ZitongHeal extends AbstractGameAction {
    private AbstractFriendlyMonster owner;
    private AbstractPlayer p;

    public ZitongHeal (AbstractFriendlyMonster owner) {
        this.duration = 0.8F;
        this.owner = owner;
    }

    @Override
    public void update() {
        if ((this.duration == 0.8F) && (AbstractDungeon.player != null)) {
            int upgradeCount = 0;
            if (this.owner.hasPower(Mana.POWER_ID) && this.owner.getPower(Mana.POWER_ID).amount >= 0) {
                upgradeCount = this.owner.getPower(Mana.POWER_ID).amount;
            }

            AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, ZitongStats.ZitongHealAmount + upgradeCount));
            
                if (p instanceof AbstractPlayerWithMinions) {

                    AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) p;

                    if (player.minions.monsters.size() != 0) {

                        for (AbstractMonster mo : player.minions.monsters) {

                            AbstractDungeon.actionManager.addToBottom(new HealAction(mo, p, ZitongStats.ZitongHealAmount + upgradeCount));

                        

                    } 
            }
            
        }
    }
        this.isDone = true;
}
    }