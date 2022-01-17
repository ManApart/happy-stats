package org.manapart.microMods

import javafx.scene.paint.Color.AQUA
import net.minecraft.network.chat.Style
import net.minecraft.network.chat.TextColor
import net.minecraft.network.chat.TextComponent
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.animal.horse.AbstractHorse
import net.minecraft.world.entity.animal.horse.Llama
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Items
import net.minecraftforge.event.entity.player.PlayerInteractEvent
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

    private fun getHealthColor(health: Double): TextColor = getColor(health, listOf(20,23,26,29))
    private fun getSpeedColor(speed: Double): TextColor = getColor(speed, listOf(7, 9, 11, 13))
    private fun getJumpColor(jump: Double): TextColor = getColor(jump, listOf(1, 2, 3, 4))
    private fun getSlotsColor(slots: Double): TextColor = getColor(slots, listOf(3, 6, 9, 12))


    private fun getColor(amount: Double, options: List<Int>): TextColor {
        val intAmount = amount.toInt()
        return when {
            intAmount<= options[0] -> TextColor.parseColor("gray")
            intAmount <= options[1] -> TextColor.parseColor("white")
            intAmount <= options[2] -> TextColor.parseColor("yellow")
            intAmount <= options[3] -> TextColor.parseColor("aqua")
            else -> TextColor.parseColor("light_purple")
        }!!
    }

    private fun displayLlamaMessage(player: Player, horseEntity: Llama, colourHealth: TextColor, colourSpeed: TextColor, health: Double, speed: Double) {
        val slots = horseEntity.strength.toDouble() * 3
        val colourSlots = getSlotsColor(slots)
        player.displayClientMessage(TextComponent(String.format("%sHealth: %.0f %sSpeed: %.1f %sChest Slots: %.0f", colourHealth, health, colourSpeed, speed, colourSlots, slots)), true)
    }

    private fun displayHorseMessage(player: Player, colourSpeed: TextColor, colourJump: TextColor, health: Double, speed: Double, jump: Double, colourHealth: TextColor) {
        //Not enough energy to re figure out how to color the text
        player.displayClientMessage(TextComponent(String.format("Health: %.0f Speed: %.1f Jump Height: %.1f", health, speed, jump)), true)
    }
}