package org.manapart.microMods

import net.minecraft.stats.Stats
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.projectile.FireworkRocketEntity
import net.minecraft.world.item.FireworkRocketItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level

class RocketItem(coolDownTimeInSeconds: Int): FireworkRocketItem(Properties().tab(ItemGroupMM.instance)) {
    override fun useOn(p_41216_: UseOnContext): InteractionResult {
        return InteractionResult.sidedSuccess(p_41216_.level.isClientSide)
    }

    override fun use(p_41218_: Level, player: Player, p_41220_: InteractionHand): InteractionResultHolder<ItemStack> {
        return if (player.isFallFlying) {
            val itemstack = player.getItemInHand(p_41220_)
            if (!p_41218_.isClientSide) {
                val fireworkrocketentity = FireworkRocketEntity(p_41218_, itemstack, player)
                p_41218_.addFreshEntity(fireworkrocketentity)
                player.awardStat(Stats.ITEM_USED[this])
            }
            InteractionResultHolder.sidedSuccess(player.getItemInHand(p_41220_), p_41218_.isClientSide())
        } else {
            InteractionResultHolder.pass(player.getItemInHand(p_41220_))
        }
    }

}