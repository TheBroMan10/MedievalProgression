package com.thebroman10.medievalprogression.client;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;


public class MinimapRenderer {

    private static final Identifier MAP =
            Identifier.fromNamespaceAndPath(
                    "medievalprogression",
                    "textures/map/world_map.png"
            );


    private static final Identifier PLAYER =
            Identifier.fromNamespaceAndPath(
                    "medievalprogression",
                    "textures/gui/sprites/player_marker.png"
            );


    private static final Identifier BORDER =
            Identifier.fromNamespaceAndPath(
                    "medievalprogression",
                    "textures/gui/minimap_border.png"
            );


    private static final int MAP_SIZE = 12315;


    private static final int MAP_ORIGIN_X = -3075;
    private static final int MAP_ORIGIN_Z = -9250;



    public static void render(
            GuiGraphicsExtractor graphics,
            DeltaTracker deltaTracker
    ) {

        Minecraft minecraft =
                Minecraft.getInstance();


        if (minecraft.player == null)
            return;



        ModConfig config =
                ModConfig.get();



        int size =
                config.minimapSize;


        double zoom =
                config.minimapZoom;



        int x;


        if (config.minimapSide.equalsIgnoreCase("left")) {

            x = 10;

        } else {

            x =
                    minecraft.getWindow()
                            .getGuiScaledWidth()
                    - size
                    - 10;

        }


        int y = 10;



        // Black background outside map

        graphics.fill(
                x,
                y,
                x + size,
                y + size,
                0xFF000000
        );



        double playerX =
                minecraft.player.getX()
                - MAP_ORIGIN_X;


        double playerZ =
                minecraft.player.getZ()
                - MAP_ORIGIN_Z;



        int sourceSize =
                (int)(size / zoom);



        int sourceX =
                (int)(playerX - sourceSize / 2);


        int sourceZ =
                (int)(playerZ - sourceSize / 2);



        int sourceEndX =
                sourceX + sourceSize;


        int sourceEndZ =
                sourceZ + sourceSize;



        int clippedX =
                Math.max(
                        0,
                        sourceX
                );


        int clippedZ =
                Math.max(
                        0,
                        sourceZ
                );


        int clippedEndX =
                Math.min(
                        MAP_SIZE,
                        sourceEndX
                );


        int clippedEndZ =
                Math.min(
                        MAP_SIZE,
                        sourceEndZ
                );



        int width =
                clippedEndX - clippedX;


        int height =
                clippedEndZ - clippedZ;



        if (width > 0 && height > 0) {


            int drawX =
                    x +
                    (int)(
                            (clippedX - sourceX)
                            * zoom
                    );


            int drawY =
                    y +
                    (int)(
                            (clippedZ - sourceZ)
                            * zoom
                    );



            graphics.blit(
                    RenderPipelines.GUI_TEXTURED,
                    MAP,
                    drawX,
                    drawY,
                    clippedX,
                    clippedZ,
                    (int)(width * zoom),
                    (int)(height * zoom),
                    width,
                    height,
                    MAP_SIZE,
                    MAP_SIZE
            );

        }



        // Player marker

        int markerSize =
                12;



        graphics.pose().pushMatrix();


        graphics.pose().translate(
                x + size / 2,
                y + size / 2
        );


        graphics.pose().rotate(
                (float)Math.toRadians(
                        minecraft.player.getYRot()
                )
        );


        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                PLAYER,
                -markerSize / 2,
                -markerSize / 2,
                0,
                0,
                markerSize,
                markerSize,
                32,
                32,
                32,
                32
        );


        graphics.pose().popMatrix();



        // PNG border overlay

        if (config.minimapBorder) {

            graphics.blit(
                    RenderPipelines.GUI_TEXTURED,
                    BORDER,
                    x,
                    y,
                    0,
                    0,
                    size,
                    size,
                    128,
                    128,
                    128,
                    128
            );

        }

    }

}