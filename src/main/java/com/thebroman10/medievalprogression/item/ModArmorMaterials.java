package com.thebroman10.medievalprogression.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.Map;

public class ModArmorMaterials {

    public static final ArmorMaterial ROSEGOLD = new ArmorMaterial(
            40,

            Map.of(
                    ArmorType.HELMET, 3,
                    ArmorType.CHESTPLATE, 8,
                    ArmorType.LEGGINGS, 6,
                    ArmorType.BOOTS, 3
            ),

            15,

            SoundEvents.ARMOR_EQUIP_IRON,

            2.0f,

            0.1f,

            ItemTags.IRON_TOOL_MATERIALS,

            EquipmentAssets.createId("rosegold")
    );
}