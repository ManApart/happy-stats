package org.manapart.microMods

import net.minecraft.network.chat.FormattedText
import net.minecraft.world.entity.animal.horse.AbstractHorse
import net.minecraft.world.entity.animal.horse.Llama
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Items
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.internal.TextComponentMessageFormatHandler
import kotlin.math.pow


class HorseStats {
    fun getStats(event: PlayerInteractEvent.EntityInteract) {
        if (event.player != null && event.world != null && event.target is AbstractHorse) {
            val player = event.player
            val horseEntity = event.target as AbstractHorse
            if (player.isCrouching && player.mainHandItem.item === Items.COMPASS) {
                event.isCanceled = true
                displayHorseStats(player, horseEntity)
            }
        }
    }

    private fun displayHorseStats(player: Player, horseEntity: AbstractHorse) {
        val health = horseEntity.getAttributeValue(Attributes.MAX_HEALTH)
        var speed = horseEntity.getAttributeValue(Attributes.MOVEMENT_SPEED)
        var jump = horseEntity.customJump
        jump = -0.1817584952 * jump.pow(3.0) + 3.689713992 * jump.pow(2.0) + 2.128599134 * jump - 0.343930367 // https://minecraft.gamepedia.com/Horse
        speed *= 43
        val colourHealth = getHealthColor(health)
        val colourSpeed = getSpeedColor(speed)
        val colourJump = getJumpColor(jump)
        if (horseEntity is Llama) {
            displayLlamaMessage(player, horseEntity, colourHealth, colourSpeed, health, speed)
        } else {
            displayHorseMessage(player, colourSpeed, colourJump, health, speed, jump, colourHealth)
        }
    }

    private fun getHealthColor(health: Double): TextFormatting {
        return when {
            health <= 20.0 -> TextFormatting.GRAY
            health <= 23.0 -> TextFormatting.WHITE
            health <= 26.0 -> TextFormatting.YELLOW
            health <= 29.0 -> TextFormatting.AQUA
            else -> TextFormatting.LIGHT_PURPLE
        }
    }

    private fun getSpeedColor(speed: Double): TextFormatting {
       return when {
            speed <= 7 -> TextFormatting.GRAY
            speed <= 9 -> TextFormatting.WHITE
            speed <= 11 -> TextFormatting.YELLOW
            speed <= 13 -> TextFormatting.AQUA
            else -> TextFormatting.LIGHT_PURPLE
        }
    }

    private fun getJumpColor(jump: Double): TextFormatting {
         return when {
             jump <= 1.50 -> TextFormatting.GRAY
             jump <= 2.0 -> TextFormatting.WHITE
             jump <= 3.0 -> TextFormatting.YELLOW
             jump <= 4.0 -> TextFormatting.AQUA
            else -> TextFormatting.LIGHT_PURPLE
        }
    }

    private fun getSlotsColor(slots: Double): TextFormatting {
        return when {
            slots <= 3.0 -> TextFormatting.GRAY
            slots <= 6.0 -> TextFormatting.WHITE
            slots <= 9.0 -> TextFormatting.YELLOW
            slots <= 12.0 -> TextFormatting.AQUA
            else -> TextFormatting.LIGHT_PURPLE
        }
    }

    private fun displayLlamaMessage(player: Player, horseEntity: Llama, colourHealth: TextFormatting, colourSpeed: TextFormatting, health: Double, speed: Double) {
        val slots = horseEntity.strength.toDouble() * 3
        val colourSlots = getSlotsColor(slots)
        player.displayClientMessage(TranslationTextComponent(String.format("%sHealth: %.0f %sSpeed: %.1f %sChest Slots: %.0f", colourHealth, health, colourSpeed, speed, colourSlots, slots)), true)
    }

    private fun displayHorseMessage(player: Player, colourSpeed: TextFormatting, colourJump: TextFormatting, health: Double, speed: Double, jump: Double, colourHealth: TextFormatting) {
        player.displayClientMessage(TranslationTextComponent(String.format("%sHealth: %.0f %sSpeed: %.1f %sJump Height: %.1f", colourHealth, health, colourSpeed, speed, colourJump, jump)), true)
    }
}