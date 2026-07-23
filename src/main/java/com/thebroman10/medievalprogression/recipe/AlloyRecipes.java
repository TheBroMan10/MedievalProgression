package com.thebroman10.medievalprogression.recipe;

import com.thebroman10.medievalprogression.item.ModItems;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;

public class AlloyRecipes {

    private static final Item BOP_ROSE_QUARTZ_CHUNK =
            BuiltInRegistries.ITEM
                    .getOptional(
                            Identifier.fromNamespaceAndPath(
                                    "biomesoplenty",
                                    "rose_quartz_chunk"
                            )
                    )
                    .orElse(Items.AIR);


    public static final List<AlloyRecipe> RECIPES = List.of(

            new AlloyRecipe(
                    new ItemStack(Items.GOLD_INGOT),
                    new ItemStack(Items.NETHERITE_SCRAP),
                    new ItemStack(ModItems.NETHERITE_NUGGET, 2),
                    100,
                    new ItemStack(ModItems.NETHERITE_NUGGET),
                    33
            ),


            // Biomes O' Plenty Rose Quartz -> Rose Gold
            new AlloyRecipe(
                    new ItemStack(Items.GOLD_INGOT),
                    new ItemStack(BOP_ROSE_QUARTZ_CHUNK),
                    new ItemStack(ModItems.ROSEGOLD_INGOT, 2),
                    100,
                    ItemStack.EMPTY,
                    0
            )

    );


    public static AlloyRecipe getRecipe(
            ItemStack first,
            ItemStack second
    ) {

        for (AlloyRecipe recipe : RECIPES) {

            if (recipe.matches(first, second)) {
                return recipe;
            }

        }

        return null;
    }

}