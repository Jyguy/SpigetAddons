package xyz.reknown.spigetaddons.listeners;

import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.reknown.spigetaddons.util.SkyblockChecker;

public class WorldListener {
    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        SkyblockChecker.resetAttempts();
    }
}
