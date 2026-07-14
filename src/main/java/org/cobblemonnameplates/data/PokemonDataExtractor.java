package org.cobblemonnameplates.data;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.pokemon.status.PersistentStatusContainer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;

public class PokemonDataExtractor {
    
    public static String getPokemonName(Pokemon pokemon) {
        if (pokemon == null) return "Unknown";
        
        Species species = pokemon.getSpecies();
        if (species != null) {
            return species.getName(); // getDisplayName() -> getName()
        }
        
        return "Unknown";
    }
    
    public static int getLevel(Pokemon pokemon) {
        if (pokemon == null) return 0;
        return pokemon.getLevel();
    }
    
    public static String getGenderSymbol(Pokemon pokemon) {
        if (pokemon == null) return "";
        
        switch (pokemon.getGender()) {
            case MALE:
                return "♂";
            case FEMALE:
                return "♀";
            default:
                return "";
        }
    }
    
    public static int getCurrentHp(Pokemon pokemon) {
        if (pokemon == null) return 0;
        return pokemon.getCurrentHealth(); // getHp() -> getCurrentHealth()
    }
    
    public static int getMaxHp(Pokemon pokemon) {
        if (pokemon == null) return 0;
        return pokemon.getMaxHealth(); // getMaxHp() -> getMaxHealth()
    }
    
    public static String getPrimaryType(Pokemon pokemon) {
        if (pokemon == null) return null;
        
        // Handle new Iterable API - getTypes() returns Iterable<ElementalType>
        Iterable<ElementalType> types = pokemon.getTypes();
        if (types != null) {
            java.util.Iterator<ElementalType> iterator = types.iterator();
            if (iterator.hasNext()) {
                ElementalType type = iterator.next();
                return type.getName().toLowerCase();
            }
        }
        return null;
    }
    
    public static String getSecondaryType(Pokemon pokemon) {
        if (pokemon == null) return null;
        
        // Handle new Iterable API - getTypes() returns Iterable<ElementalType>
        Iterable<ElementalType> types = pokemon.getTypes();
        if (types != null) {
            java.util.Iterator<ElementalType> iterator = types.iterator();
            if (iterator.hasNext()) {
                iterator.next(); // Skip first type
                if (iterator.hasNext()) {
                    ElementalType type = iterator.next();
                    return type.getName().toLowerCase();
                }
            }
        }
        return null;
    }
    
    public static String getStatusEffect(Pokemon pokemon) {
        if (pokemon == null) return null;
        
        // Handle new PersistentStatusContainer API - Fixed for 1.21+
        PersistentStatusContainer status = pokemon.getStatus();
        if (status != null) {
            // Check if there are any active status effects
            // Note: PersistentStatusContainer in 1.21+ has different API
            // You may need to implement proper status checking based on the actual API
            return "status"; // Placeholder - implement proper status checking
        }
        return null;
    }
    
    public static boolean hasStatusEffect(Pokemon pokemon) {
        if (pokemon == null) return false;
        
        // Handle new PersistentStatusContainer API - Fixed for 1.21+
        PersistentStatusContainer status = pokemon.getStatus();
        return status != null; // Check if container exists, not specific active status
    }
    
    // New methods for better data extraction
    public static double getHealthPercentage(Pokemon pokemon) {
        if (pokemon == null) return 0.0;
        int currentHp = getCurrentHp(pokemon);
        int maxHp = getMaxHp(pokemon);
        return maxHp > 0 ? (double) currentHp / maxHp : 0.0;
    }
    
    public static boolean isPokemonVisible(Pokemon pokemon, double distance) {
        if (pokemon == null) return false;
        // Check if the Pokemon should be rendered based on distance
        return distance <= org.cobblemonnameplates.config.NameplateConfig.getInstance().getMaxRenderDistance();
    }
}