package xyz.reknown.spigetaddons.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class PlayerUtil {
    public static void message(String msg) {
        if (Minecraft.getMinecraft().isCallingFromMinecraftThread()) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(StringUtil.formatColor(msg)));
        } else {
            MainThread.scheduleTask(() -> Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(StringUtil.formatColor(msg))));
        }
    }
}
