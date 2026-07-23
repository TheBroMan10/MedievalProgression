package com.thebroman10.medievalprogression.item;

import com.thebroman10.medievalprogression.MedievalProgression;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeTabs {

    public static final ResourceKey<CreativeModeTab> MEDIEVAL_TAB =
            ResourceKey.create(
                    Registries.CREATIVE_MODE_TAB,
                    Identifier.fromNamespaceAndPath(
                            MedievalProgression.MOD_ID,
                            "medieval_tab"
                    )
            );


    public static void register() {

        CreativeModeTab tab =
                CreativeModeTab.builder(
                                CreativeModeTab.Row.TOP,
                                0
                        )
                        .title(Component.translatable(
                                "itemGroup.medievalprogression.medieval_tab"
                        ))
                        .icon(() -> new ItemStack(ModItems.ALLOY_FURNACE))
                        .displayItems((parameters, output) -> {

                            output.accept(ModItems.ROSEGOLD_HELMET);
                            output.accept(ModItems.ROSEGOLD_CHESTPLATE);
                            output.accept(ModItems.ROSEGOLD_LEGGINGS);
                            output.accept(ModItems.ROSEGOLD_BOOTS);

                            output.accept(ModItems.ROSEGOLD_HORSE_ARMOR);
                            output.accept(ModItems.ROSEGOLD_NAUTILUS_ARMOR);

                            output.accept(ModItems.ROSEGOLD_SPEAR);
                            output.accept(ModItems.ROSEGOLD_SWORD);
                            output.accept(ModItems.ROSEGOLD_PICKAXE);
                            output.accept(ModItems.ROSEGOLD_AXE);
                            output.accept(ModItems.ROSEGOLD_SHOVEL);
                            output.accept(ModItems.ROSEGOLD_HOE);
                            
                            output.accept(ModItems.ROSEGOLD_INGOT);
                            output.accept(ModItems.ROSEGOLD_NUGGET);
                            output.accept(ModItems.ROSEGOLD_BLOCK);
                            
                            output.accept(ModItems.NETHERITE_NUGGET);
                            output.accept(ModItems.STAR_FRAGMENT);
                            output.accept(ModItems.HALF_STAR);
                            output.accept(ModItems.ENDERITE_NUGGET);
                            
                            output.accept(ModItems.ALLOY_FURNACE);

                            output.accept(ModItems.SMOOTH_DEEPSLATE);
                            output.accept(ModItems.DIAMOND_UPGRADE);
                            output.accept(ModItems.MEDIEVAL_GUIDE);

                        })
                        .build();


        Registry.register(
                BuiltInRegistries.CREATIVE_MODE_TAB,
                MEDIEVAL_TAB,
                tab
        );
    }
}
