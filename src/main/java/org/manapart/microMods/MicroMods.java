package org.manapart.microMods;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MicroMods.MODID)
@Mod.EventBusSubscriber(modid = MicroMods.MODID)
public class MicroMods {
    static final String MODID = "micromods";
    private final HorseStats stats = new HorseStats();

    public MicroMods() {
        MinecraftForge.EVENT_BUS.register(this);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::getStats);
    }

    @SubscribeEvent
    public void getStats(PlayerInteractEvent.EntityInteract event) {
        stats.getStats(event);
    }

}
