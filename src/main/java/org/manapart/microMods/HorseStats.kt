package org.manapart.microMods

import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.animal.horse.AbstractHorse
import net.minecraft.world.entity.animal.horse.Llama
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Items
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import java.awt.TextComponent
import kotlin.math.pow


class HorseStats {
    fun getStats(event: PlayerInteractEvent.EntityInteract) {
        if (event.entity != null && event.level != null && event.target is AbstractHorse) {
            val player = event.entity
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

    private fun getHealthColor(health: Double): ChatFormatting = getColor(health, listOf(20, 23, 26, 29))
    private fun getSpeedColor(speed: Double): ChatFormatting = getColor(speed, listOf(7, 9, 11, 13))
    private fun getJumpColor(jump: Double): ChatFormatting = getColor(jump, listOf(1, 2, 3, 4))
    private fun getSlotsColor(slots: Double): ChatFormatting = getColor(slots, listOf(3, 6, 9, 12))


    private fun getColor(amount: Double, options: List<Int>): ChatFormatting {
        val intAmount = amount.toInt()
        return when {
            intAmount <= options[0] -> ChatFormatting.GRAY
            intAmount <= options[1] -> ChatFormatting.WHITE
            intAmount <= options[2] -> ChatFormatting.YELLOW
            intAmount <= options[3] -> ChatFormatting.AQUA
            else -> ChatFormatting.LIGHT_PURPLE
        }
    }

    private fun displayLlamaMessage(player: Player, horseEntity: Llama, colourHealth: ChatFormatting, colourSpeed: ChatFormatting, health: Double, speed: Double) {
        val slots = horseEntity.strength.toDouble() * 3
        val colourSlots = getSlotsColor(slots)
        val message = Component.literal("")
            .append("Health: %.0f".formatted(health, colourHealth))
            .append(" Speed: %.1f".formatted(speed, colourSpeed))
            .append(" Chest Slots: %.1f".formatted(slots, colourSlots))

        player.displayClientMessage(message, true)
    }

    private fun displayHorseMessage(player: Player, colourSpeed: ChatFormatting, colourJump: ChatFormatting, health: Double, speed: Double, jump: Double, colourHealth: ChatFormatting) {
        val message = Component.literal("")
            .append("Health: %.0f".formatted(health, colourHealth))
            .append(" Speed: %.1f".formatted(speed, colourSpeed))
            .append(" Jump Height: %.1f".formatted(jump, colourJump))

        player.displayClientMessage(message, true)
    }

    private fun String.formatted(amount: Double, color: ChatFormatting): MutableComponent {
        return Component.literal(String.format(this, amount)).withStyle(color)
    }
}