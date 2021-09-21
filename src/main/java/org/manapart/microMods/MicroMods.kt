package org.manapart.microMods

import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.fml.common.Mod
import thedarkcolour.kotlinforforge.forge.FORGE_BUS

const val MODID = "micromods"

@Mod(MODID)
object MicroMods {
    private val stats = HorseStats()

    init {
        FORGE_BUS.addListener { event: PlayerInteractEvent.EntityInteract -> stats.getStats(event) }
    }

}