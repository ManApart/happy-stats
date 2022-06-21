package org.manapart.microMods

import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.MinecartItem

class ItemGroupMM private constructor(index: Int, label: String) : CreativeModeTab(index, label) {
    override fun makeIcon(): ItemStack {
        return ItemStack(RocketItem())
//        return ItemStack(MicroMods.micromodsIcon)
    }

    companion object {
        val instance = ItemGroupMM(getGroupCountSafe(), "micromods")
    }
}