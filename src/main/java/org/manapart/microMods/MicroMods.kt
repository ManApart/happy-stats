package org.manapart.microMods

import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.fml.common.Mod
import thedarkcolour.kotlinforforge.forge.FORGE_BUS
import thedarkcolour.kotlinforforge.forge.MOD_BUS

const val MODID = "micromods"

@Mod(MODID)
object MicroMods {
    private val stats = HorseStats()

    init {
        ModItems.REGISTRY.register(MOD_BUS)
        FORGE_BUS.addListener { event: PlayerInteractEvent.EntityInteract -> stats.getStats(event) }
    }

}