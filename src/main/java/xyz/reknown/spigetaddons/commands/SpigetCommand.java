package xyz.reknown.spigetaddons.commands;

import com.google.gson.JsonObject;
import gg.essential.api.commands.Command;
import gg.essential.api.commands.DefaultHandler;
import gg.essential.api.commands.DisplayName;
import gg.essential.api.commands.SubCommand;
import gg.essential.api.utils.GuiUtil;
import net.minecraft.client.Minecraft;
import xyz.reknown.spigetaddons.SpigetAddons;
import xyz.reknown.spigetaddons.util.HypixelApi;
import xyz.reknown.spigetaddons.util.PlayerUtil;
import xyz.reknown.spigetaddons.util.UUIDFetcher;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class SpigetCommand extends Command {
    private final Map<String, String> trophyfish;

    public SpigetCommand() {
        super("spiget");

        trophyfish = new LinkedHashMap<>();

        // Common
        trophyfish.put("sulphur_skitter", "&fSulphur Skitter");
        trophyfish.put("obfuscated_fish_1", "&fObfuscated 1");
        trophyfish.put("steaming_hot_flounder", "&fSteaming-Hot Flounder");
        trophyfish.put("gusher", "&fGusher");
        trophyfish.put("blobfish", "&fBlobfish");

        // Uncommon
        trophyfish.put("obfuscated_fish_2", "&aObfuscated 2");
        trophyfish.put("slugfish", "&aSlugfish");
        trophyfish.put("flyfish", "&aFlyfish");

        // Rare
        trophyfish.put("obfuscated_fish_3", "&9Obfuscated 3");
        trophyfish.put("lava_horse", "&9Lavahorse");
        trophyfish.put("mana_ray", "&9Mana Ray");
        trophyfish.put("volcanic_stonefish", "&9Volcanic Stonefish");
        trophyfish.put("vanille", "&9Vanille");

        // Epic
        trophyfish.put("skeleton_fish", "&5Skeleton Fish");
        trophyfish.put("moldfin", "&5Moldfin");
        trophyfish.put("soul_fish", "&5Soul Fish");
        trophyfish.put("karate_fish", "&5Karate Fish");

        // Legendary
        trophyfish.put("golden_fish", "&6Golden Fish");
    }

    @DefaultHandler
    public void handle() {
        GuiUtil.open(SpigetAddons.INSTANCE.getConfig().gui());
    }

    @SubCommand(value = "trophyfish", description = "Displays a player's trophy fishing stats.", aliases = { "tf" })
    public void trophyfish(@DisplayName("player") Optional<String> playerName) {
        new Thread(() -> {
            String uuid;
            if (playerName.isPresent()) uuid = UUIDFetcher.fetch(playerName.get());
            else uuid = Minecraft.getMinecraft().thePlayer.getUniqueID().toString();

            if (uuid == null) {
                PlayerUtil.message("&cFailed to obtain UUID data.");
                return;
            }

            uuid = uuid.replaceAll("-", "");

            JsonObject profileData = HypixelApi.getLatestProfile(uuid); // TODO profile selection support
            if (profileData == null) {
                PlayerUtil.message("&cFailed to obtain profile data. Check the username/API key!");
                return;
            }

            JsonObject trophyFishData = profileData
                    .getAsJsonObject("members")
                    .getAsJsonObject(uuid)
                    .getAsJsonObject("trophy_fish");

            PlayerUtil.message("&4----------");
            int total = 0;
            for (Map.Entry<String, String> entry : trophyfish.entrySet()) {
                int typeTotal = trophyFishData.has(entry.getKey()) ? trophyFishData.get(entry.getKey()).getAsInt() : 0;
                total += typeTotal;
                int bronze = trophyFishData.has(entry.getKey() + "_bronze") ? trophyFishData.get(entry.getKey() + "_bronze").getAsInt() : 0;
                int silver = trophyFishData.has(entry.getKey() + "_silver") ? trophyFishData.get(entry.getKey() + "_silver").getAsInt() : 0;
                int gold = trophyFishData.has(entry.getKey() + "_gold") ? trophyFishData.get(entry.getKey() + "_gold").getAsInt() : 0;
                int diamond = trophyFishData.has(entry.getKey() + "_diamond") ? trophyFishData.get(entry.getKey() + "_diamond").getAsInt() : 0;
                PlayerUtil.message(String.format("%s&r &e(%d)&f: &8%d &7%d &6%d &b%d", entry.getValue(), typeTotal, bronze, silver, gold, diamond));
            }

            PlayerUtil.message(String.format("&6Total: &e&l%d", total));
            PlayerUtil.message("&4----------");
        }).start();
    }
}
