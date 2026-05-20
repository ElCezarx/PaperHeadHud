package com.elcezarx.papereadhud.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class PaperHeadHudConfigScreen extends Screen {

    public PaperHeadHudConfigScreen() {
        super(Component.literal("PaperHeadHud Config"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        this.addRenderableWidget(Button.builder(Component.literal("▲ Subir"), button -> {
            PaperHeadHudConfig.offsetY -= 5;
            PaperHeadHudConfig.save();
        }).bounds(centerX - 100, centerY - 40, 200, 20).build());

        this.addRenderableWidget(Button.builder(Component.literal("▼ Bajar"), button -> {
            PaperHeadHudConfig.offsetY += 5;
            PaperHeadHudConfig.save();
        }).bounds(centerX - 100, centerY - 15, 200, 20).build());

        this.addRenderableWidget(Button.builder(Component.literal("↺ Reset"), button -> {
            PaperHeadHudConfig.offsetY = 135;
            PaperHeadHudConfig.save();
        }).bounds(centerX - 100, centerY + 10, 200, 20).build());

        this.addRenderableWidget(Button.builder(Component.literal("✓ Guardar y cerrar"), button -> {
            this.minecraft.setScreen(null);
        }).bounds(centerX - 100, centerY + 40, 200, 20).build());
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {

        // Fondo oscuro
        graphics.fill(
                this.width / 2 - 130,
                this.height / 2 - 110,
                this.width / 2 + 130,
                this.height / 2 + 100,
                0x88000000
        );

        // Marco
        graphics.renderOutline(
                this.width / 2 - 130,
                this.height / 2 - 110,
                260,
                210,
                0xFFFFFFFF
        );

        // Botones
        super.render(graphics, mouseX, mouseY, delta);

        // Título (AL FINAL)
        graphics.drawCenteredString(this.font, "PaperHeadHud", this.width / 2, this.height / 2 - 95, 0xFFFFFFFF);

        graphics.drawCenteredString(this.font, "Configuración", this.width / 2, this.height / 2 - 80, 0xFFFFFFFF);

        graphics.drawCenteredString(this.font, "Altura Y: " + PaperHeadHudConfig.offsetY, this.width / 2, this.height / 2 - 65, 0xFFFFFFFF);

        graphics.drawCenteredString(
                this.font,
                "v1.0 • by ElCezarx",
                this.width / 2,
                this.height / 2 + 80,
                0xFFAAAAAA
        );
    }

}