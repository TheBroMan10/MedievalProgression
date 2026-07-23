package com.thebroman10.medievalprogression;

import com.thebroman10.medievalprogression.screen.ModMenuTypes;
import com.thebroman10.medievalprogression.item.ModCreativeTabs;
import com.thebroman10.medievalprogression.item.ModItems;
import com.thebroman10.medievalprogression.block.ModBlocks;
import com.thebroman10.medievalprogression.block.entity.ModBlockEntities;

import net.fabricmc.api.ModInitializer;


public class MedievalProgression implements ModInitializer {

    public static final String MOD_ID = "medievalprogression";


    @Override
    public void onInitialize() {

        ModBlocks.initialize();

        ModItems.initialize();

        ModBlockEntities.initialize();
        
        ModMenuTypes.initialize();
        
        ModCreativeTabs.register();

        System.out.println("Medieval Progression loaded!");

    }
}