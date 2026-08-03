package com.thebroman10.medievalprogression.block.entity;

import com.thebroman10.medievalprogression.block.AlloyFurnaceBlock;
import com.thebroman10.medievalprogression.recipe.AlloyRecipe;
import com.thebroman10.medievalprogression.recipe.AlloyRecipes;
import com.thebroman10.medievalprogression.screen.AlloyFurnaceMenu;

import net.minecraft.core.Direction;
import net.minecraft.world.WorldlyContainer;
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

public class AlloyFurnaceBlockEntity extends BlockEntity implements WorldlyContainer, MenuProvider {

    private final NonNullList<ItemStack> items =
            NonNullList.withSize(5, ItemStack.EMPTY);

    private int burnTime;
    private int maxBurnTime;
    private int cookTime;

    private static final int[] INPUT_SLOTS = {
            0,
            1
    };

    private static final int[] FUEL_SLOT = {
            2
    };

    private static final int[] OUTPUT_SLOTS = {
            3,
            4
    };

    public AlloyFurnaceBlockEntity(
            BlockPos pos,
            BlockState state
    ) {
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

                        furnace.maxBurnTime =
                                getFuelTime(fuel);

                        furnace.burnTime =
                                furnace.maxBurnTime;

                        fuel.shrink(1);

                        dirty = true;
                    }
                }


                if (furnace.burnTime > 0) {

                    furnace.cookTime++;

                    AlloyRecipe recipe =
                            AlloyRecipes.getRecipe(
                                    furnace.items.get(0),
                                    furnace.items.get(1)
                            );

                    if (recipe != null
                            && furnace.cookTime >= recipe.getCookTime()) {

                        furnace.cookTime = 0;
                        furnace.createAlloy();
                        dirty = true;
                    }

                } else {

                    furnace.cookTime = 0;
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
            
            
                // Burning sounds
                if (furnace.burnTime > 0
                        && level.random.nextInt(20) == 0) {
                
                    level.playSound(
                            null,
                            pos,
                            net.minecraft.sounds.SoundEvents.BLASTFURNACE_FIRE_CRACKLE,
                            net.minecraft.sounds.SoundSource.BLOCKS,
                            1.0f,
                            1.0f
                    );
                
                    level.playSound(
                            null,
                            pos,
                            net.minecraft.sounds.SoundEvents.LAVA_AMBIENT,
                            net.minecraft.sounds.SoundSource.BLOCKS,
                            0.6f,
                            1.0f
                    );
                }
            
            
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


        if (!canOutput(items.get(3), primary)
                && !canOutput(items.get(4), primary)) {

            return false;
        }


        if (recipe.hasSecondaryResult()
                && !canOutput(items.get(4), secondary)) {

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



        if (canOutput(items.get(3), primary)) {

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
            return 800;
        }

        if (stack.is(Items.CHARCOAL)) {
            return 800;
        }

        if (stack.is(Items.LAVA_BUCKET)) {
            return 12800;
        }

        if (stack.is(Items.COAL_BLOCK)) {
            return 8000;
        }

        if (stack.is(Items.BLAZE_POWDER)) {
            return 6400;
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
                    case 2 -> maxBurnTime;

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
                    case 2 -> maxBurnTime = value;

                }
            }


            @Override
            public int getCount() {
                return 3;
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
                "MaxBurnTime",
                maxBurnTime
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


        maxBurnTime =
                input.getIntOr(
                        "MaxBurnTime",
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
    public ItemStack removeItemNoUpdate(int slot) {

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
    
    
    @Override
    public int[] getSlotsForFace(Direction side) {

        if (side == Direction.DOWN) {

            return OUTPUT_SLOTS;

        }

        if (side == Direction.UP) {

            return FUEL_SLOT;

        }

        return INPUT_SLOTS;
    }


    @Override
    public boolean canPlaceItemThroughFace(
            int slot,
            ItemStack stack,
            Direction side
    ) {

        // Top = fuel
        if (side == Direction.UP) {

            return slot == 2
                    && getFuelTime(stack) > 0;

        }


        // Sides = inputs
        if (side != Direction.DOWN) {

            return slot == 0
                    || slot == 1;

        }


        // Bottom cannot insert
        return false;
    }


    @Override
    public boolean canTakeItemThroughFace(
            int slot,
            ItemStack stack,
            Direction side
    ) {

        return side == Direction.DOWN
                && (slot == 3 || slot == 4);
    }
}
