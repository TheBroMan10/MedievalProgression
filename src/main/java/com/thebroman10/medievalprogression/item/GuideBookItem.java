package com.thebroman10.medievalprogression.item;

import com.thebroman10.medievalprogression.client.screen.GuideBookScreen;

import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class GuideBookItem extends Item {

    public GuideBookItem(Properties properties) {
        super(properties);
    }


    @Override
    public InteractionResult use(
            Level level,
            Player player,
            InteractionHand hand
    ) {

        if (level.isClientSide()) {

            Minecraft.getInstance().setScreen(
                    new GuideBookScreen()
            );

        }

        return InteractionResult.SUCCESS;
    }
}