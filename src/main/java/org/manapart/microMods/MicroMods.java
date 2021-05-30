package org.manapart.microMods;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MicroMods.MODID)
@Mod.EventBusSubscriber(modid = MicroMods.MODID)
public class MicroMods {
    static final String MODID = "micromods";
    private final HorseStats stats = new HorseStats();

    public MicroMods() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void getStats(PlayerInteractEvent.EntityInteract event) {
        stats.getStats(event);
    }

}
