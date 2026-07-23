package com.thebroman10.medievalprogression.block;

import com.thebroman10.medievalprogression.MedievalProgression;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

public class ModBlocks {

    public static final Block ROSEGOLD_BLOCK = register(
            "rosegold_block",
            Block::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK)
    );
    
    public static final Block SMOOTH_DEEPSLATE = register(
            "smooth_deepslate",
            Block::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DEEPSLATE)
    );


    public static final Block ALLOY_FURNACE = register(
            "alloy_furnace",
            AlloyFurnaceBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.BLAST_FURNACE)
    );


    private static Block register(
            String name,
            Function<BlockBehaviour.Properties, Block> factory,
            BlockBehaviour.Properties properties
    ) {

        Identifier id = Identifier.fromNamespaceAndPath(
                MedievalProgression.MOD_ID,
                name
        );

        ResourceKey<Block> key = ResourceKey.create(
                net.minecraft.core.registries.Registries.BLOCK,
                id
        );

        properties.setId(key);

        return Registry.register(
                BuiltInRegistries.BLOCK,
                id,
                factory.apply(properties)
        );
    }


    public static void initialize() {
    }
}