package xyz.reknown.spigetaddons.listeners;

import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.reknown.spigetaddons.SpigetAddons;
import xyz.reknown.spigetaddons.config.SpigetAddonsConfig;
import xyz.reknown.spigetaddons.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientChatReceivedListener {
    private final Pattern guildChatPattern = Pattern.compile("^§2Guild > (?:\\S+ )?(\\w{3,16})(?: §[a-z]\\[[A-Z]+])?§f: .+");

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent event) {
        if (event.type != 0) return; // don't touch non-chat messages
        SpigetAddonsConfig config = SpigetAddons.INSTANCE.getConfig();
        if (config.isReplaceBridgeName()) {
            String msg = event.message.getUnformattedText();
            Matcher matcher = guildChatPattern.matcher(msg);
            if (matcher.find() && matcher.group(1).equalsIgnoreCase(config.getBridgeUsername())) {
                event.message.getSiblings().set(0, new ChatComponentText(String.format("§r§2Guild > §r%s§f: ", StringUtil.formatColor(config.getReplacementText()))));
            }
        }
    }
}
