package defaultmod.waifus.zitong;


import com.badlogic.gdx.graphics.Texture;
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
    private static Texture intentOne = TextureLoader.getTexture("images/Waifus/Zitong/attack_intent_1.png");
    private static Texture intentTwo = TextureLoader.getTexture("images/Waifus/Zitong/attack_intent_2.png");
    private static Texture intentThree = TextureLoader.getTexture("images/Waifus/Zitong/attack_intent_3.png");
    private static Texture intentFour = TextureLoader.getTexture("images/Waifus/Zitong/attack_intent_4.png");
    private static Texture intentFive = TextureLoader.getTexture("imagesWaifus/Zitong/attack_intent_5.png");
    private static Texture intentSix = TextureLoader.getTexture("images/Waifus/Zitong/attack_intent_6.png");
    private static Texture intentSeven = TextureLoader.getTexture("images/Waifus/Zitong/attack_intent_7.png");
    private static Texture[] intentImgs = {intentOne, intentTwo, intentThree, intentFour, intentFive, intentSix, intentSeven};
    
    private AbstractMonster target;

    public ZitongBase(float offSetX, boolean slotOne) {
        super(NAME, ID, ZitongStats.ZitongHP,
                -2.0F, 10.0F, 230.0F, 240.0F, "images/Waifus/Zitong/Zitong.png", offSetX, 0, intentImgs, slotOne);
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
    }

    public void addMoves() {

        
        int rng = ThreadLocalRandom.current().nextInt(1, 10 + 1);
        switch(rng) {
        
        case 7: 
        case 8:
        case 9: AbstractDungeon.actionManager.addToBottom(new ZitongHeal(this));
        	break;
        default: AbstractDungeon.actionManager.addToBottom(new ZitongAttack(this));
        break;
        }
        
        

        this.moves.addMove(new MinionMove("Attack", this, new Texture("images/Waifus/Zitong/Zitong.png"), "Deal 3 plus her mana count damage to all enemies", () -> {
            target = AbstractDungeon.getRandomMonster();
            DamageInfo info = new DamageInfo(this,5,DamageInfo.DamageType.NORMAL);
            info.applyPowers(this, target); // <--- This lets powers effect minions attacks
            AbstractDungeon.actionManager.addToBottom(new ZitongAttack(this));
        }));
        this.moves.addMove(new MinionMove("Heal", this, new Texture("images/Waifus/Zitong/Zitong.png"),"Gain heal 1 hp to her and all allies", () -> {
            AbstractDungeon.actionManager.addToBottom(new ZitongHeal(this));
        }));

    }

    //Not needed unless doing some kind of random move like normal Monsters
    @Override
    protected void getMove(int i) {
    }
}