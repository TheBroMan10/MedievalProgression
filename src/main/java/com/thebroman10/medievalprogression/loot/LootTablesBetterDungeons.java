package com.thebroman10.medievalprogression.loot;

import com.thebroman10.medievalprogression.item.ModItems;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class LootTablesBetterDungeons {

    public static void register() {

        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {

            Identifier id = key.identifier();

            // Only Better Dungeons loot tables
            if (!id.getNamespace().equals("betterdungeons")) {
                return;
            }

            LootPool.Builder pool = LootPool.lootPool()
                    // The pool rolls 2-4 times per chest
                    .setRolls(
                            UniformGenerator.between(2, 4)
                    );


            // Diamond upgrade template
            pool.add(
                    LootItem.lootTableItem(
                            ModItems.DIAMOND_UPGRADE
                    )
                    .setWeight(1)
            );


            // Diamond upgrade template
            pool.add(
                    LootItem.lootTableItem(
                            ModItems.ROSEGOLD_HORSE_ARMOR
                    )
                    .setWeight(1)
            );


            // Biomes O' Plenty rose quartz chunks
            BuiltInRegistries.ITEM.get(
                    Identifier.fromNamespaceAndPath(
                            "biomesoplenty",
                            "rose_quartz_chunk"
                    )
            ).ifPresent(itemReference -> {

                pool.add(
                        LootItem.lootTableItem(
                                itemReference.value()
                        )
                        .setWeight(8)
                        .apply(
                                net.minecraft.world.level.storage.loot.functions.SetItemCountFunction.setCount(
                                        UniformGenerator.between(2, 8)
                                )
                        )
                );

            });


            // Empty chance
            pool.add(
                    EmptyLootItem.emptyItem()
                            .setWeight(10)
            );


            tableBuilder.withPool(pool);

        });
    }
}