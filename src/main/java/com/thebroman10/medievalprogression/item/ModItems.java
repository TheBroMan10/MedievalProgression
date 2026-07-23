package com.thebroman10.medievalprogression.item;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.thebroman10.medievalprogression.MedievalProgression;
import com.thebroman10.medievalprogression.block.ModBlocks;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAssets;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ShovelItem;

public class ModItems {


    public static final Item MEDIEVAL_GUIDE = register(
            "medieval_guide",
            GuideBookItem::new,
            new Item.Properties()
    );


    public static final Item ROSEGOLD_INGOT = register(
            "rosegold_ingot",
            Item::new,
            new Item.Properties()
                    .trimMaterial(
                            ResourceKey.create(
                                    Registries.TRIM_MATERIAL,
                                    Identifier.fromNamespaceAndPath(
                                            MedievalProgression.MOD_ID,
                                            "rosegold"
                                    )
                            )
                    )
    );


    public static final Item ROSEGOLD_NUGGET = register(
            "rosegold_nugget",
            Item::new,
            new Item.Properties()
    );


    public static final Item NETHERITE_NUGGET = register(
            "netherite_nugget",
            Item::new,
            new Item.Properties()
    );

    public static final Item STAR_FRAGMENT = register(
            "star_fragment",
            GlintItem::new,
            new Item.Properties()
                    .rarity(net.minecraft.world.item.Rarity.EPIC)
    );

    public static final Item HALF_STAR = register(
            "half_star",
            GlintItem::new,
            new Item.Properties()
                    .rarity(net.minecraft.world.item.Rarity.EPIC)
    );

    public static final Item ENDERITE_NUGGET = register(
            "enderite_nugget",
            Item::new,
            new Item.Properties()
    );


    public static final Item DIAMOND_UPGRADE = register(
            "diamond_upgrade_template",
            properties -> new SmithingTemplateItem(
                    Component.translatable(
                            "item.medievalprogression.diamond_upgrade.applies_to"
                    ),
                    Component.translatable(
                            "item.medievalprogression.diamond_upgrade.ingredients"
                    ),
                    Component.translatable(
                            "item.medievalprogression.diamond_upgrade.base_slot_description"
                    ),
                    Component.translatable(
                            "item.medievalprogression.diamond_upgrade.additions_slot_description"
                    ),
                    List.of(
                            Identifier.fromNamespaceAndPath(
                                    "minecraft",
                                    "container/slot/helmet"
                            )
                    ),
                    List.of(
                            Identifier.fromNamespaceAndPath(
                                    "minecraft",
                                    "container/slot/diamond"
                            )
                    ),
                    properties
            ),
            new Item.Properties()
    );


    public static final ToolMaterial ROSEGOLD_TOOL = new ToolMaterial(
            ToolMaterial.IRON.incorrectBlocksForDrops(),
            512,
            12.0f,
            2.5f,
            25,
            TagKey.create(
                    Registries.ITEM,
                    Identifier.fromNamespaceAndPath(
                            MedievalProgression.MOD_ID,
                            "rosegold_repair_items"
                    )
            )
    );


    public static final ArmorMaterial ROSEGOLD_ARMOR = new ArmorMaterial(
            25,
            Map.of(
                    ArmorType.BOOTS, 3,
                    ArmorType.LEGGINGS, 4,
                    ArmorType.CHESTPLATE, 7,
                    ArmorType.HELMET, 3
            ),
            25,
            SoundEvents.ARMOR_EQUIP_GOLD,
            1.0f,
            0.0f,
            TagKey.create(
                    Registries.ITEM,
                    Identifier.fromNamespaceAndPath(
                            MedievalProgression.MOD_ID,
                            "rosegold_repair_items"
                    )
            ),
            ResourceKey.create(
                    EquipmentAssets.ROOT_ID,
                    Identifier.fromNamespaceAndPath(
                            MedievalProgression.MOD_ID,
                            "rosegold"
                    )
            )
    );


    public static final ArmorMaterial ROSEGOLD_ANIMAL_ARMOR = new ArmorMaterial(
            9,
            Map.of(
                    ArmorType.BODY, 9
            ),
            0,
            SoundEvents.ARMOR_EQUIP_GOLD,
            0.0f,
            0.0f,
            TagKey.create(
                    Registries.ITEM,
                    Identifier.fromNamespaceAndPath(
                            MedievalProgression.MOD_ID,
                            "rosegold_repair_items"
                    )
            ),
            ResourceKey.create(
                    EquipmentAssets.ROOT_ID,
                    Identifier.fromNamespaceAndPath(
                            MedievalProgression.MOD_ID,
                            "rosegold"
                    )
            )
    );
    
    
    public static final Item ROSEGOLD_PICKAXE = register(
            "rosegold_pickaxe",
            Item::new,
            new Item.Properties()
                    .pickaxe(ROSEGOLD_TOOL, 1.0f, -2.8f)
    );


    public static final Item ROSEGOLD_AXE = register(
            "rosegold_axe",
            properties -> new AxeItem(
                    ROSEGOLD_TOOL,
                    5.5f,
                    -3.0f,
                    properties
            ),
            new Item.Properties()
    );


        public static final Item ROSEGOLD_SHOVEL = register(
                "rosegold_shovel",
                properties -> new ShovelItem(
                        ROSEGOLD_TOOL,
                        1.5f,
                        -3.0f,
                        properties
                ),
                new Item.Properties()
        );


        public static final Item ROSEGOLD_HOE = register(
                "rosegold_hoe",
                properties -> new HoeItem(
                        ROSEGOLD_TOOL,
                        -0.5f,
                        -1.0f,
                        properties
                ),
                new Item.Properties()
        );


    public static final Item ROSEGOLD_SWORD = register(
            "rosegold_sword",
            Item::new,
            new Item.Properties()
                    .sword(ROSEGOLD_TOOL, 3.0f, -2.4f)
    );


    public static final Item ROSEGOLD_SPEAR = register(
            "rosegold_spear",
            Item::new,
            new Item.Properties()
                    .spear(
                            ROSEGOLD_TOOL,

                            1.025f, // attackDuration
                            1.0125f, // damageMultiplier
                            0.5f, // delay
                            3.0f, // dismountTime

                            10.5f, // min attacker speed
                            6.625f, // min attacker speed damage

                            5.1f, // min relative speed
                            10.625f, // min relative speed damage

                            4.6f // relative speed
                    )
    );


    public static final Item ROSEGOLD_HELMET = register(
            "rosegold_helmet",
            Item::new,
            new Item.Properties()
                    .humanoidArmor(
                            ROSEGOLD_ARMOR,
                            ArmorType.HELMET
                    )
    );


    public static final Item ROSEGOLD_CHESTPLATE = register(
            "rosegold_chestplate",
            Item::new,
            new Item.Properties()
                    .humanoidArmor(
                            ROSEGOLD_ARMOR,
                            ArmorType.CHESTPLATE
                    )
    );


    public static final Item ROSEGOLD_LEGGINGS = register(
            "rosegold_leggings",
            Item::new,
            new Item.Properties()
                    .humanoidArmor(
                            ROSEGOLD_ARMOR,
                            ArmorType.LEGGINGS
                    )
    );


    public static final Item ROSEGOLD_BOOTS = register(
            "rosegold_boots",
            Item::new,
            new Item.Properties()
                    .humanoidArmor(
                            ROSEGOLD_ARMOR,
                            ArmorType.BOOTS
                    )
    );


    public static final Item ROSEGOLD_HORSE_ARMOR = register(
            "rosegold_horse_armor",
            Item::new,
            new Item.Properties()
                    .horseArmor(ROSEGOLD_ANIMAL_ARMOR)
    );


    public static final Item ROSEGOLD_NAUTILUS_ARMOR = register(
            "rosegold_nautilus_armor",
            Item::new,
            new Item.Properties()
                    .nautilusArmor(ROSEGOLD_ANIMAL_ARMOR)
    );


    public static final Item ROSEGOLD_BLOCK = register(
            "rosegold_block",
            properties -> new BlockItem(
                    ModBlocks.ROSEGOLD_BLOCK,
                    properties
            ),
            new Item.Properties()
    );


    public static final Item SMOOTH_DEEPSLATE = register(
            "smooth_deepslate",
            properties -> new BlockItem(
                    ModBlocks.SMOOTH_DEEPSLATE,
                    properties
            ),
            new Item.Properties()
    );


    public static final Item ALLOY_FURNACE = register(
            "alloy_furnace",
            properties -> new BlockItem(
                    ModBlocks.ALLOY_FURNACE,
                    properties
            ),
            new Item.Properties()
    );


    private static Item register(
            String name,
            Function<Item.Properties, Item> factory,
            Item.Properties properties
    ) {

        Identifier id = Identifier.fromNamespaceAndPath(
                MedievalProgression.MOD_ID,
                name
        );

        ResourceKey<Item> key = ResourceKey.create(
                Registries.ITEM,
                id
        );

        properties.setId(key);

        return Registry.register(
                BuiltInRegistries.ITEM,
                id,
                factory.apply(properties)
        );
    }


    public static void initialize() {

    }

}
