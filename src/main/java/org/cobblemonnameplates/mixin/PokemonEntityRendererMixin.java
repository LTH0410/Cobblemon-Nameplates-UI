package org.cobblemonnameplates.mixin;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.pokemon.status.PersistentStatusContainer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.cobblemonnameplates.NameplateMod;
import org.cobblemonnameplates.config.NameplateConfig;
import org.cobblemonnameplates.render.NameplateRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PokemonEntity.class)
public class PokemonEntityRendererMixin {
          
// Note: The mixin target method may not exist in newer Cobblemon versions
// This mixin might need to be updated or removed for 1.21+ compatibility
// Updated for 1.21+ - using the correct method signature for Minecraft 1.21+
@Inject(at = @At("TAIL"), method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/Entity;DDDFFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")
    public void render(PokemonEntity entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Entity entity2, double x, double y, double z, float yaw, float tickDelta, MatrixStack matrices2, VertexConsumerProvider vertexConsumers2, int light2, CallbackInfo ci) {
        if (entity instanceof PokemonEntity) {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client != null && client.world != null) {
                // Check distance to player
                Vec3d playerPos = client.player.getPos();
                Vec3d entityPos = entity.getPos();
                double distance = playerPos.distanceTo(entityPos);
                 
                // Only render if within max distance
                NameplateConfig config = NameplateConfig.getInstance();
                if (distance <= config.getMaxRenderDistance()) {
                    Pokemon pokemon = entity.getPokemon();
                    if (pokemon != null) {
                        NameplateRenderer.renderNameplate(matrices, entity, pokemon, x, y, z, tickDelta);
                    }
                }
            }
        }
    }
}