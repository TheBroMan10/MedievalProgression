package com.thebroman10.medievalprogression.screen;

import com.thebroman10.medievalprogression.block.entity.AlloyFurnaceBlockEntity;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.FuelValues;

public class AlloyFurnaceMenu extends AbstractContainerMenu {

    private final Container container;
    private final ContainerData data;

    public AlloyFurnaceMenu(
            int id,
            Inventory inventory
    ) {
        this(id, inventory, null);
    }

    public AlloyFurnaceMenu(
            int id,
            Inventory inventory,
            AlloyFurnaceBlockEntity blockEntity
    ) {

        super(
                ModMenuTypes.ALLOY_FURNACE,
                id
        );

        if (blockEntity != null) {

            this.container = blockEntity;
            this.data = blockEntity.getData();

        } else {

            this.container = new SimpleContainer(5);
            this.data = new SimpleContainerData(2);

        }

        FuelValues fuelValues =
                inventory.player
                        .level()
                        .fuelValues();

        // Input 1
        addSlot(new Slot(
                container,
                0,
                56,
                17
        ));

        // Input 2
        addSlot(new Slot(
                container,
                1,
                56,
                53
        ));

        // Fuel
        addSlot(new FuelSlot(
                container,
                2,
                20,
                35,
                fuelValues
        ));

        // Primary Output
        addSlot(new OutputSlot(
                container,
                3,
                116,
                26
        ));

        // Secondary Output
        addSlot(new OutputSlot(
                container,
                4,
                116,
                44
        ));

        // Player inventory
        for (int y = 0; y < 3; y++) {

            for (int x = 0; x < 9; x++) {

                addSlot(new Slot(
                        inventory,
                        x + y * 9 + 9,
                        8 + x * 18,
                        84 + y * 18
                ));

            }

        }

        // Hotbar
        for (int x = 0; x < 9; x++) {

            addSlot(new Slot(
                    inventory,
                    x,
                    8 + x * 18,
                    142
            ));

        }

        addDataSlots(data);
    }

    @Override
    public ItemStack quickMoveStack(
            Player player,
            int index
    ) {

        ItemStack stack = ItemStack.EMPTY;

        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {

            ItemStack original = slot.getItem();

            stack = original.copy();

            // Furnace -> Player
            if (index < 5) {

                if (!moveItemStackTo(
                        original,
                        5,
                        this.slots.size(),
                        true
                )) {

                    return ItemStack.EMPTY;

                }

            }

            // Player -> Furnace
            else {

                // Fuel
                if (!moveItemStackTo(
                        original,
                        2,
                        3,
                        false
                )) {

                    // Inputs
                    if (!moveItemStackTo(
                            original,
                            0,
                            2,
                            false
                    )) {

                        return ItemStack.EMPTY;

                    }

                }

            }

            if (original.getCount() == stack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.setByPlayer(original);

        }

        return stack;
    }

    @Override
    public boolean stillValid(Player player) {
        return container.stillValid(player);
    }

    public boolean isBurning() {
        return data.get(0) > 0;
    }

    public int getScaledBurnTime(int pixels) {
        return data.get(0) * pixels / 1600;
    }

    public int getScaledProgress(int pixels) {
        return data.get(1) * pixels / 200;
    }
}