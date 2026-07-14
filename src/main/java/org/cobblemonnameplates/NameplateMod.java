package org.cobblemonnameplates;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.cobblemonnameplates.mixin.PokemonEntityRendererMixin;

public class NameplateMod implements ModInitializer {
    public static final String MOD_ID = "cobblemon-nameplates";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Cobblemon Nameplates mod");
        
        // Register the mixin for Pokemon entity rendering
        // Note: Mixins are automatically registered through Fabric's system
        // We just need to make sure our mixin class is in the right place
        
        // Initialize configuration
        org.cobblemonnameplates.config.NameplateConfig.getInstance();
    }
}
