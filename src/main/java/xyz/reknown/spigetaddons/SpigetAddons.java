package xyz.reknown.spigetaddons;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(name = "SpigetAddons", modid = "spigetaddons", version = "1.0")

public class SpigetAddons {

    public Logger LOGGER = LogManager.getLogger("SpigetAddons");

    @EventHandler
    @SuppressWarnings("unused")
    public void init(FMLInitializationEvent event) {
    }
}