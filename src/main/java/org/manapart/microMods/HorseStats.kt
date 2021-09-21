package org.manapart.microMods

import net.minecraft.entity.ai.attributes.Attributes
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract
import net.minecraft.entity.passive.horse.AbstractHorseEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.text.TextFormatting
import net.minecraft.entity.passive.horse.LlamaEntity
import net.minecraft.item.Items
import net.minecraft.util.text.TranslationTextComponent
import kotlin.math.pow

class HorseStats {
    fun getStats(event: EntityInteract) {
        if (event.player != null && event.world != null && event.target is AbstractHorseEntity) {
            val player = event.player
            val horseEntity = event.target as AbstractHorseEntity
            if (player.isCrouching && player.mainHandItem.item === Items.COMPASS) {
                event.isCanceled = true
                displayHorseStats(player, horseEntity)
            }
        }
    }

    private fun displayHorseStats(player: PlayerEntity, horseEntity: AbstractHorseEntity) {
        val health = horseEntity.getAttributeValue(Attributes.MAX_HEALTH)
        var speed = horseEntity.getAttributeValue(Attributes.MOVEMENT_SPEED)
        var jump = horseEntity.customJump
        jump = -0.1817584952 * jump.pow(3.0) + 3.689713992 * jump.pow(2.0) + 2.128599134 * jump - 0.343930367 // https://minecraft.gamepedia.com/Horse
        speed *= 43
        val colourHealth = getHealthColor(health)
        val colourSpeed = getSpeedColor(speed)
        val colourJump = getJumpColor(jump)
        if (horseEntity is LlamaEntity) {
            displayLlamaMessage(player, horseEntity, colourHealth, colourSpeed, health, speed)
        } else {
            displayHorseMessage(player, colourSpeed, colourJump, health, speed, jump, colourHealth)
        }
    }

    private fun getHealthColor(health: Double): TextFormatting {
        var color = TextFormatting.WHITE
        if (health <= 20) {
            color = TextFormatting.GRAY
        } else if (health <= 23) {
            color = TextFormatting.WHITE
        } else if (health <= 26) {
            color = TextFormatting.YELLOW
        } else if (health <= 29) {
            color = TextFormatting.AQUA
        } else if (health <= Double.MAX_VALUE) {
            color = TextFormatting.LIGHT_PURPLE
        }
        return color
    }

    private fun getSpeedColor(speed: Double): TextFormatting {
        return if (speed <= 7) {
            TextFormatting.GRAY
        } else if (speed <= 9) {
            TextFormatting.WHITE
        } else if (speed <= 11) {
            TextFormatting.YELLOW
        } else if (speed <= 13) {
            TextFormatting.AQUA
        } else if (speed <= Double.MAX_VALUE) {
            TextFormatting.LIGHT_PURPLE
        } else {
            TextFormatting.LIGHT_PURPLE
        }
    }

    private fun getJumpColor(jump: Double): TextFormatting {
        return if (jump <= 1.50) {
            TextFormatting.GRAY
        } else if (jump <= 2.0) {
            TextFormatting.WHITE
        } else if (jump <= 3.0) {
            TextFormatting.YELLOW
        } else if (jump <= 4.0) {
            TextFormatting.AQUA
        } else if (jump <= Double.MAX_VALUE) {
            TextFormatting.LIGHT_PURPLE
        } else {
            TextFormatting.WHITE
        }
    }

    private fun getSlotsColor(slots: Double): TextFormatting {
        return if (slots <= 3) {
            TextFormatting.GRAY
        } else if (slots <= 6) {
            TextFormatting.WHITE
        } else if (slots <= 9) {
            TextFormatting.YELLOW
        } else if (slots <= 12) {
            TextFormatting.AQUA
        } else if (slots <= Double.MAX_VALUE) {
            TextFormatting.LIGHT_PURPLE
        } else {
            TextFormatting.WHITE
        }
    }

    private fun displayLlamaMessage(player: PlayerEntity, horseEntity: LlamaEntity, colourHealth: TextFormatting, colourSpeed: TextFormatting, health: Double, speed: Double) {
        var slots = horseEntity.strength.toDouble()
        slots *= 3
        val colourSlots = getSlotsColor(slots)
        player.displayClientMessage(TranslationTextComponent(String.format("%sHealth: %.0f %sSpeed: %.1f %sChest Slots: %.0f", colourHealth, health, colourSpeed, speed, colourSlots, slots)), true)
    }

    private fun displayHorseMessage(player: PlayerEntity, colourSpeed: TextFormatting, colourJump: TextFormatting, health: Double, speed: Double, jump: Double, colourHealth: TextFormatting) {
        player.displayClientMessage(TranslationTextComponent(String.format("%sHealth: %.0f %sSpeed: %.1f %sJump Height: %.1f", colourHealth, health, colourSpeed, speed, colourJump, jump)), true)
    }
}