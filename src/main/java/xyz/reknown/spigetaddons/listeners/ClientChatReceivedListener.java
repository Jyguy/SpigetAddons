package xyz.reknown.spigetaddons.listeners;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.reknown.spigetaddons.SpigetAddons;
import xyz.reknown.spigetaddons.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientChatReceivedListener {
    private final Pattern apiKeyPattern = Pattern.compile("^Your new API key is (\\S+)$");

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent event) {
        if (event.type != 0) return;

        Matcher matcher = apiKeyPattern.matcher(event.message.getUnformattedText());
        if (matcher.find()) {
            String apiKey = matcher.group(1);
            SpigetAddons.INSTANCE.getConfig().setApiKey(apiKey);
            EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
            if (player == null) return;
            player.addChatMessage(new ChatComponentText(StringUtil.formatColor("&a[SpigetAddons] Set up API key! ✔")));
        }
    }
}
