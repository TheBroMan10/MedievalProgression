package com.thebroman10.medievalprogression;

import com.thebroman10.medievalprogression.client.MapScreen;
import com.thebroman10.medievalprogression.client.MinimapRenderer;
import com.thebroman10.medievalprogression.client.ModKeybinds;
import com.thebroman10.medievalprogression.screen.AlloyFurnaceScreen;
import com.thebroman10.medievalprogression.screen.ModMenuTypes;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.Identifier;

public class MedievalProgressionClient implements ClientModInitializer {

    private static final Identifier MAP_TEXTURE =
            Identifier.fromNamespaceAndPath(
                    "medievalprogression",
                    "textures/map/world_map.png"
            );

    private boolean mapPreloaded = false;

    @Override
    public void onInitializeClient() {

        MenuScreens.register(
                ModMenuTypes.ALLOY_FURNACE,
                AlloyFurnaceScreen::new
        );

        HudElementRegistry.addLast(
                Identifier.fromNamespaceAndPath(
                        "medievalprogression",
                        "minimap"
                ),
                MinimapRenderer::render
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            if (!mapPreloaded && client.level != null) {

                client.getTextureManager()
                        .getTexture(MAP_TEXTURE);

                mapPreloaded = true;

                System.out.println(
                        "Preloaded world map texture."
                );
            }


            while (ModKeybinds.OPEN_MAP.consumeClick()) {

                client.setScreen(
                        new MapScreen()
                );

            }

        });

    }

}