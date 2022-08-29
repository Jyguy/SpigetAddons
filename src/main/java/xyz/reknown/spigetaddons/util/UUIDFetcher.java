package xyz.reknown.spigetaddons.util;

import gg.essential.api.EssentialAPI;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

public class UUIDFetcher {
    public static String fetch(String playerName) {
        try {
            return EssentialAPI.getMojangAPI().getUUID(playerName).get().toString(); // TODO null handling?
        } catch (CancellationException | ExecutionException | InterruptedException ignored) {
            return null;
        }
    }
}
