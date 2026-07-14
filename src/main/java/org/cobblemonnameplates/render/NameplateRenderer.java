package org.cobblemonnameplates.render;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.pokemon.status.PersistentStatusContainer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.cobblemonnameplates.config.NameplateConfig;
import org.cobblemonnameplates.data.PokemonDataExtractor;
import org.cobblemonnameplates.CobblemonIcons;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumer;

// 1.21+ 호환성을 위한 추가 임포트 - 직접적인 렌더링 코드로 수정
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;

public class NameplateRenderer {
    public static void renderNameplate(MatrixStack matrices, Entity entity, Pokemon pokemon, double x, double y, double z, float tickDelta) {
        NameplateConfig config = NameplateConfig.getInstance();
        
        // Check if we should render this nameplate
        if (!shouldRenderNameplate(entity)) {
            return;
        }
        
        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer textRenderer = client.textRenderer;
        
        // Calculate distance for scaling
        double distance = client.gameRenderer.getCamera().getPos().distanceTo(entity.getPos());
        if (distance > config.getMaxRenderDistance()) {
            return;
        }
        
        // Set up matrix for rendering
        matrices.push();
        
        // Position at the entity's head position
        Vec3d entityPos = entity.getPos().add(0, entity.getHeight() + 0.5, 0);
        double renderX = entityPos.x;
        double renderY = entityPos.y;
        double renderZ = entityPos.z;
        
        // Adjust Y position to be above the entity
        renderY += 1.5; // Adjust as needed
        
        // Apply billboard effect (always face player)
        if (config.isAlwaysShowNameplates()) {
            applyBillboard(matrices);
        }
        
        // Scale based on distance
        float scale = calculateScale(distance);
        matrices.scale(scale, scale, scale);
        
        // Render nameplate elements
        renderNameplateContent(matrices, textRenderer, pokemon, renderX, renderY, renderZ);
        
        matrices.pop();
    }
    
    private static boolean shouldRenderNameplate(Entity entity) {
        MinecraftClient client = MinecraftClient.getInstance();
        return client != null && 
               client.player != null && 
               !entity.isSneaking() &&  // Don't render if sneaking
               entity.isAlive(); // Only render alive entities
    }
    
    private static void applyBillboard(MatrixStack matrices) {
        // Get the camera position
        MinecraftClient client = MinecraftClient.getInstance();
        Vec3d cameraPos = client.gameRenderer.getCamera().getPos();
        
        // Apply rotation to face the camera (simplified billboard)
        matrices.multiply(client.gameRenderer.getCamera().getRotation());
    }
    
    private static float calculateScale(double distance) {
        // Scale based on distance (smaller at distance)
        float scale = 0.025f; // Base scale
        scale /= (float)(distance / 16.0); // Reduce scale with distance
        
        // Clamp minimum scale
        return Math.max(scale, 0.01f);
    }
    
    private static void renderNameplateContent(MatrixStack matrices, TextRenderer textRenderer, Pokemon pokemon, double x, double y, double z) {
        String name = PokemonDataExtractor.getPokemonName(pokemon);
        int level = PokemonDataExtractor.getLevel(pokemon);
        String genderSymbol = PokemonDataExtractor.getGenderSymbol(pokemon);
        
        // Render name and level
        String nameText = name + " Lv." + level + genderSymbol;
        float textWidth = textRenderer.getWidth(nameText) / 2.0f;
        
        matrices.translate(-textWidth, 0, 0); // Center the text
        
        // Render name
        renderText(matrices, textRenderer, Text.literal(nameText), 0xFFFFFF);
        
        // Render HP bar if enabled
        if (NameplateConfig.getInstance().isShowHealthBars()) {
            renderHPBar(matrices, pokemon, x, y - 10, z);
        }
        
        // Render type icons if enabled
        if (NameplateConfig.getInstance().isShowTypeIcons()) {
            renderTypeIcons(matrices, pokemon, x, y - 20, z);
        }
        
        // Render status effects if enabled
        if (NameplateConfig.getInstance().isShowStatusEffects() && PokemonDataExtractor.hasStatusEffect(pokemon)) {
            renderStatusEffects(matrices, pokemon, x, y - 30, z);
        }
    }
    
    private static void renderText(MatrixStack matrices, TextRenderer textRenderer, Text text, int color) {
        // Render text using Minecraft's rendering API
        matrices.push();
        RenderSystem.enableDepthTest();
        RenderSystem.disableCull();
        
        // Use the proper text rendering method - updated for 1.21+
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null && client.world != null) {
            VertexConsumerProvider.Immediate vertexConsumers = client.getBufferBuilders().getEntityVertexConsumers();
            textRenderer.draw(text, 0, 0, color, false, matrices.peek().getPositionMatrix(), vertexConsumers, TextRenderer.TextLayerType.NORMAL, 0, 15728880);
        }
        
        RenderSystem.enableCull();
        RenderSystem.disableDepthTest();
        matrices.pop();
    }
    
    private static void renderHPBar(MatrixStack matrices, Pokemon pokemon, double x, double y, double z) {
        // Get HP values
        int currentHp = pokemon.getCurrentHealth();
        int maxHp = pokemon.getMaxHealth();
        
        if (maxHp <= 0) return;
        
        float hpPercentage = (float)currentHp / maxHp;
        
        // Draw HP bar background (gray)
        drawRectangle(matrices, -15.0f, -10.0f, 30.0f, 4.0f, 0x808080);
        
        // Draw HP bar fill (green, yellow, or red based on percentage)
        int barColor = 0x00FF00; // Default green
        if (hpPercentage < 0.5f) barColor = 0xFFFF00; // Yellow
        if (hpPercentage < 0.25f) barColor = 0xFF0000; // Red
        
        drawRectangle(matrices, -15.0f, -10.0f, 30.0f * hpPercentage, 4.0f, barColor);
    }
    
    private static void renderTypeIcons(MatrixStack matrices, Pokemon pokemon, double x, double y, double z) {
        // Get type information
        Iterable<ElementalType> types = pokemon.getTypes();
        String primaryType = null;
        String secondaryType = null;
        
        if (types != null) {
            java.util.Iterator<ElementalType> iterator = types.iterator();
            if (iterator.hasNext()) {
                primaryType = iterator.next().getName();
                if (iterator.hasNext()) {
                    secondaryType = iterator.next().getName();
                }
            }
        }
        
        if (primaryType == null || primaryType.isEmpty()) return;
        
        // Render primary type icon
        renderTypeIcon(matrices, primaryType, -15.0f, -20.0f);
        
        // Render secondary type icon if exists
        if (secondaryType != null && !secondaryType.isEmpty() && !secondaryType.equals(primaryType)) {
            renderTypeIcon(matrices, secondaryType, 5.0f, -20.0f);
        }
    }
    
    private static void renderStatusEffects(MatrixStack matrices, Pokemon pokemon, double x, double y, double z) {
        // Check for status effects and render them
        // Note: Status effect handling needs to be implemented based on new Cobblemon API
        PersistentStatusContainer statusContainer = pokemon.getStatus();
        if (statusContainer != null) {
            // Render basic status effect indicator
            matrices.translate(35.0f, 0.0f, 0.0f);
            renderText(matrices, MinecraftClient.getInstance().textRenderer, Text.literal("⚠"), 0xFF0000);
        }
    }
    
    private static void drawRectangle(MatrixStack matrices, float x, float y, float width, float height, int color) {
        // Render rectangle using Minecraft's rendering API - 1.21+ compatible
        RenderSystem.enableDepthTest();
        RenderSystem.disableCull();
        
        // Use direct RenderSystem methods for 1.21+ compatibility
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        
        // For 1.21+, use direct RenderSystem shader methods
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        
        matrices.push();
        
        float x1 = x;
        float y1 = y;
        float x2 = x + width;
        float y2 = y + height;
        
        // Add the four vertices of the rectangle - 1.21+ compatible
        bufferBuilder.vertex(matrices.peek().getPositionMatrix(), x1, y2, 0.0F).color(color);
        bufferBuilder.vertex(matrices.peek().getPositionMatrix(), x2, y2, 0.0F).color(color);
        bufferBuilder.vertex(matrices.peek().getPositionMatrix(), x2, y1, 0.0F).color(color);
        bufferBuilder.vertex(matrices.peek().getPositionMatrix(), x1, y1, 0.0F).color(color);
        
        // Draw the buffer using the new method
        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
        matrices.pop();
        
        RenderSystem.enableCull();
        RenderSystem.disableDepthTest();
    }
    
    private static void renderTypeIcon(MatrixStack matrices, String type, float x, float y) {
        // Get the identifier for the type icon
        Identifier iconId = CobblemonIcons.getTypeIcon(type);
        
        // Render the type icon using Minecraft's rendering API - 1.21+ compatible
        RenderSystem.enableDepthTest();
        RenderSystem.disableCull();
        // For 1.21+, use proper texture shader method
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        
        matrices.push();
        
        // Set up texture
        RenderSystem.setShaderTexture(0, iconId);
        
        // Draw the icon (32x32 size) - 1.21+ compatible
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        float width = 16.0f;
        float height = 16.0f;
        
        // Add the four vertices of the icon - 1.21+ compatible
        bufferBuilder.vertex(matrices.peek().getPositionMatrix(), x, y + height, 0.0F)
            .texture(0.0F, 1.0F).color(0xFFFFFFFF);
        bufferBuilder.vertex(matrices.peek().getPositionMatrix(), x + width, y + height, 0.0F)
            .texture(1.0F, 1.0F).color(0xFFFFFFFF);
        bufferBuilder.vertex(matrices.peek().getPositionMatrix(), x + width, y, 0.0F)
            .texture(1.0F, 0.0F).color(0xFFFFFFFF);
        bufferBuilder.vertex(matrices.peek().getPositionMatrix(), x, y, 0.0F)
            .texture(0.0F, 0.0F).color(0xFFFFFFFF);
        
        // Draw the buffer using the new method
        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
        matrices.pop();
        
        RenderSystem.enableCull();
        RenderSystem.disableDepthTest();
    }
}