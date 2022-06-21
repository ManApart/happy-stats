package org.manapart.microMods

import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.registerObject

object ModItems {
    val REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, MODID)

    val TELEPORT_ITEM by REGISTRY.registerObject("rocketitem") {
        RocketItem(5)
    }
}