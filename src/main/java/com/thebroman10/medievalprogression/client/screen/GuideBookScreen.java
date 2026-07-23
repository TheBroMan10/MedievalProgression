package com.thebroman10.medievalprogression.client.screen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;


public class GuideBookScreen extends Screen {


    private static final Identifier BOOK_TEXTURE =
            Identifier.fromNamespaceAndPath(
                    "medievalprogression",
                    "textures/gui/guide_book/background.png"
            );


    private static final Identifier PREVIOUS =
            Identifier.fromNamespaceAndPath(
                    "medievalprogression",
                    "textures/gui/sprites/guide_book/previous.png"
            );


    private static final Identifier NEXT =
            Identifier.fromNamespaceAndPath(
                    "medievalprogression",
                    "textures/gui/sprites/guide_book/next.png"
            );


    private static final Identifier PREVIOUS_HOVER =
            Identifier.fromNamespaceAndPath(
                    "medievalprogression",
                    "textures/gui/sprites/guide_book/previous_hover.png"
            );


    private static final Identifier NEXT_HOVER =
            Identifier.fromNamespaceAndPath(
                    "medievalprogression",
                    "textures/gui/sprites/guide_book/next_hover.png"
            );



    private int page = 0;


    private static final int MAX_PAGE = 4;


    private static final int BOOK_WIDTH = 256;
    private static final int BOOK_HEIGHT = 256;


    private static final int BUTTON_SIZE = 16;



    public GuideBookScreen() {

        super(
                Component.literal("Medieval Progression Guide")
        );

    }





    private Identifier getPageTexture() {

        return Identifier.fromNamespaceAndPath(
                "medievalprogression",
                "textures/gui/guide_book/page" + page + ".png"
        );

    }





    private boolean isPreviousHovered(
            double mouseX,
            double mouseY,
            int bookX,
            int bookY
    ) {

        return mouseX >= bookX + 20
                && mouseX <= bookX + 20 + BUTTON_SIZE
                && mouseY >= bookY + 220
                && mouseY <= bookY + 220 + BUTTON_SIZE;

    }





    private boolean isNextHovered(
            double mouseX,
            double mouseY,
            int bookX,
            int bookY
    ) {

        return mouseX >= bookX + 220
                && mouseX <= bookX + 220 + BUTTON_SIZE
                && mouseY >= bookY + 220
                && mouseY <= bookY + 220 + BUTTON_SIZE;

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
                0x88000000
        );



        int bookX =
                (width - BOOK_WIDTH) / 2;


        int bookY =
                (height - BOOK_HEIGHT) / 2;



        // Main book background
        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                BOOK_TEXTURE,
                bookX,
                bookY,
                0,
                0,
                BOOK_WIDTH,
                BOOK_HEIGHT,
                BOOK_WIDTH,
                BOOK_HEIGHT
        );



        // Current page
        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                getPageTexture(),
                bookX,
                bookY,
                0,
                0,
                BOOK_WIDTH,
                BOOK_HEIGHT,
                BOOK_WIDTH,
                BOOK_HEIGHT
        );



        // Previous arrow
        if (page > 0) {

            graphics.blit(
                    RenderPipelines.GUI_TEXTURED,
                    isPreviousHovered(
                            mouseX,
                            mouseY,
                            bookX,
                            bookY
                    )
                            ? PREVIOUS_HOVER
                            : PREVIOUS,
                    bookX + 20,
                    bookY + 220,
                    0,
                    0,
                    BUTTON_SIZE,
                    BUTTON_SIZE,
                    BUTTON_SIZE,
                    BUTTON_SIZE
            );

        }



        // Next arrow
        if (page < MAX_PAGE) {

            graphics.blit(
                    RenderPipelines.GUI_TEXTURED,
                    isNextHovered(
                            mouseX,
                            mouseY,
                            bookX,
                            bookY
                    )
                            ? NEXT_HOVER
                            : NEXT,
                    bookX + 220,
                    bookY + 220,
                    0,
                    0,
                    BUTTON_SIZE,
                    BUTTON_SIZE,
                    BUTTON_SIZE,
                    BUTTON_SIZE
            );

        }



        // Page counter
        graphics.text(
                font,
                "Page " + (page + 1) + " / " + (MAX_PAGE + 1),
                bookX + 105,
                bookY + 235,
                0x000000,
                false
        );

    }





    @Override
    public boolean mouseClicked(
            MouseButtonEvent event,
            boolean doubleClick
    ) {


        int bookX =
                (width - BOOK_WIDTH) / 2;


        int bookY =
                (height - BOOK_HEIGHT) / 2;



        // Previous page

        if (
                page > 0 &&
                isPreviousHovered(
                        event.x(),
                        event.y(),
                        bookX,
                        bookY
                )
        ) {

            page--;

            return true;

        }



        // Next page

        if (
                page < MAX_PAGE &&
                isNextHovered(
                        event.x(),
                        event.y(),
                        bookX,
                        bookY
                )
        ) {

            page++;

            return true;

        }



        return super.mouseClicked(
                event,
                doubleClick
        );

    }





    @Override
    public boolean isPauseScreen() {

        return false;

    }

}