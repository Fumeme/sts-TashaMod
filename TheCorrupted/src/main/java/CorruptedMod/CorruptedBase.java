package CorruptedMod;

import java.nio.charset.StandardCharsets;

import CorruptedMod.Encounters.Enemies.InfernalSpawn;
import CorruptedMod.cards.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.rewards.RewardSave;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import CorruptedMod.cards.ammo.*;
import CorruptedMod.cards.lore.*;
import CorruptedMod.cards.test.*;
import CorruptedMod.characters.*;
import CorruptedMod.patches.*;
import CorruptedMod.potions.*;
import CorruptedMod.relics.*;
import CorruptedMod.variables.*;
import basemod.BaseMod;
import basemod.ModLabel;
import basemod.ModPanel;
import basemod.helpers.RelicType;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.EditKeywordsSubscriber;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import com.evacipated.cardcrawl.mod.stslib.Keyword;

import static basemod.BaseMod.addMonster;

@SpireInitializer
public class CorruptedBase
        implements EditCardsSubscriber, EditRelicsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber,
        EditCharactersSubscriber, PostInitializeSubscriber {
    public static final Logger logger = LogManager.getLogger(CorruptedBase.class.getName());

    //This is for the in-game mod settings pannel.
    private static final String MODNAME = "The Corrupted";
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
    private static final String DEFAULT_MOD_ASSETS_FOLDER = "CorruptedResources/images";

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

    public static final String SappingStrike = "cards/sapping-strike.png";
    public static final String Corrupt = "cards/Corrupt.png";
    public static final String InfernalForm = "cards/Infernal-Form.png";
    public static final String ReinArmor = "cards/ReinArmor.png";
    public static final String ToxicWall = "cards/Toxic-Wall.png";
    public static final String SpreadCorruption = "cards/SpreadCorruption.png";
    public static final String TransMind = "cards/TransMind.png";
    public static final String Sleep = "cards/Sleep.png";
    
    
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
    
    //Card Tags
	@SpireEnum public static AbstractCard.CardTags Ammo;
	@SpireEnum public static AbstractCard.CardTags Lore;

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

    public CorruptedBase() {
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
        logger.info("========================= Initializing Corrupted Mod. =========================");
        CorruptedBase defaultmod = new CorruptedBase();
        logger.info("========================= /Corrupted Mod Initialized/ =========================");
    }

    // ============== /SUBSCRIBE, CREATE THE COLOR, INITIALIZE/ =================

    
    // =============== LOAD THE CHARACTER =================

    @Override
    public void receiveEditCharacters() {
        logger.info("begin editing characters. " + "Add " + TheDefaultEnum.THE_DEFAULT.toString());

        BaseMod.addCharacter(new Tasha("The Corrupted", TheDefaultEnum.THE_DEFAULT),
                makePath(THE_DEFAULT_BUTTON), makePath(THE_DEFAULT_PORTRAIT), TheDefaultEnum.THE_DEFAULT);
        
        receiveEditPotions();
        logger.info("done editing characters");
    }

    // =============== /LOAD THE CHARACTER/ =================

    
    // =============== POST-INITIALIZE =================

    
    @Override
    public void receivePostInitialize() {
logger.info("adding monster(s)");

        addMonster("Infernal_Spawn", "Infernal Spawn", () -> new InfernalSpawn());

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
        
        BaseMod.registerCustomReward(
                LoreDiaryPatch.LoreDiaryReward, 
                (rewardSave) -> { // this handles what to do when this quest type is loaded.
                    return new LoreDiaryReward();
                }, 
                (customReward) -> { // this handles what to do when this quest type is saved.
                    return new RewardSave(customReward.type.toString(), null);
                });

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
        BaseMod.addRelicToCustomPool(new InfernalCore(), AbstractCardEnum.DEFAULT_GRAY);
        BaseMod.addRelicToCustomPool(new ManaReactor(), AbstractCardEnum.DEFAULT_GRAY);
        BaseMod.addRelicToCustomPool(new LoreDiary(), AbstractCardEnum.DEFAULT_GRAY);

        // This adds a relic to the Shared pool. Every character can find this relic.
        BaseMod.addRelic(new PlaceholderRelic2(), RelicType.SHARED);
        BaseMod.addRelic(new FadingHope(), RelicType.SHARED);
        BaseMod.addRelic(new OddShapedKey(), RelicType.SHARED);

        logger.info("Done adding relics!");
    }

    // ================ /ADD RELICS/ ===================

    
    
    // ================ ADD CARDS ===================

    @Override
    public void receiveEditCards() {
        logger.info("Add Variables");
        BaseMod.addDynamicVariable(new MagicalMagic());

        // Add the Custom Dynamic Variables
        BaseMod.addDynamicVariable(new Magicx2());
        BaseMod.addDynamicVariable(new MagicP1());
        BaseMod.addDynamicVariable(new TuP1());
        BaseMod.addDynamicVariable(new DamageM1());
        BaseMod.addDynamicVariable(new DamageM2());
        
        logger.info("Add Cards");
        // Add the cards
        
        BaseMod.addCard(new DefaultCommonAttack());
        BaseMod.addCard(new DefaultCommonSkill());
        BaseMod.addCard(new CursedStrike());
        BaseMod.addCard(new Focusfire());
        BaseMod.addCard(new Channel());
        
        
        BaseMod.addCard(new QuickDraw());
        BaseMod.addCard(new HairyTrigger());
        
        
        BaseMod.addCard(new BattleSense());
        BaseMod.addCard(new Flare());
        BaseMod.addCard(new ShortTerm());
        BaseMod.addCard(new Overhead());
        BaseMod.addCard(new TransMind());
        BaseMod.addCard(new ReinArmor());
        BaseMod.addCard(new SpecializedShot());
        BaseMod.addCard(new PowerNap());
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
        BaseMod.addCard(new Curse()); 
        BaseMod.addCard(new ImmovableObject()); 
        BaseMod.addCard(new aWeakDiamond()); 
        
        BaseMod.addCard(new AimForTheHead()); 
        BaseMod.addCard(new EvilCloud()); 
        BaseMod.addCard(new aLittleBitExtra()); 
        BaseMod.addCard(new witheringBarrier()); 
        BaseMod.addCard(new Reload());
        BaseMod.addCard(new BlackArmor()); 
        BaseMod.addCard(new MaxOut()); 
        BaseMod.addCard(new FocusCard());

        BaseMod.addCard(new BurstFire());
        BaseMod.addCard(new ManaShell()); 
        BaseMod.addCard(new RapidFire());  
        BaseMod.addCard(new ManaBLightTest()); 
        
        BaseMod.addCard(new EnergyCannon()); 
        BaseMod.addCard(new ManaSparkAmmo()); 
        BaseMod.addCard(new PureManaAmmo()); 
        /*
         * 
         */
        
        BaseMod.addCard(new ReloadLore()); 
        BaseMod.addCard(new SpecializedShotLore()); 
        BaseMod.addCard(new loreSpire()); 
        


        logger.info("Making sure the cards are unlocked.");
        // Unlock the cards
        UnlockTracker.unlockCard(DefaultCommonAttack.ID);
        UnlockTracker.unlockCard(DefaultCommonSkill.ID);
        UnlockTracker.unlockCard(Focusfire.ID);
        UnlockTracker.unlockCard(Channel.ID);
        UnlockTracker.unlockCard(CursedStrike.ID);

        logger.info("Cards - added!");
    }

    // ================ /ADD CARDS/ ===================

    
    
    // ================ LOAD THE TEXT ===================

    @Override
    public void receiveEditStrings() {
        logger.info("Begin editting strings");

        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                "CorruptedResources/localization/Corrupted-Card-Strings.json");

        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                "CorruptedResources/localization/Corrupted-Power-Strings.json");

        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                "CorruptedResources/localization/Corrupted-Relic-Strings.json");

        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                "CorruptedResources/localization/Corrupted-Potion-Strings.json");

        logger.info("Done edittting strings");
    }

    // ================ /LOAD THE TEXT/ ===================
    

    

    // ================ LOAD THE KEYWORDS ===================

    @Override
    public void receiveEditKeywords() {

        final String[] Magic = {"[#239ecf]magic", "magic"};
        BaseMod.addKeyword(Magic, "(Magic Y) Requires atleast Y stacks of Mana to activate the cards effect");
        
        final String[] Corruption = {"[#9c65c3]corruption"};
        BaseMod.addKeyword(Corruption, "(Corruption Z) Requires atleast Z stacks of Decay to activate the cards effect");

        final String[] Mana = {"mana", "[][#239ecf]mana", "[#239ecf]mana"};
        BaseMod.addKeyword(Mana, "Used for Powering your cards.");
        
        final String[] Decay = {"decay"};
        BaseMod.addKeyword(Decay, "At the end of your turn, take damage equal to the stacks this has then reduce it by one (just blockable poison)");
  
        final String[] Ammo = {"ammo"};
        BaseMod.addKeyword(Ammo, "Ammo cards are low damage 0-cost attacks that have multiple magic effects");
        
        final String[] Overexertion = {"overexertion"};
        BaseMod.addKeyword(Overexertion, "Overexertion is a status that when drawn, you gain gain 1 Mana, gain 1 Inefficiency and take 2 damage");
        
        final String[] Inefficiency = {"inefficiency"};
        BaseMod.addKeyword(Inefficiency, "if you have more than 3 mana redude you mana by you Inefficiency stacks otherwise it removes itself.");
  
        
        Gson gson = new Gson();
        String json = Gdx.files.internal("CorruptedResources/localization/Corrupted-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = (Keyword[])gson.fromJson(json, Keyword[].class);
        if (keywords != null) {
          for (Keyword keyword : keywords) {
            BaseMod.addKeyword(keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
          }
        }

    }


    // ================ /LOAD THE KEYWORDS/ ===================    

    // this adds "ModName: " before the ID of any card/relic/power etc.
    // in order to avoid conflics if any other mod uses the same ID.
    public static String makeID(String idText) {
        return "Corrupted: " + idText;
    }
    


}
