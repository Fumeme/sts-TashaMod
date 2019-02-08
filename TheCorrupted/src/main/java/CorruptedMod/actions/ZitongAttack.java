package CorruptedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import CorruptedMod.powers.Mana;
import CorruptedMod.waifus.zitong.*;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;

public class ZitongAttack extends AbstractGameAction {
    private AbstractFriendlyMonster owner;

    public ZitongAttack (AbstractFriendlyMonster owner) {
        this.duration = 0.8F;
        this.owner = owner;
    }

    @Override
    public void update() {
        if ((this.duration == 0.8F) && (AbstractDungeon.player != null)) {
            int upgradeCount = 0;
            if (this.owner.hasPower(Mana.POWER_ID) && this.owner.getPower(Mana.POWER_ID).amount != 0) {
                upgradeCount = this.owner.getPower(Mana.POWER_ID).amount;
            }
            int attackDamage = ZitongStats.ZitongAttackDamage + upgradeCount;
        	System.out.println(this.owner + " is attacking");   
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                DamageInfo info = new DamageInfo(this.owner, attackDamage, DamageInfo.DamageType.NORMAL);
                info.applyPowers(this.owner, mo);
                AbstractDungeon.actionManager.addToBottom(new DamageAction(mo, info));
            }
            this.isDone = true;
        }
    }
}