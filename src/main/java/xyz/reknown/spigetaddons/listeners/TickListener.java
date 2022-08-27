package xyz.reknown.spigetaddons.listeners;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import xyz.reknown.spigetaddons.util.MainThread;
import xyz.reknown.spigetaddons.util.SkyblockChecker;

public class TickListener {
    private int ticks;

    public TickListener() {
        ticks = 0;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            MainThread.executeTasks();

            ticks += 1;

            if (ticks == 20) {
                ticks = 0;
                SkyblockChecker.checkScoreboard();
            }
        }
    }
}
