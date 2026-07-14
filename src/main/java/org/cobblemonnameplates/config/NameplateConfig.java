package org.cobblemonnameplates.config;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

public class NameplateConfig {
    private static NameplateConfig instance;
    
    // Maximum render distance for nameplates
    private double maxRenderDistance = 64.0;
    
    // Whether to show nameplates for wild Pokemon
    private boolean showWildPokemonNameplates = true;
    
    // Whether to show nameplates for player's Pokemon
    private boolean showPlayerPokemonNameplates = true;
    
    // Whether to show health bars
    private boolean showHealthBars = true;
    
    // Whether to show type icons
    private boolean showTypeIcons = true;
    
    // Whether to show status effects
    private boolean showStatusEffects = true;
    
    // Whether to always show nameplates (even when not in battle)
    private boolean alwaysShowNameplates = true;
    
    // Whether to show gender symbols
    private boolean showGenderSymbols = true;
    
    public static NameplateConfig getInstance() {
        if (instance == null) {
            instance = new NameplateConfig();
        }
        return instance;
    }
    
    // Getters and setters
    public double getMaxRenderDistance() {
        return maxRenderDistance;
    }
    
    public void setMaxRenderDistance(double maxRenderDistance) {
        this.maxRenderDistance = maxRenderDistance;
    }
    
    public boolean isShowWildPokemonNameplates() {
        return showWildPokemonNameplates;
    }
    
    public void setShowWildPokemonNameplates(boolean showWildPokemonNameplates) {
        this.showWildPokemonNameplates = showWildPokemonNameplates;
    }
    
    public boolean isShowPlayerPokemonNameplates() {
        return showPlayerPokemonNameplates;
    }
    
    public void setShowPlayerPokemonNameplates(boolean showPlayerPokemonNameplates) {
        this.showPlayerPokemonNameplates = showPlayerPokemonNameplates;
    }
    
    public boolean isShowHealthBars() {
        return showHealthBars;
    }
    
    public void setShowHealthBars(boolean showHealthBars) {
        this.showHealthBars = showHealthBars;
    }
    
    public boolean isShowTypeIcons() {
        return showTypeIcons;
    }
    
    public void setShowTypeIcons(boolean showTypeIcons) {
        this.showTypeIcons = showTypeIcons;
    }
    
    public boolean isShowStatusEffects() {
        return showStatusEffects;
    }
    
    public void setShowStatusEffects(boolean showStatusEffects) {
        this.showStatusEffects = showStatusEffects;
    }
    
    public boolean isAlwaysShowNameplates() {
        return alwaysShowNameplates;
    }
    
    public void setAlwaysShowNameplates(boolean alwaysShowNameplates) {
        this.alwaysShowNameplates = alwaysShowNameplates;
    }
    
    public boolean isShowGenderSymbols() {
        return showGenderSymbols;
    }
    
    public void setShowGenderSymbols(boolean showGenderSymbols) {
        this.showGenderSymbols = showGenderSymbols;
    }
}