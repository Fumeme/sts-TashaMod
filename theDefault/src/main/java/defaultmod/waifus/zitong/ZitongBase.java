package defaultmod.waifus.zitong;


import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import kobting.friendlyminions.monsters.MinionMove;
import kobting.friendlyminions.monsters.MinionMoveGroup;
import defaultmod.actions.ZitongAttack;
import defaultmod.actions.ZitongHeal;
import defaultmod.powers.Mana;
import defaultmod.tools.TextureLoader;
import defaultmod.waifus.AbstractCorrMinion;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;


public class ZitongBase extends AbstractCorrMinion {

	public static String NAME = "Zitong";
    public static String ID = "Zitong";
    private int upgradeCount;
    private boolean hasAttacked = false;
    private AbstractMonster target;
    private static Texture intentOne = TextureLoader.getTexture("Waifus/Zitong/attack_intent_1.png");
    private static Texture intentTwo = TextureLoader.getTexture("Waifus/Zitong/attack_intent_2.png");
    private static Texture intentThree = TextureLoader.getTexture("Waifus/Zitong/attack_intent_3.png");
    private static Texture intentFour = TextureLoader.getTexture("Waifus/Zitong/attack_intent_4.png");
    private static Texture intentFive = TextureLoader.getTexture("Waifus/Zitong/attack_intent_5.png");
    private static Texture intentSix = TextureLoader.getTexture("Waifus/Zitong/attack_intent_6.png");
    private static Texture intentSeven = TextureLoader.getTexture("Waifus/Zitong/attack_intent_7.png");
    private static Texture[] intentImgs = {intentOne, intentTwo, intentThree, intentFour, intentFive, intentSix, intentSeven};

    public ZitongBase(float offSetX, boolean slotOne) {
        super(NAME, ID, ZitongStats.ZitongHP,
                -2.0F, 10.0F, 230.0F, 240.0F, "Waifus/Zitong/Zitong.png", offSetX, 0, intentImgs, slotOne);
        addMoves();
    }

    @Override
    public void applyStartOfTurnPowers() {
        AbstractDungeon.actionManager.addToBottom(new LoseBlockAction(this, this, this.currentBlock));
        System.out.println(this.name + " " + this.currentHealth);
    }

    @Override
    public void applyEndOfTurnTriggers() {
        super.applyEndOfTurnTriggers();
        this.hasAttacked = false;
    }

    public void addMoves() {
        ArrayList<MinionMove> ZitongMoves = new ArrayList<>();
        if (this.hasPower(Mana.POWER_ID) && this.getPower(Mana.POWER_ID).amount != 0) {
            upgradeCount = this.getPower(Mana.POWER_ID).amount;
        }
        int AttackDamage = (ZitongStats.ZitongAttackDamage + upgradeCount);
       int HealAmount = (ZitongStats.ZitongHealAmount + upgradeCount);
        /**
        String attackDesc = String.format("Deal %d damage to all enemies.", AttackDamage);
        String chargeDesc = String.format("Heal all allies %d and 1 to self.", HealAmount);
      **/
        
        int rng = ThreadLocalRandom.current().nextInt(1, 10 + 1);
        switch(rng) {
        
        case 7: 
        case 8:
        case 9: AbstractDungeon.actionManager.addToBottom(new ZitongHeal(this));
        	break;
        default: AbstractDungeon.actionManager.addToBottom(new ZitongAttack(this));
        }
        
        /**
        lilyMoves.add(new MinionMove("rubyPic", this, TextureLoader.getTexture("summons/bubbles/lilybubble.png")
                , "", () -> this.setTakenTurn(false)));
        lilyMoves.add(new MinionMove("Attack", this, new Texture("summons/bubbles/atk_bubble.png")
                , attackDesc, () -> {
            AbstractDungeon.actionManager.addToBottom(new ZitongAttack(this));

        }));
        lilyMoves.add(new MinionMove("Heal", this, new Texture("summons/bubbles/charge_bubble.png")
                ,chargeDesc, () -> {
            AbstractDungeon.actionManager.addToBottom(new ZitongHeal(this));
        }));
        
        **/
        if (slotOne) {
            this.moves = new MinionMoveGroup(ZitongMoves, 350F * Settings.scale, -200F * Settings.scale);
        } else {
            this.moves = new MinionMoveGroup(ZitongMoves, 350F * Settings.scale, -300F * Settings.scale);
        }
    }

    //Not needed unless doing some kind of random move like normal Monsters
    @Override
    protected void getMove(int i) {
    }
}