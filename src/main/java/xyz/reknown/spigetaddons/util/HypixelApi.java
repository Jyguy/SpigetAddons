package xyz.reknown.spigetaddons.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.commons.codec.binary.Base64;
import xyz.reknown.spigetaddons.SpigetAddons;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HypixelApi {
    public static NBTTagCompound decodeData(String rawData) {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64.decodeBase64(rawData))) {
            return CompressedStreamTools.readCompressed(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return new NBTTagCompound();
        }
    }

    public static JsonObject getLatestProfile(String uuid) {
        try {
            JsonObject response = queryAPI("https://api.hypixel.net/skyblock/profiles?key=" + SpigetAddons.INSTANCE.getConfig().getApiKey() + "&uuid=" + uuid);
            if (!response.get("success").getAsBoolean()) return null;

            JsonArray array = response.getAsJsonArray("profiles");
            long latestSave = -1;
            JsonObject latestProfile = null;
            for (JsonElement profile : array) {
                JsonElement lastSave = profile.getAsJsonObject().get("last_save");
                if (lastSave == null) continue;

                long lastSaveLong = lastSave.getAsLong();
                if (latestSave > lastSaveLong) continue;

                latestSave = lastSaveLong;
                latestProfile = profile.getAsJsonObject();
            }

            return latestProfile;
        } catch (IOException ignored) {
            return null;
        }
    }

    public static JsonObject queryAPI(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("accept", "application/json");
        InputStream stream = connection.getInputStream();
        JsonParser parser = new JsonParser();
        return (JsonObject) parser.parse(new InputStreamReader(stream, StandardCharsets.UTF_8));
    }
}
