package defaultmod.cards;

import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import defaultmod.DefaultMod;
import defaultmod.patches.AbstractCardEnum;
import defaultmod.waifus.zitong.ZitongBase;
import kobting.friendlyminions.characters.AbstractPlayerWithMinions;

public class CallZitong extends CustomCard {

    public static final String ID = defaultmod.DefaultMod.makeID("CallZitong");

    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
/**
    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
**/
    public static final String IMG_PATH = DefaultMod.makePath(DefaultMod.SappingStrike);

    private static final int COST = 1;

    private static final CardRarity rarity = CardRarity.COMMON;

    private static final CardTarget target = CardTarget.SELF;



    public CallZitong() {

        super(ID, "CallZitong", IMG_PATH, COST, "Call in Zitong.",

                CardType.SKILL, AbstractCardEnum.DEFAULT_GRAY,

                rarity, target);

    }



    @Override

    public void upgrade() {

        this.upgradeName();

        this.upgradeBaseCost(0);



    }



    @Override

    public AbstractCard makeCopy() {

        return new CallZitong();

    }



    @Override

    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    	if(abstractPlayer instanceof AbstractPlayerWithMinions) {
    		
            AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) abstractPlayer;

            
            if(player.hasMinion(ZitongBase.ID)) {
            
            AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "She's already out!", 1.0F, 2.0F));
            
            }else {
            	
            	player.addMinion(new ZitongBase(-750F, true));
            }
        }

    	/**
    	
    	
        if (abstractPlayer instanceof AbstractPlayerWithMinions) {

            AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) abstractPlayer;

            int summonCount = player.minions.monsters.size();

            if (summonCount == 0) {

                player.addMinion(new ZitongBase(-750F, true));

                AbstractMonster ZitongBase0 = player.minions.monsters.get(0);

                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(ZitongBase0, abstractPlayer, new Mana(ZitongBase0, ZitongBase0, 0), 0));

            } else if (summonCount == 1) {

                if (player.minions.monsters.get(0).id.equals(ZitongBase.ID)) {

                    //Upgrade

                    AbstractMonster ZitongBase0Upgraded = player.minions.monsters.get(0);

                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(ZitongBase0Upgraded, abstractPlayer, new Mana(ZitongBase0Upgraded, ZitongBase0Upgraded, 1), 1));

                } else {

                    //No Upgrade

                    AbstractCorrMinion yohaneMinion = null;

                    AbstractMonster summonedMonster = player.minions.monsters.get(0);

                    if (summonedMonster instanceof AbstractCorrMinion) {

                        yohaneMinion = (AbstractCorrMinion)summonedMonster;

                    }

                    if (yohaneMinion != null && yohaneMinion.slotOne)  {

                        player.addMinion(new ZitongBase(-1150F, false));

                    } else {

                        player.addMinion(new ZitongBase(-750F, true));

                    }

                    AbstractMonster ZitongBase1 = player.minions.monsters.get(1);

                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(ZitongBase1, abstractPlayer, new Mana(ZitongBase1, ZitongBase1, 0), 0));

                }

            } else if (summonCount == 2) {

                if (player.minions.monsters.get(0).id.equals(ZitongBase.ID)) {

                    //Upgrade

                    AbstractMonster ZitongBase0Upgraded = player.minions.monsters.get(0);

                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(ZitongBase0Upgraded, abstractPlayer, new Mana(ZitongBase0Upgraded, ZitongBase0Upgraded, 1), 1));

                } else if (player.minions.monsters.get(1).id.equals(ZitongBase.ID)) {

                    //Upgrade

                    AbstractMonster ZitongBase1Upgraded = player.minions.monsters.get(1);

                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(ZitongBase1Upgraded, abstractPlayer, new Mana(ZitongBase1Upgraded, ZitongBase1Upgraded, 1), 1));

                }

            } else {

                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "I can't summon another Waifu!", 1.0F, 2.0F));

            }

        }
**/
    }





}