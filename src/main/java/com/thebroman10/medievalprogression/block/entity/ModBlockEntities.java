package com.thebroman10.medievalprogression.block.entity;

import com.thebroman10.medievalprogression.MedievalProgression;
import com.thebroman10.medievalprogression.block.ModBlocks;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.BlockEntityType;

import net.minecraft.core.Registry;

public class ModBlockEntities {

    public static BlockEntityType<AlloyFurnaceBlockEntity> ALLOY_FURNACE;


    public static void initialize() {

        ALLOY_FURNACE = Registry.register(
                BuiltInRegistries.BLOCK_ENTITY_TYPE,
                Identifier.fromNamespaceAndPath(
                        MedievalProgression.MOD_ID,
                        "alloy_furnace"
                ),
                FabricBlockEntityTypeBuilder.create(
                        AlloyFurnaceBlockEntity::new,
                        ModBlocks.ALLOY_FURNACE
                ).build()
        );

    }
}