package com.thebroman10.medievalprogression.screen;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.FuelValues;


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

        return fuelValues.isFuel(stack);

    }

}