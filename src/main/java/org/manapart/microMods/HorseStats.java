package org.manapart.microMods;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class HorseStats {

    public void getStats(PlayerInteractEvent.EntityInteract event) {
        if (event.getPlayer() != null && event.getWorld() != null && event.getTarget() instanceof AbstractHorseEntity) {
            PlayerEntity player = event.getPlayer();
            AbstractHorseEntity horseEntity = (AbstractHorseEntity) event.getTarget();
            if (player.isCrouching() && player.getHeldItemMainhand().getItem() == Items.COMPASS) {
                event.setCanceled(true);
                displayHorseStats(player, horseEntity);
            }
        }
    }

    private void displayHorseStats(PlayerEntity player, AbstractHorseEntity horseEntity) {
        double health = horseEntity.getAttribute(SharedMonsterAttributes.MAX_HEALTH).getValue();
        double speed = horseEntity.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue();
        double jump = horseEntity.getHorseJumpStrength();

        jump = -0.1817584952 * Math.pow(jump, 3) + 3.689713992 * Math.pow(jump, 2) + 2.128599134 * jump - 0.343930367; // https://minecraft.gamepedia.com/Horse
        speed = speed * 43;

        TextFormatting colourHealth = getHealthColor(health);
        TextFormatting colourSpeed = getSpeedColor(speed);
        TextFormatting colourJump = getJumpColor(jump);


        if (horseEntity instanceof LlamaEntity) {
            displayLlamaMessage(player, (LlamaEntity) horseEntity, colourHealth, colourSpeed, health, speed);
        } else {
            displayHorseMessage(player, colourSpeed, colourJump, health, speed, jump, colourHealth);
        }
    }

    private TextFormatting getHealthColor(double health) {
        TextFormatting color = TextFormatting.WHITE;
        if (health <= 20) {
            color = TextFormatting.GRAY;
        } else if (health <= 23) {
            color = TextFormatting.WHITE;
        } else if (health <= 26) {
            color = TextFormatting.YELLOW;
        } else if (health <= 29) {
            color = TextFormatting.AQUA;
        } else if (health <= Double.MAX_VALUE) {
            color = TextFormatting.LIGHT_PURPLE;
        }
        return color;
    }

    private TextFormatting getSpeedColor(double speed) {
        TextFormatting color;
        if (speed <= 7) {
            color = TextFormatting.GRAY;
        } else if (speed <= 9) {
            color = TextFormatting.WHITE;
        } else if (speed <= 11) {
            color = TextFormatting.YELLOW;
        } else if (speed <= 13) {
            color = TextFormatting.AQUA;
        } else if (speed <= Double.MAX_VALUE) {
            color = TextFormatting.LIGHT_PURPLE;
        } else {
            color = TextFormatting.LIGHT_PURPLE;
        }
        return color;
    }

    private TextFormatting getJumpColor(double jump) {
        TextFormatting color = TextFormatting.WHITE;
        if (jump <= 1.50) {
            color = TextFormatting.GRAY;
        } else if (jump <= 2.0) {
            color = TextFormatting.WHITE;
        } else if (jump <= 3.0) {
            color = TextFormatting.YELLOW;
        } else if (jump <= 4.0) {
            color = TextFormatting.AQUA;
        } else if (jump <= Double.MAX_VALUE) {
            color = TextFormatting.LIGHT_PURPLE;
        }
        return color;
    }

    private TextFormatting getSlotsColor(double slots) {
        TextFormatting color = TextFormatting.WHITE;
        if (slots <= 3) {
            color = TextFormatting.GRAY;
        } else if (slots <= 6) {
            color = TextFormatting.WHITE;
        } else if (slots <= 9) {
            color = TextFormatting.YELLOW;
        } else if (slots <= 12) {
            color = TextFormatting.AQUA;
        } else if (slots <= Double.MAX_VALUE) {
            color = TextFormatting.LIGHT_PURPLE;
        }
        return color;
    }

    private void displayLlamaMessage(PlayerEntity player, LlamaEntity horseEntity, TextFormatting colourHealth, TextFormatting colourSpeed, double health, double speed) {
        double slots = horseEntity.getStrength();

        slots = slots * 3;

        TextFormatting colourSlots = getSlotsColor(slots);

        player.sendStatusMessage(new TranslationTextComponent(String.format("%sHealth: %.0f %sSpeed: %.1f %sChest Slots: %.0f", colourHealth, health, colourSpeed, speed, colourSlots, slots)), true);
    }

    private void displayHorseMessage(PlayerEntity player, TextFormatting colourSpeed, TextFormatting colourJump, double health, double speed, double jump, TextFormatting colourHealth) {
        player.sendStatusMessage(new TranslationTextComponent(String.format("%sHealth: %.0f %sSpeed: %.1f %sJump Height: %.1f", colourHealth, health, colourSpeed, speed, colourJump, jump)), true);
    }

}
