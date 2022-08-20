package xyz.reknown.spigetaddons.config;

import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyType;

import java.io.File;

@SuppressWarnings("FieldMayBeFinal")
public class SpigetAddonsConfig extends Vigilant {
    @Property(
            type = PropertyType.SWITCH,
            name = "Replace Bridge Name",
            description = "Replaces the bridge bot's name with a configured name.",
            category = "General",
            subcategory = "Bridge"
    )
    private boolean replaceBridgeName = false;

    @Property(
            type = PropertyType.TEXT,
            name = "Bridge User Name",
            description = "The username of the bridge bot.",
            category = "General",
            subcategory = "Bridge"
    )
    private String bridgeUsername = "zZzBRIDGE";

    @Property(
            type = PropertyType.TEXT,
            name = "Replacement Text",
            description = "The text to replace the username with. Formatting codes are allowed.",
            category = "General",
            subcategory = "Bridge"
    )
    private String replacementText = "&b&lBRIDGE";

    public SpigetAddonsConfig() {
        super(new File("./config/spigetaddons.toml"));
        initialize();

        addDependency("bridgeUsername", "replaceBridgeName");
        addDependency("replacementText", "replaceBridgeName");
    }

    public String getBridgeUsername() {
        return bridgeUsername;
    }

    public String getReplacementText() {
        return replacementText;
    }

    public boolean isReplaceBridgeName() {
        return replaceBridgeName;
    }
}
