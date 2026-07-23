package com.thebroman10.medievalprogression.loot;

import com.thebroman10.medievalprogression.item.ModItems;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;

import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public class LootTablesRosegoldNautilusArmor {

    public static void register() {

        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {

            Identifier id = key.identifier();


            // Structures where Diamond Nautilus Armor generates
            if (!id.equals(
                    Identifier.fromNamespaceAndPath(
                            "minecraft",
                            "chests/buried_treasure"
                    )
            )
            &&
            !id.equals(
                    Identifier.fromNamespaceAndPath(
                            "minecraft",
                            "chests/shipwreck_supply"
                    )
            )
            &&
            !id.equals(
                    Identifier.fromNamespaceAndPath(
                            "minecraft",
                            "chests/shipwreck_treasure"
                    )
            )
            &&
            !id.equals(
                    Identifier.fromNamespaceAndPath(
                            "minecraft",
                            "chests/ocean_ruin_small"
                    )
            )
            &&
            !id.equals(
                    Identifier.fromNamespaceAndPath(
                            "minecraft",
                            "chests/ocean_ruin_big"
                    )
            )) {
                return;
            }


            LootPool.Builder pool = LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1));


            // Rosegold Nautilus Armor
            pool.add(
                    LootItem.lootTableItem(
                            ModItems.ROSEGOLD_NAUTILUS_ARMOR
                    )
                    .setWeight(1)
            );


            // Empty chance to keep it rare
            pool.add(
                    EmptyLootItem.emptyItem()
                            .setWeight(24)
            );


            tableBuilder.withPool(pool);

        });
    }
}