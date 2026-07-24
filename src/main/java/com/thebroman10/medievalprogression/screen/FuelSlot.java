package com.thebroman10.medievalprogression.screen;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.FuelValues;
import net.minecraft.world.item.Items;

public class FuelSlot extends Slot {


    private final FuelValues fuelValues;


    public FuelSlot(
            Container container,
            int slot,
            int x,
            int y,
            FuelValues fuelValues
    ) {

        super(container, slot, x, y);

        this.fuelValues = fuelValues;

    }



    @Override
    public boolean mayPlace(ItemStack stack) {
        return (stack.is(Items.COAL) || stack.is(Items.CHARCOAL) || stack.is(Items.LAVA_BUCKET) || stack.is(Items.COAL_BLOCK) || stack.is(Items.BLAZE_POWDER));
    }

}
