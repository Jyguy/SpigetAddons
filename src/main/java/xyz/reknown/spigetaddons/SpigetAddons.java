package xyz.reknown.spigetaddons;

import gg.essential.api.EssentialAPI;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.reknown.spigetaddons.commands.SpigetCommand;
import xyz.reknown.spigetaddons.config.SpigetAddonsConfig;
import xyz.reknown.spigetaddons.listeners.ClientChatReceivedListener;

@Mod(name = "SpigetAddons", modid = "spigetaddons", version = "1.0")

public class SpigetAddons {
    @Mod.Instance
    public static SpigetAddons INSTANCE;

    private final Logger LOGGER = LogManager.getLogger("SpigetAddons");
    private SpigetAddonsConfig config;

    @EventHandler
    @SuppressWarnings("unused")
    public void init(FMLInitializationEvent event) {
        config = new SpigetAddonsConfig();
        config.preload();

        EssentialAPI.getCommandRegistry().registerCommand(new SpigetCommand());

        MinecraftForge.EVENT_BUS.register(new ClientChatReceivedListener());
    }

    public SpigetAddonsConfig getConfig() {
        return config;
    }

    public Logger getLogger() {
        return LOGGER;
    }
}
