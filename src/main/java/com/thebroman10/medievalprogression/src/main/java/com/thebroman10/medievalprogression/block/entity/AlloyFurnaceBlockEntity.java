package com.thebroman10.medievalprogression.block.entity;

import com.thebroman10.medievalprogression.block.AlloyFurnaceBlock;
import com.thebroman10.medievalprogression.recipe.AlloyRecipe;
import com.thebroman10.medievalprogression.recipe.AlloyRecipes;
import com.thebroman10.medievalprogression.screen.AlloyFurnaceMenu;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AlloyFurnaceBlockEntity extends BlockEntity implements Container, MenuProvider {

    private final NonNullList<ItemStack> items =
            NonNullList.withSize(5, ItemStack.EMPTY);

    private int burnTime;
    private int cookTime;

    private static final int MAX_COOK_TIME = 200;

    public AlloyFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(
                ModBlockEntities.ALLOY_FURNACE,
                pos,
                state
        );
    }

    public static void tick(
            Level level,
            BlockPos pos,
            BlockState state,
            AlloyFurnaceBlockEntity furnace
    ) {

        boolean dirty = false;

        if (furnace.burnTime > 0) {
            furnace.burnTime--;
            dirty = true;
        }

        if (!level.isClientSide()) {

            if (furnace.canProcess()) {

                if (furnace.burnTime <= 0) {

                    ItemStack fuel = furnace.items.get(2);

                    if (!fuel.isEmpty()) {

                        furnace.burnTime = getFuelTime(fuel);
                        fuel.shrink(1);
                        dirty = true;

                    }
                }

                if (furnace.burnTime > 0) {

                    furnace.cookTime++;

                    if (furnace.cookTime >= MAX_COOK_TIME) {

                        furnace.cookTime = 0;
                        furnace.createAlloy();
                        dirty = true;

                    }
                }

            } else {

                furnace.cookTime = 0;

            }

            boolean lit = furnace.burnTime > 0;

            if (state.getValue(AlloyFurnaceBlock.LIT) != lit) {

                level.setBlock(
                        pos,
                        state.setValue(
                                AlloyFurnaceBlock.LIT,
                                lit
                        ),
                        3
                );

                dirty = true;

            }

            if (dirty) {
                furnace.setChanged();
            }
        }
    }

    private boolean canProcess() {

        if (items.get(0).isEmpty()
                || items.get(1).isEmpty()) {

            return false;
        }


        AlloyRecipe recipe =
                AlloyRecipes.getRecipe(
                        items.get(0),
                        items.get(1)
                );


        if (recipe == null) {
            return false;
        }


        ItemStack primary =
                recipe.getResult();

        ItemStack secondary =
                recipe.getSecondaryResult();


        // Primary output can go into slot 3 or overflow slot 4
        if (!canOutput(
                items.get(3),
                primary
        )
        && !canOutput(
                items.get(4),
                primary
        )) {

            return false;
        }


        // Secondary output only uses slot 4
        if (recipe.hasSecondaryResult()
                && !canOutput(
                        items.get(4),
                        secondary
                )) {

            return false;
        }


        return true;
    }

    private boolean canOutput(
            ItemStack output,
            ItemStack result
    ) {

        if (result.isEmpty()) {
            return true;
        }

        if (output.isEmpty()) {
            return true;
        }

        if (!ItemStack.isSameItem(output, result)) {
            return false;
        }

        return output.getCount()
                + result.getCount()
                <= output.getMaxStackSize();
    }

    private void createAlloy() {

        AlloyRecipe recipe =
                AlloyRecipes.getRecipe(
                        items.get(0),
                        items.get(1)
                );


        if (recipe == null) {
            return;
        }


        ItemStack primary =
                recipe.getResult();


        ItemStack secondary =
                recipe.getSecondaryResult();



        // Primary output always happens
        if (canOutput(
                items.get(3),
                primary
        )) {

            addOutput(
                    3,
                    primary
            );

        } else {

            addOutput(
                    4,
                    primary
            );

        }



        // Secondary output uses chance
        if (recipe.shouldCreateSecondary()) {

            addOutput(
                    4,
                    secondary
            );

        }



        items.get(0).shrink(1);
        items.get(1).shrink(1);

    }

    private void addOutput(
            int slot,
            ItemStack result
    ) {

        if (result.isEmpty()) {
            return;
        }

        if (items.get(slot).isEmpty()) {

            items.set(
                    slot,
                    result.copy()
            );

        } else {

            items.get(slot)
                    .grow(
                            result.getCount()
                    );
        }
    }

    private static int getFuelTime(ItemStack stack) {

        if (stack.is(Items.COAL)) {
            return 1600;
        }

        if (stack.is(Items.CHARCOAL)) {
            return 1600;
        }

        return 0;
    }
    public ContainerData getData() {

        return new ContainerData() {

            @Override
            public int get(int index) {

                return switch (index) {

                    case 0 -> burnTime;
                    case 1 -> cookTime;

                    default -> 0;
                };
            }

            @Override
            public void set(
                    int index,
                    int value
            ) {

                switch (index) {

                    case 0 -> burnTime = value;
                    case 1 -> cookTime = value;

                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    protected void saveAdditional(
            net.minecraft.world.level.storage.ValueOutput output
    ) {

        super.saveAdditional(output);

        ContainerHelper.saveAllItems(
                output,
                items
        );

        output.putInt(
                "BurnTime",
                burnTime
        );

        output.putInt(
                "CookTime",
                cookTime
        );
    }

    @Override
    protected void loadAdditional(
            net.minecraft.world.level.storage.ValueInput input
    ) {

        super.loadAdditional(input);

        ContainerHelper.loadAllItems(
                input,
                items
        );

        burnTime =
                input.getIntOr(
                        "BurnTime",
                        0
                );

        cookTime =
                input.getIntOr(
                        "CookTime",
                        0
                );
    }

    public int getBurnTime() {
        return burnTime;
    }

    public int getCookTime() {
        return cookTime;
    }

    @Override
    public int getContainerSize() {
        return 5;
    }

    @Override
    public boolean isEmpty() {

        for (ItemStack stack : items) {

            if (!stack.isEmpty()) {
                return false;
            }

        }

        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        return items.get(slot);
    }

    @Override
    public ItemStack removeItem(
            int slot,
            int amount
    ) {

        return ContainerHelper.removeItem(
                items,
                slot,
                amount
        );
    }

    @Override
    public ItemStack removeItemNoUpdate(
            int slot
    ) {

        ItemStack stack = items.get(slot);

        items.set(
                slot,
                ItemStack.EMPTY
        );

        return stack;
    }

    @Override
    public void setItem(
            int slot,
            ItemStack stack
    ) {

        items.set(
                slot,
                stack
        );

        setChanged();
    }

    @Override
    public boolean stillValid(
            net.minecraft.world.entity.player.Player player
    ) {

        return true;
    }

    @Override
    public void clearContent() {
        items.clear();
    }

    @Override
    public Component getDisplayName() {

        return Component.literal(
                "Alloy Furnace"
        );
    }

    @Override
    public AbstractContainerMenu createMenu(
            int id,
            Inventory inventory,
            net.minecraft.world.entity.player.Player player
    ) {

        return new AlloyFurnaceMenu(
                id,
                inventory,
                this
        );
    }
}