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

class RocketItem: FireworkRocketItem(Properties().tab(ItemGroupMM.instance)) {

    override fun useOn(p_41216_: UseOnContext): InteractionResult {
        val level = p_41216_.level
//        if (!level.isClientSide) {
//            val itemstack = p_41216_.itemInHand
//            val vec3 = p_41216_.clickLocation
//            val direction = p_41216_.clickedFace
//            val fireworkrocketentity = FireworkRocketEntity(
//                level,
//                p_41216_.player,
//                vec3.x + direction.stepX.toDouble() * 0.15,
//                vec3.y + direction.stepY.toDouble() * 0.15,
//                vec3.z + direction.stepZ.toDouble() * 0.15,
//                itemstack
//            )
//            level.addFreshEntity(fireworkrocketentity)
//            //Don't shrink the item stack
//        }
        return InteractionResult.sidedSuccess(level.isClientSide)
    }

    override fun use(p_41218_: Level, p_41219_: Player, p_41220_: InteractionHand): InteractionResultHolder<ItemStack> {
        return if (p_41219_.isFallFlying) {
            val itemstack = p_41219_.getItemInHand(p_41220_)
            if (!p_41218_.isClientSide) {
                val fireworkrocketentity = FireworkRocketEntity(p_41218_, itemstack, p_41219_)
                p_41218_.addFreshEntity(fireworkrocketentity)
//                if (!p_41219_.abilities.instabuild) {
//                    itemstack.shrink(1)
//                }
                p_41219_.awardStat(Stats.ITEM_USED[this])
            }
            InteractionResultHolder.sidedSuccess(p_41219_.getItemInHand(p_41220_), p_41218_.isClientSide())
        } else {
            InteractionResultHolder.pass(p_41219_.getItemInHand(p_41220_))
        }
    }

}