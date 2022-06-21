package org.manapart.microMods

import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.MinecartItem

private val iconInstance: RocketItem by lazy { RocketItem(1) }

class ItemGroupMM private constructor(index: Int, label: String) : CreativeModeTab(index, label) {
    override fun makeIcon(): ItemStack {
        return ItemStack(iconInstance)
    }

    companion object {
        val instance = ItemGroupMM(getGroupCountSafe(), "micromods")
    }
}