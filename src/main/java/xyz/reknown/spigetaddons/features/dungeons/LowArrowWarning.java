package xyz.reknown.spigetaddons.features.dungeons;

import com.google.gson.JsonObject;
import gg.essential.elementa.UIComponent;
import gg.essential.elementa.components.UIText;
import gg.essential.elementa.constraints.CenterConstraint;
import gg.essential.elementa.constraints.PixelConstraint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import xyz.reknown.spigetaddons.SpigetAddons;
import xyz.reknown.spigetaddons.util.HypixelApi;
import xyz.reknown.spigetaddons.util.MainThread;
import xyz.reknown.spigetaddons.util.SkyblockChecker;

import java.awt.*;

public class LowArrowWarning {
    private int arrowCount = 0;
    private long lastAttempt = 0;
    private final UIComponent component;
    private int rehideTicks = 0;

    public LowArrowWarning() {
        component = new UIText("Low Arrows!", true)
                .setX(new CenterConstraint())
                .setY(new CenterConstraint())
                .setTextScale(new PixelConstraint(5f))
                .setColor(Color.RED)
                .setChildOf(SpigetAddons.INSTANCE.getHud().getWindow());
        component.hide();
    }

    @SubscribeEvent
    public void checkAPI(WorldEvent.Load event) {
        if (!SkyblockChecker.onSkyblock) return;
        if (!SpigetAddons.INSTANCE.getConfig().isLowArrowWarning()) return;

        long current = System.currentTimeMillis();
        if (current - lastAttempt <= 10000) return; // Don't check if it hasn't been more than 10 seconds since last attempt
        lastAttempt = System.currentTimeMillis();

        new Thread(() -> {
            EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
            if (player == null) return;
            String uuid = player.getUniqueID().toString().replaceAll("-", "");
            JsonObject profile = HypixelApi.getLatestProfile(uuid);
            if (profile == null) return;

            JsonObject quiverObj = profile.getAsJsonObject("members").getAsJsonObject(uuid).getAsJsonObject("quiver");
            if (quiverObj == null) return; // Inventory API disabled
            String rawData = quiverObj.get("data").getAsString();
            NBTTagCompound quiverData = HypixelApi.decodeData(rawData);
            NBTTagList arrows = quiverData.getTagList("i", Constants.NBT.TAG_COMPOUND);
            int arrowCount = 0;
            for (int i = 0; i < arrows.tagCount(); i++) {
                NBTTagCompound arrow = arrows.getCompoundTagAt(i);
                int id = arrow.getInteger("id");
                if (id != 262) continue; // ignore non-arrow items
                arrowCount += arrow.getInteger("Count");
            }

            this.arrowCount = arrowCount;
            sendWarning();
        }).start();
    }

    @SubscribeEvent
    public void checkQuiver(GuiScreenEvent.BackgroundDrawnEvent event) {
        if (!SpigetAddons.INSTANCE.getConfig().isLowArrowWarning()) return;
        if (event.gui instanceof GuiContainer) {
            GuiContainer gui = (GuiContainer) event.gui;
            Container container = gui.inventorySlots;
            if (container instanceof ContainerChest) {
                ContainerChest chestContainer = (ContainerChest) container;
                String title = chestContainer.getLowerChestInventory().getDisplayName().getUnformattedText().trim();
                if (title.equals("Quiver")) {
                    arrowCount = -1;
                    for (Slot slot : chestContainer.inventorySlots) {
                        if (slot.getHasStack() && slot.getStack().getItem() == Items.arrow) {
                            arrowCount += slot.getStack().stackSize;
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (rehideTicks <= 0) {
            rehideTicks = 0;
            component.hide();
        } else {
            rehideTicks -= 1;
        }
    }

    private void sendWarning() {
        if (arrowCount > SpigetAddons.INSTANCE.getConfig().getLowArrowThreshold()) return;
        rehideTicks = 100;
        MainThread.scheduleTask(() -> {
            component.unhide(true);
            EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
            if (player != null) player.playSound("random.orb", 1, 0.5f);
        });
    }
}
