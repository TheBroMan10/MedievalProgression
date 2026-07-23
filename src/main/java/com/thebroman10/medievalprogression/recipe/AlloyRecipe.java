package com.thebroman10.medievalprogression.recipe;

import net.minecraft.world.item.ItemStack;

public class AlloyRecipe {

    private final ItemStack input1;
    private final ItemStack input2;

    private final ItemStack result;
    private final int resultChance;

    private final ItemStack secondaryResult;
    private final int secondaryChance;


    public AlloyRecipe(
            ItemStack input1,
            ItemStack input2,
            ItemStack result
    ) {

        this(
                input1,
                input2,
                result,
                100,
                ItemStack.EMPTY,
                0
        );

    }


    public AlloyRecipe(
            ItemStack input1,
            ItemStack input2,
            ItemStack result,
            int resultChance,
            ItemStack secondaryResult,
            int secondaryChance
    ) {

        this.input1 = input1;
        this.input2 = input2;

        this.result = result;
        this.resultChance = resultChance;

        this.secondaryResult = secondaryResult;
        this.secondaryChance = secondaryChance;

    }


    public boolean matches(
            ItemStack first,
            ItemStack second
    ) {

        return (
                ItemStack.isSameItem(first, input1)
                && ItemStack.isSameItem(second, input2)

        ) || (

                ItemStack.isSameItem(first, input2)
                && ItemStack.isSameItem(second, input1)

        );

    }


    public ItemStack getResult() {

        return result.copy();

    }


    public ItemStack getSecondaryResult() {

        return secondaryResult.copy();

    }


    public boolean hasSecondaryResult() {

        return !secondaryResult.isEmpty();

    }


    public boolean shouldGivePrimary() {

        return Math.random() * 100 < resultChance;

    }


    public boolean shouldGiveSecondary() {

        return hasSecondaryResult()
                && Math.random() * 100 < secondaryChance;

    }


    // Used by AlloyFurnaceBlockEntity
    public boolean shouldCreatePrimary() {

        return shouldGivePrimary();

    }


    // Used by AlloyFurnaceBlockEntity
    public boolean shouldCreateSecondary() {

        return shouldGiveSecondary();

    }

}