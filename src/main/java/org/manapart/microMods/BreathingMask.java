package org.manapart.microMods;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;

public class BreathingMask extends ArmorItem {
    public BreathingMask() {
        super(ArmorMaterial.LEATHER, EquipmentSlotType.HEAD, (new Item.Properties()).group(ItemGroup.COMBAT));
    }
}
