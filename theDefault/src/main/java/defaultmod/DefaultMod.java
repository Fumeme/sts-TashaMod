package defaultmod;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.shop.StorePotion;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import basemod.BaseMod;
import basemod.ModLabel;
import basemod.ModPanel;
import basemod.helpers.RelicType;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.EditKeywordsSubscriber;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostCreateShopPotionSubscriber;
import basemod.interfaces.PostInitializeSubscriber;

import defaultmod.potions.*;
import defaultmod.patches.*;
import defaultmod.relics.*;
import defaultmod.variables.*;
import defaultmod.cards.*;
import defaultmod.characters.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/* 
 * Welcome to this mildly over-commented Slay the Spire modding base. 
 * Use it to make your own mod of any type. - If you want to add any standard in-game content (Character, 
 * cards, relics), this is a good starting point.
 * It features 1 character with a minimal set of things: 1 card of each type, 1 debuff, 1 relic, etc.
 * If you're new to modding, I highly recommend going though the BaseMod wiki for whatever you wish to add 
 * https://github.com/daviscook477/BaseMod/wiki  and work your way thought your made with this base. 
 * Feel free to use this in any way you like, of course. Happy modding!
 */

@SpireInitializer
public class DefaultMod
        implements EditCardsSubscriber, EditRelicsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber,
        EditCharactersSubscriber, PostInitializeSubscriber {
    public static final Logger logger = LogManager.getLogger(DefaultMod.class.getName());

    //This is for the in-game mod settings pannel.
    private static final String MODNAME = "Unnamed Mod";
    private static final String AUTHOR = "SacredDiamond";
    private static final String DESCRIPTION = "Use mana to boost the effectiveness of your cards or unleash powerful cards with painful side effects.";

    // =============== INPUT TEXTURE LOCATION =================

    // Colors (RGB)
        // Character Color
        public static final Color DEFAULT_GRAY = CardHelper.getColor(64.0f, 70.0f, 70.0f);

        // Potion Colors in RGB
        public static final Color PLACEHOLDER_POTION_lIQUID = CardHelper.getColor(209.0f, 53.0f, 18.0f); // Orange-ish Red 
        public static final Color PLACEHOLDER_POTION_HYBRID = CardHelper.getColor(255.0f, 230.0f, 230.0f); // Near White
        public static final Color PLACEHOLDER_POTION_SPOTS = CardHelper.getColor(100.0f, 25.0f, 10.0f); // Super Dark Red/Brown
        
    // Image folder name
    private static final String DEFAULT_MOD_ASSETS_FOLDER = "defaultModResources/images";

    // Card backgrounds
    private static final String ATTACK_DEAFULT_GRAY = "512/bg_attack_default_gray.png";
    private static final String POWER_DEAFULT_GRAY = "512/bg_power_default_gray.png";
    private static final String SKILL_DEAFULT_GRAY = "512/bg_skill_default_gray.png";
    private static final String ENERGY_ORB_DEAFULT_GRAY = "512/card_default_gray_orb.png";
    private static final String CARD_ENERGY_ORB = "512/card_small_orb.png";

    private static final String ATTACK_DEAFULT_GRAY_PORTRAIT = "1024/bg_attack_default_gray.png";
    private static final String POWER_DEAFULT_GRAY_PORTRAIT = "1024/bg_power_default_gray.png";
    private static final String SKILL_DEAFULT_GRAY_PORTRAIT = "1024/bg_skill_default_gray.png";
    private static final String ENERGY_ORB_DEAFULT_GRAY_PORTRAIT = "1024/card_default_gray_orb.png";

    // Card images
    public static final String DEFAULT_COMMON_ATTACK = "cards/Attack.png";
    public static final String DEFAULT_COMMON_SKILL = "cards/Skill.png";
    public static final String DEFAULT_COMMON_POWER = "cards/Power.png";
    public static final String DEFAULT_UNCOMMON_ATTACK = "cards/Attack.png";
    public static final String DEFAULT_UNCOMMON_SKILL = "cards/Skill.png";
    public static final String DEFAULT_UNCOMMON_POWER = "cards/Power.png";
    public static final String DEFAULT_RARE_ATTACK = "cards/Attack.png";
    public static final String DEFAULT_RARE_SKILL = "cards/Skill.png";
    public static final String DEFAULT_RARE_POWER = "cards/Power.png";

    // Power images
    public static final String COMMON_POWER = "powers/placeholder_power.png";
    public static final String UNCOMMON_POWER = "powers/placeholder_power.png";
    public static final String RARE_POWER = "powers/placeholder_power.png";

    // Relic images  
    public static final String PLACEHOLDER_RELIC = "relics/placeholder_relic.png";
    public static final String PLACEHOLDER_RELIC_OUTLINE = "relics/outline/placeholder_relic.png";

    public static final String PLACEHOLDER_RELIC_2 = "relics/placeholder_relic2.png";
    public static final String PLACEHOLDER_RELIC_OUTLINE_2 = "relics/outline/placeholder_relic2.png";
    
    // Character assets
    private static final String THE_DEFAULT_BUTTON = "charSelect/DefaultCharacterButton.png";
    private static final String THE_DEFAULT_PORTRAIT = "charSelect/DeafultCharacterPortraitBG.png";
    public static final String THE_DEFAULT_SHOULDER_1 = "char/defaultCharacter/shoulder.png";
    public static final String THE_DEFAULT_SHOULDER_2 = "char/defaultCharacter/shoulder2.png";
    public static final String THE_DEFAULT_CORPSE = "char/defaultCharacter/corpse.png";

    //Mod Badge
    public static final String BADGE_IMAGE = "Badge.png";

    // Animations atlas and JSON files
    public static final String THE_DEFAULT_SKELETON_ATLAS = "char/defaultCharacter/skeleton.atlas";
    public static final String THE_DEFAULT_SKELETON_JSON = "char/defaultCharacter/skeleton.json";

    // =============== /INPUT TEXTURE LOCATION/ =================

    /**
     * Makes a full path for a resource path
     * 
     * @param resource the resource, must *NOT* have a leading "/"
     * @return the full path
     */
    public static final String makePath(String resource) {
        return DEFAULT_MOD_ASSETS_FOLDER + "/" + resource;
    }

    // =============== SUBSCRIBE, CREATE THE COLOR, INITIALIZE =================

    public DefaultMod() {
        logger.info("Subscribe to basemod hooks");

        BaseMod.subscribe(this);

        logger.info("Done subscribing");

        logger.info("Creating the color " + AbstractCardEnum.DEFAULT_GRAY.toString());

        BaseMod.addColor(AbstractCardEnum.DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY,
                DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY, makePath(ATTACK_DEAFULT_GRAY),
                makePath(SKILL_DEAFULT_GRAY), makePath(POWER_DEAFULT_GRAY),
                makePath(ENERGY_ORB_DEAFULT_GRAY), makePath(ATTACK_DEAFULT_GRAY_PORTRAIT),
                makePath(SKILL_DEAFULT_GRAY_PORTRAIT), makePath(POWER_DEAFULT_GRAY_PORTRAIT),
                makePath(ENERGY_ORB_DEAFULT_GRAY_PORTRAIT), makePath(CARD_ENERGY_ORB));

        logger.info("Done Creating the color");
    }

    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= Initializing Default Mod. Hi. =========================");
        DefaultMod defaultmod = new DefaultMod();
        logger.info("========================= /Default Mod Initialized/ =========================");
    }

    // ============== /SUBSCRIBE, CREATE THE COLOR, INITIALIZE/ =================

    
    // =============== LOAD THE CHARACTER =================

    @Override
    public void receiveEditCharacters() {
        logger.info("begin editing characters. " + "Add " + TheDefaultEnum.THE_DEFAULT.toString());

        BaseMod.addCharacter(new TheDefault("The Corrupted", TheDefaultEnum.THE_DEFAULT),
                makePath(THE_DEFAULT_BUTTON), makePath(THE_DEFAULT_PORTRAIT), TheDefaultEnum.THE_DEFAULT);
        
        receiveEditPotions();
        logger.info("done editing characters");
    }

    // =============== /LOAD THE CHARACTER/ =================

    
    // =============== POST-INITIALIZE =================

    
    @Override
    public void receivePostInitialize() {

        logger.info("Load Badge Image and mod options");
        // Load the Mod Badge
        Texture badgeTexture = new Texture(makePath(BADGE_IMAGE));
        
        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();
        settingsPanel.addUIElement(new ModLabel("no settings, jk", 400.0f, 700.0f,
                settingsPanel, (me) -> {
                }));
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        logger.info("Done loading badge Image and mod options");

       }

    // =============== / POST-INITIALIZE/ =================

    
    // ================ ADD POTIONS ===================

       
    public void receiveEditPotions() {
        logger.info("begin editing potions");
       
        // Class Specific Potion If you want your potion to not be class-specific, just remove the player class at the end (in this case the "TheDefaultEnum.THE_DEFAULT")
        BaseMod.addPotion(BrainJuice.class, PLACEHOLDER_POTION_lIQUID, PLACEHOLDER_POTION_HYBRID, PLACEHOLDER_POTION_SPOTS, BrainJuice.POTION_ID, TheDefaultEnum.THE_DEFAULT);
      
        logger.info("end editing potions");
    }
    
    // ================ /ADD POTIONS/ ===================

    
    // ================ ADD RELICS ===================

    @Override
    public void receiveEditRelics() {
        logger.info("Add relics");

        // This adds a character specific relic. Only when you play with the mentioned color, will you get this relic.
        BaseMod.addRelicToCustomPool(new PlaceholderRelic(), AbstractCardEnum.DEFAULT_GRAY);

        // This adds a relic to the Shared pool. Every character can find this relic.
        BaseMod.addRelic(new PlaceholderRelic2(), RelicType.SHARED);
        
        BaseMod.addRelic(new ManaReactor(), RelicType.SHARED);

        logger.info("Done adding relics!");
    }

    // ================ /ADD RELICS/ ===================

    
    
    // ================ ADD CARDS ===================

    @Override
    public void receiveEditCards() {
        logger.info("Add Variables");
        // Add the Custom Dynamic Variables
        BaseMod.addDynamicVariable(new Magicx2());
        BaseMod.addDynamicVariable(new MagicP1());
        
        logger.info("Add Cards");
        // Add the cards
        BaseMod.addCard(new DefaultCommonAttack());
        BaseMod.addCard(new DefaultCommonSkill());
        
        BaseMod.addCard(new QuickDraw());
        BaseMod.addCard(new HairyTrigger());
        BaseMod.addCard(new Channel());
        BaseMod.addCard(new Focusfire());
        BaseMod.addCard(new BattleSense());
        BaseMod.addCard(new Flare());
        BaseMod.addCard(new ShortTerm());
        BaseMod.addCard(new Overhead());
        BaseMod.addCard(new TransMind());
        BaseMod.addCard(new ReinArmor());
        BaseMod.addCard(new SpecializedShot());
        BaseMod.addCard(new PowerNap());
        BaseMod.addCard(new CursedStrike());
        BaseMod.addCard(new Corrupt());
        BaseMod.addCard(new InfernalForm());
        BaseMod.addCard(new Embrace());
        BaseMod.addCard(new SealTheWounds());
        BaseMod.addCard(new ToxicWall());
        BaseMod.addCard(new SpreadCorruption());
        BaseMod.addCard(new LongTerm());
        BaseMod.addCard(new MagicArmor());
        BaseMod.addCard(new InnerHeaven());
        BaseMod.addCard(new SappingStrike());
        BaseMod.addCard(new Overload());        
        BaseMod.addCard(new DarkmagicSlice()); 
        BaseMod.addCard(new ManaBurst()); 
        BaseMod.addCard(new BreakTheseCuffs()); 
        BaseMod.addCard(new EndlessTorment()); 
        BaseMod.addCard(new HybridRounds()); 
        
        logger.info("Making sure the cards are unlocked.");
        // Unlock the cards
        UnlockTracker.unlockCard(DefaultCommonAttack.ID);
        UnlockTracker.unlockCard(DefaultCommonSkill.ID);

        logger.info("Cards - added!");
    }

    // ================ /ADD CARDS/ ===================

    
    
    // ================ LOAD THE TEXT ===================

    @Override
    public void receiveEditStrings() {
        logger.info("Begin editting strings");

        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                "defaultModResources/localization/DefaultMod-Card-Strings.json");

        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                "defaultModResources/localization/DefaultMod-Power-Strings.json");

        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                "defaultModResources/localization/DefaultMod-Relic-Strings.json");

        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                "defaultModResources/localization/DefaultMod-Potion-Strings.json");

        logger.info("Done edittting strings");
    }

    // ================ /LOAD THE TEXT/ ===================

    // ================ LOAD THE KEYWORDS ===================

    @Override
    public void receiveEditKeywords() {
        final String[] placeholder = { "keyword", "keywords" };
        BaseMod.addKeyword(placeholder, "Whenever you play a card, gain 1 dexterity this turn only.");

        final String[] Magic = {"magic"};
        BaseMod.addKeyword(Magic, "(Magic Y) Requires atleast Y stacks if Mana to activate the cards effect");

        final String[] Mana = {"mana"};
        BaseMod.addKeyword(Mana, "Used for Powering your cards.");
        
        final String[] Decay = {"decay"};
        BaseMod.addKeyword(Decay, "At the end of your turn, take damage equal to the stacks this has then reduce it by one (just blockable poison)");
    }

    // ================ /LOAD THE KEYWORDS/ ===================    

    // this adds "ModName: " before the ID of any card/relic/power etc.
    // in order to avoid conflics if any other mod uses the same ID.
    public static String makeID(String idText) {
        return "Corrupted: " + idText;
    }

}
