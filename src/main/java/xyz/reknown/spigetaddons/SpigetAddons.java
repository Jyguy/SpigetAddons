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
import xyz.reknown.spigetaddons.features.RenameBridge;
import xyz.reknown.spigetaddons.features.dungeons.LowArrowWarning;
import xyz.reknown.spigetaddons.hud.HUD;
import xyz.reknown.spigetaddons.listeners.ClientChatReceivedListener;
import xyz.reknown.spigetaddons.listeners.TickListener;
import xyz.reknown.spigetaddons.listeners.WorldListener;

@Mod(name = "SpigetAddons", modid = "spigetaddons", version = "1.1.0")

public class SpigetAddons {
    @Mod.Instance
    public static SpigetAddons INSTANCE;

    private final Logger LOGGER = LogManager.getLogger("SpigetAddons");
    private SpigetAddonsConfig config;
    private HUD hud;

    @EventHandler
    @SuppressWarnings("unused")
    public void init(FMLInitializationEvent event) {
        config = new SpigetAddonsConfig();
        config.preload();

        EssentialAPI.getCommandRegistry().registerCommand(new SpigetCommand());

        // HUD
        hud = new HUD();
        MinecraftForge.EVENT_BUS.register(hud);

        // Features
        MinecraftForge.EVENT_BUS.register(new LowArrowWarning());
        MinecraftForge.EVENT_BUS.register(new RenameBridge());

        // Listeners
        MinecraftForge.EVENT_BUS.register(new ClientChatReceivedListener());
        MinecraftForge.EVENT_BUS.register(new TickListener());
        MinecraftForge.EVENT_BUS.register(new WorldListener());
    }

    public SpigetAddonsConfig getConfig() {
        return config;
    }

    public Logger getLogger() {
        return LOGGER;
    }

    public HUD getHud() {
        return hud;
    }
}
