package com.thebroman10.medievalprogression.screen;

import com.thebroman10.medievalprogression.MedievalProgression;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.inventory.MenuType;

public class ModMenuTypes {

    public static MenuType<AlloyFurnaceMenu> ALLOY_FURNACE;


    public static void initialize() {

        Identifier id = Identifier.fromNamespaceAndPath(
                MedievalProgression.MOD_ID,
                "alloy_furnace"
        );

        ResourceKey<MenuType<?>> key = ResourceKey.create(
                Registries.MENU,
                id
        );

        ALLOY_FURNACE = Registry.register(
                BuiltInRegistries.MENU,
                id,
                new MenuType<>(
                        AlloyFurnaceMenu::new,
                        net.minecraft.world.flag.FeatureFlags.DEFAULT_FLAGS
                )
        );
    }
}