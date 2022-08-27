package xyz.reknown.spigetaddons.hud;

import gg.essential.elementa.ElementaVersion;
import gg.essential.elementa.components.Window;
import gg.essential.universal.UMatrixStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.reknown.spigetaddons.util.SkyblockChecker;

public class HUD {
    private final Window window;

    public HUD() {
        window = new Window(ElementaVersion.V2);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post event) {
        if (event.type == RenderGameOverlayEvent.ElementType.TEXT) {
            if (SkyblockChecker.onSkyblock) window.draw(UMatrixStack.Compat.INSTANCE.get());
        }
    }

    public Window getWindow() {
        return window;
    }
}
