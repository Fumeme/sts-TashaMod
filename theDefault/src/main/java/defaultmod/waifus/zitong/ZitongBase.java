package defaultmod.waifus.zitong;


import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
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



public class ZitongBase extends AbstractCorrMinion {

	public static String NAME = "Zitong";
    public static String ID = "Zitong";
    private int upgradeCount;
    private static Texture intentOne = TextureLoader.getTexture("defaultModResources/images/Waifus/Zitong/attack_intent_1.png");
    private static Texture intentTwo = TextureLoader.getTexture("defaultModResources/images/Waifus/Zitong/attack_intent_2.png");
    private static Texture intentThree = TextureLoader.getTexture("defaultModResources/images/Waifus/Zitong/attack_intent_3.png");
    private static Texture intentFour = TextureLoader.getTexture("defaultModResources/images/Waifus/Zitong/attack_intent_4.png");
    private static Texture intentFive = TextureLoader.getTexture("defaultModResources/images/Waifus/Zitong/attack_intent_5.png");
    private static Texture intentSix = TextureLoader.getTexture("defaultModResources/images/Waifus/Zitong/attack_intent_6.png");
    private static Texture intentSeven = TextureLoader.getTexture("defaultModResources/images/Waifus/Zitong/attack_intent_7.png");
    private static Texture[] intentImgs = {intentOne, intentTwo, intentThree, intentFour, intentFive, intentSix, intentSeven};
    
    private AbstractMonster target;
    int  basedmg;

    public ZitongBase(float offSetX, boolean slotOne) {
        super(NAME, ID, ZitongStats.ZitongHP,
                -2.0F, 10.0F, 230.0F, 240.0F, "defaultModResources/images/Waifus/Zitong/Zitong.png", offSetX, 0, intentImgs, slotOne);
        addMoves();
    }


    
    @Override
    public void applyStartOfTurnPowers() {

        AbstractDungeon.actionManager.addToBottom(new LoseBlockAction(this, this, this.currentBlock));
        System.out.println(this.name + " has " + this.currentHealth + " hp.");
    	getMove(AbstractDungeon.aiRng.random(0,10));
    }

    @Override
    public void applyEndOfTurnTriggers() {
        super.applyEndOfTurnTriggers();
    }

    public void takeTurn()

    {

    	 System.out.println("Zitong Taking Turn. nextMove: " + this.nextMove);

        AbstractMonster abstractMonster = AbstractDungeon.getRandomMonster();

        DamageInfo damageInfo = this.damage.get(0);

        damageInfo.applyPowers(this,abstractMonster);
        
    	System.out.println("checking if " + this.name + " has mana");   
        if(this.hasPower(Mana.POWER_ID)) {
        	  this.basedmg =   this.getPower(Mana.POWER_ID).amount + ZitongStats.ZitongAttackDamage;
        	}else {
        		  this.basedmg = ZitongStats.ZitongAttackDamage;
        	}
        
        
        switch (this.nextMove)

        {

            case 1:

            	System.out.println(this.name + " is about to heal");   
                AbstractDungeon.actionManager.addToBottom(new ZitongHeal(this));
                
                setMove((byte)2, Intent.BUFF);

                break;

            case 2:

            	System.out.println(this.name + " is about to attack");   
            	AbstractDungeon.actionManager.addToBottom(new ZitongAttack(this));

 	
                setMove((byte)1, AbstractMonster.Intent.ATTACK, this.basedmg );
            	break;
        }

    }
    
    
    
    
    
    public void addMoves() {
/**
        this.moves.addMove(new MinionMove("Attack", this, new Texture("defaultModResources/images/Waifus/Zitong/Zitong.png"), "Deal 3 plus her mana count damage to all enemies", () -> {
            target = AbstractDungeon.getRandomMonster();
            DamageInfo info = new DamageInfo(this,5,DamageInfo.DamageType.NORMAL);
            info.applyPowers(this, target); // <--- This lets powers effect minions attacks
            AbstractDungeon.actionManager.addToBottom(new ZitongAttack(this));
        }));
        this.moves.addMove(new MinionMove("Heal", this, new Texture("defaultModResources/images/Waifus/Zitong/Zitong.png"),"Gain heal 1 hp to her and all allies", () -> {
            AbstractDungeon.actionManager.addToBottom(new ZitongHeal(this));
        }));
**/
    }

    //Not needed unless doing some kind of random move like normal Monsters
    @Override
    protected void getMove(int num)

    {

        System.out.println("Zitong Getting Move Turn.");

        
        
        if (num<7) {
        	
        	System.out.println(this.name + " is intending to attack");   
            setMove((byte)2, AbstractMonster.Intent.ATTACK, this.basedmg);

        } else {
        	System.out.println(this.name + " is intending to heal");   
            setMove((byte)1, Intent.BUFF);

        }
        

    }
}