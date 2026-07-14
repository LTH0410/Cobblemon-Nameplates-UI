package org.cobblemonnameplates;

import net.minecraft.util.Identifier;
import java.util.HashMap;
import java.util.Map;

public class CobblemonIcons {
    // Type icon identifiers
    public static final Identifier NORMAL_TYPE = new Identifier("cobblemon-nameplates", "textures/type_icons/normal.png");
    public static final Identifier FIRE_TYPE = new Identifier("cobblemon-nameplates", "textures/type_icons/fire.png");
    public static final Identifier WATER_TYPE = new Identifier("cobblemon-nameplates", "textures/type_icons/water.png");
    public static final Identifier ELECTRIC_TYPE = new Identifier("cobblemon-nameplates", "textures/type_icons/electric.png");
    public static final Identifier GRASS_TYPE = new Identifier("cobblemon-nameplates", "textures/type_icons/grass.png");
    public static final Identifier ICE_TYPE = new Identifier("cobblemon-nameplates", "textures/type_icons/ice.png");
    public static final Identifier FIGHTING_TYPE = new Identifier("cobblemon-nameplates", "textures/type_icons/fighting.png");
    public static final Identifier POISON_TYPE = new Identifier("cobblemon-nameplates", "textures/type_icons/poison.png");
    public static final Identifier GROUND_TYPE = new Identifier("cobblemon-nameplates", "textures/type_icons/ground.png");
    public static final Identifier FLYING_TYPE = new Identifier("cobblemon-nameplates", "textures/type_icons/flying.png");
    public static final Identifier PSYCHIC_TYPE = new Identifier("cobblemon-nameplates", "textures/type_icons/psychic.png");
    public static final Identifier BUG_TYPE = new Identifier("cobblemon-nameplates", "textures/type_icons/bug.png");
    public static final Identifier ROCK_TYPE = new Identifier("cobblemon-nameplates", "textures/type_icons/rock.png");
    public static final Identifier GHOST_TYPE = new Identifier("cobblemon-nameplates", "textures/type_icons/ghost.png");
    public static final Identifier DRAGON_TYPE = new Identifier("cobblemon-nameplates", "textures/type_icons/dragon.png");
    public static final Identifier DARK_TYPE = new Identifier("cobblemon-nameplates", "textures/type_icons/dark.png");
    public static final Identifier STEEL_TYPE = new Identifier("cobblemon-nameplates", "textures/type_icons/steel.png");
    public static final Identifier FAIRY_TYPE = new Identifier("cobblemon-nameplates", "textures/type_icons/fairy.png");
    
    // Status effect identifiers
    public static final Identifier PARALYSIS_EFFECT = new Identifier("cobblemon-nameplates", "textures/status_effects/paralysis.png");
    public static final Identifier POISON_EFFECT = new Identifier("cobblemon-nameplates", "textures/status_effects/poison.png");
    public static final Identifier BURN_EFFECT = new Identifier("cobblemon-nameplates", "textures/status_effects/burn.png");
    public static final Identifier SLEEP_EFFECT = new Identifier("cobblemon-nameplates", "textures/status_effects/sleep.png");
    public static final Identifier FREEZE_EFFECT = new Identifier("cobblemon-nameplates", "textures/status_effects/freeze.png");
    public static final Identifier CONFUSION_EFFECT = new Identifier("cobblemon-nameplates", "textures/status_effects/confusion.png");
    
    private static final Map<String, Identifier> typeIconMap = new HashMap<>();
    
    static {
        typeIconMap.put("normal", NORMAL_TYPE);
        typeIconMap.put("fire", FIRE_TYPE);
        typeIconMap.put("water", WATER_TYPE);
        typeIconMap.put("electric", ELECTRIC_TYPE);
        typeIconMap.put("grass", GRASS_TYPE);
        typeIconMap.put("ice", ICE_TYPE);
        typeIconMap.put("fighting", FIGHTING_TYPE);
        typeIconMap.put("poison", POISON_TYPE);
        typeIconMap.put("ground", GROUND_TYPE);
        typeIconMap.put("flying", FLYING_TYPE);
        typeIconMap.put("psychic", PSYCHIC_TYPE);
        typeIconMap.put("bug", BUG_TYPE);
        typeIconMap.put("rock", ROCK_TYPE);
        typeIconMap.put("ghost", GHOST_TYPE);
        typeIconMap.put("dragon", DRAGON_TYPE);
        typeIconMap.put("dark", DARK_TYPE);
        typeIconMap.put("steel", STEEL_TYPE);
        typeIconMap.put("fairy", FAIRY_TYPE);
    }
    
    public static Identifier getTypeIcon(String typeName) {
        return typeIconMap.getOrDefault(typeName.toLowerCase(), NORMAL_TYPE);
    }
}