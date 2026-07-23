package com.thebroman10.medievalprogression.screen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;


public class AlloyFurnaceScreen extends AbstractContainerScreen<AlloyFurnaceMenu> {


    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath(
                    "medievalprogression",
                    "textures/gui/container/alloy_furnace.png"
            );


    private static final Identifier FLAME =
            Identifier.fromNamespaceAndPath(
                    "medievalprogression",
                    "textures/gui/sprites/alloy_furnace/lit_progress.png"
            );


    private static final Identifier ARROW =
            Identifier.fromNamespaceAndPath(
                    "medievalprogression",
                    "textures/gui/sprites/alloy_furnace/burn_progress.png"
            );



    public AlloyFurnaceScreen(
            AlloyFurnaceMenu menu,
            Inventory inventory,
            Component title
    ) {

        super(menu, inventory, title);

        this.titleLabelX = 56;
        this.titleLabelY = 6;
        this.inventoryLabelY = 72;
    }





    @Override
    public void extractBackground(
            GuiGraphicsExtractor graphics,
            int mouseX,
            int mouseY,
            float partialTick
    ) {


        // Main GUI
        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                TEXTURE,
                this.leftPos,
                this.topPos,
                0,
                0,
                this.imageWidth,
                this.imageHeight,
                256,
                256
        );




        // Flame below fuel slot
        if(menu.isBurning()) {


            int flameHeight = menu.getScaledBurnTime(14);


            graphics.blit(
                    RenderPipelines.GUI_TEXTURED,
                    FLAME,
                    this.leftPos + 20,
                    this.topPos + 53 + (14 - flameHeight),
                    0,
                    14 - flameHeight,
                    14,
                    flameHeight,
                    14,
                    14
            );
        }





        // Progress arrow
        int progress = menu.getScaledProgress(24);


        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                ARROW,
                this.leftPos + 79,
                this.topPos + 34,
                0,
                0,
                progress,
                17,
                24,
                17
        );
    }
}