package com.elcezarx.papereadhud.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.PlayerFaceRenderer;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.Identifier;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;
import com.mojang.blaze3d.platform.InputConstants;


public class PaperHeadHudClient implements ClientModInitializer {
    private static KeyMapping openConfigKey;
    private static long startTime = System.currentTimeMillis();


    @Override
    public void onInitializeClient() {

        PaperHeadHudConfig.load();

        HudElementRegistry.attachElementBefore(
                VanillaHudElements.CHAT,
                Identifier.fromNamespaceAndPath("papereadhud", "paper_head"),
                PaperHeadHudClient::renderHud
        );
        openConfigKey = KeyBindingHelper.registerKeyBinding(
                new KeyMapping(
                        "PaperHeadHud Up",
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_B,
                        KeyMapping.Category.MISC
                )
        );


    }

    private static void renderHud(GuiGraphics graphics, DeltaTracker tickCounter) {
        Minecraft mc = Minecraft.getInstance();

        while (openConfigKey.consumeClick()) {
            Minecraft.getInstance().setScreen(new PaperHeadHudConfigScreen());
        }



        int offsetX = 0;


        int x = graphics.guiWidth() / 2 - 8 + offsetX;
        int y = graphics.guiHeight() / 2 + PaperHeadHudConfig.offsetY;

        var skin = ((AbstractClientPlayer) mc.player).getSkin();

        PlayerFaceRenderer.draw(
                graphics,
                skin,
                x,
                y,
                PaperHeadHudConfig.headSize
        );

        int posX = mc.player.getBlockX();
        int posY = mc.player.getBlockY();
        int posZ = mc.player.getBlockZ();

        long day = mc.player.level().getDayTime() / 24000L + 1;

        long elapsed = Minecraft.getInstance().player.level().getGameTime() / 20;
        long minutes = elapsed / 60;
        long seconds = elapsed % 60;

        String hudText =
                "§eXYZ §f" + posX + " " + posY + " " + posZ
                        + " §e| DAY §f" + day
                        + " §7[" + minutes + ":" + String.format("%02d", seconds) + "]";

        graphics.drawString(
                Minecraft.getInstance().font,
                hudText,
                x - 70,
                y + 20,
                0xFFFFFFFF,
                true
        );
    }
}