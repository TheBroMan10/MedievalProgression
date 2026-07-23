package com.thebroman10.medievalprogression.client;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;


public class MapScreen extends Screen {

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


    private static final int MAP_SIZE = 12315;


    private static final int MAP_ORIGIN_X = -3075;
    private static final int MAP_ORIGIN_Z = -9250;



    private double zoom = 0.1;


    private double mapX;
    private double mapZ;


    private boolean initialized = false;


    private boolean dragging = false;


    private double lastMouseX;
    private double lastMouseY;



    public MapScreen() {

        super(
                Component.literal("World Map")
        );

    }





    @Override
    public void extractRenderState(
            GuiGraphicsExtractor graphics,
            int mouseX,
            int mouseY,
            float partialTick
    ) {

        super.extractRenderState(
                graphics,
                mouseX,
                mouseY,
                partialTick
        );



        graphics.fill(
                0,
                0,
                width,
                height,
                0xFF000000
        );



        if (minecraft.player == null) {
            return;
        }



        if (!initialized) {

            mapX =
                    minecraft.player.getX()
                    - MAP_ORIGIN_X;


            mapZ =
                    minecraft.player.getZ()
                    - MAP_ORIGIN_Z;


            initialized = true;

        }





        int viewSize =
                (int)(MAP_SIZE / zoom);



        int sourceX =
                (int)(mapX - viewSize / 2);


        int sourceZ =
                (int)(mapZ - viewSize / 2);



        int drawSize =
                (int)(viewSize * zoom);



        int drawX =
                (width - drawSize) / 2;


        int drawZ =
                (height - drawSize) / 2;





        int clippedX =
                Math.max(
                        sourceX,
                        0
                );


        int clippedZ =
                Math.max(
                        sourceZ,
                        0
                );



        int sourceWidth =
                Math.min(
                        viewSize - (clippedX - sourceX),
                        MAP_SIZE - clippedX
                );


        int sourceHeight =
                Math.min(
                        viewSize - (clippedZ - sourceZ),
                        MAP_SIZE - clippedZ
                );



        if (sourceWidth > 0 && sourceHeight > 0) {


            int destinationX =
                    drawX
                    + (int)(
                            (clippedX - sourceX)
                            * zoom
                    );


            int destinationZ =
                    drawZ
                    + (int)(
                            (clippedZ - sourceZ)
                            * zoom
                    );



            graphics.blit(
                    RenderPipelines.GUI_TEXTURED,
                    MAP,
                    destinationX,
                    destinationZ,
                    clippedX,
                    clippedZ,
                    (int)(sourceWidth * zoom),
                    (int)(sourceHeight * zoom),
                    sourceWidth,
                    sourceHeight,
                    MAP_SIZE,
                    MAP_SIZE
            );

        }





        double playerMapX =
                minecraft.player.getX()
                - MAP_ORIGIN_X;


        double playerMapZ =
                minecraft.player.getZ()
                - MAP_ORIGIN_Z;



        double relativeX =
                playerMapX - mapX;


        double relativeZ =
                playerMapZ - mapZ;



        int screenX =
                (int)(
                        width / 2
                        + relativeX * zoom
                );


        int screenZ =
                (int)(
                        height / 2
                        + relativeZ * zoom
                );



        int iconSize =
                (int)(16 * (zoom + 1));





        graphics.pose().pushMatrix();



        graphics.pose().translate(
                screenX,
                screenZ
        );



        graphics.pose().rotate(
                (float)Math.toRadians(
                        minecraft.player.getYRot()
                )
        );



        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                PLAYER,
                -iconSize / 2,
                -iconSize / 2,
                0,
                0,
                iconSize,
                iconSize,
                32,
                32,
                32,
                32
        );



        graphics.pose().popMatrix();

    }





    @Override
    public boolean mouseClicked(
            MouseButtonEvent event,
            boolean doubleClick
    ) {

        if (event.button() == 0) {

            dragging = true;


            lastMouseX =
                    minecraft.mouseHandler.xpos();


            lastMouseY =
                    minecraft.mouseHandler.ypos();


            return true;

        }


        return false;

    }





    @Override
    public boolean mouseDragged(
            MouseButtonEvent event,
            double mouseX,
            double mouseY
    ) {

        if (!dragging) {
            return false;
        }



        double currentMouseX =
                minecraft.mouseHandler.xpos();


        double currentMouseY =
                minecraft.mouseHandler.ypos();



        double guiScale =
                minecraft.getWindow().getGuiScale();



        double deltaX =
                (currentMouseX - lastMouseX)
                / guiScale;


        double deltaZ =
                (currentMouseY - lastMouseY)
                / guiScale;



        mapX -= deltaX / zoom;

        mapZ -= deltaZ / zoom;



        lastMouseX = currentMouseX;

        lastMouseY = currentMouseY;



        return true;

    }





    @Override
    public boolean mouseReleased(
            MouseButtonEvent event
    ) {

        if (event.button() == 0) {

            dragging = false;

            return true;

        }


        return false;

    }





    @Override
    public boolean mouseScrolled(
            double mouseX,
            double mouseY,
            double scrollX,
            double scrollY
    ) {

        double oldZoom = zoom;



        if (scrollY > 0) {

            zoom *= 1.15;

        }


        if (scrollY < 0) {

            zoom /= 1.15;

        }



        zoom = Math.max(
                0.025,
                Math.min(
                        zoom,
                        0.25
                )
        );



        if (oldZoom != zoom) {


            double beforeX =
                    mapX
                    + (mouseX - width / 2)
                    / oldZoom;


            double beforeZ =
                    mapZ
                    + (mouseY - height / 2)
                    / oldZoom;



            double afterX =
                    mapX
                    + (mouseX - width / 2)
                    / zoom;


            double afterZ =
                    mapZ
                    + (mouseY - height / 2)
                    / zoom;



            mapX += beforeX - afterX;

            mapZ += beforeZ - afterZ;

        }



        return true;

    }





    @Override
    public boolean isPauseScreen() {

        return false;

    }

}