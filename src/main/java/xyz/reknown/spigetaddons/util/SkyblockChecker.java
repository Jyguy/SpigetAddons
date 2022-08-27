package xyz.reknown.spigetaddons.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.util.StringUtils;

public class SkyblockChecker {
    public static boolean onSkyblock = false;
    private static int attempts = 15;
    private static boolean hasScoreboard = false;

    public static void checkScoreboard() {
        if (attempts <= 0) {
            if (!hasScoreboard) onSkyblock = false;
            return;
        }

        attempts -= 1;
        WorldClient world = Minecraft.getMinecraft().theWorld;
        if (world == null) {
            hasScoreboard = false;
            return;
        }

        ScoreObjective objective = world.getScoreboard().getObjectiveInDisplaySlot(1);
        if (objective == null) {
            hasScoreboard = false;
            return;
        }

        hasScoreboard = true;

        String title = StringUtils.stripControlCodes(objective.getDisplayName());
        onSkyblock = title.startsWith("SKYBLOCK");
        attempts = 0; // Scoreboard has been resolved, don't check until next world load
    }

    public static void resetAttempts() {
        attempts = 15;
    }
}
