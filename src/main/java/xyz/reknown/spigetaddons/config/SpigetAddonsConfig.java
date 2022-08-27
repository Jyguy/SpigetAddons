package xyz.reknown.spigetaddons.config;

import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.JVMAnnotationPropertyCollector;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyType;
import lombok.Getter;

import java.io.File;

@SuppressWarnings("FieldMayBeFinal")
public class SpigetAddonsConfig extends Vigilant {
    @Property(
            type = PropertyType.TEXT,
            name = "API Key",
            description = "Your Hypixel API key. Using '/api new' will auto-fill this field.",
            category = "General",
            subcategory = "API",
            protectedText = true
    )
    @Getter private String apiKey = "";

    @Property(
            type = PropertyType.SWITCH,
            name = "Replace Bridge Name",
            description = "Replaces the bridge bot's name with a configured name.",
            category = "General",
            subcategory = "Bridge"
    )
    @Getter private boolean replaceBridgeName = false;

    @Property(
            type = PropertyType.TEXT,
            name = "Bridge User Name",
            description = "The username of the bridge bot.",
            category = "General",
            subcategory = "Bridge"
    )
    @Getter private String bridgeUsername = "zZzBRIDGE";

    @Property(
            type = PropertyType.TEXT,
            name = "Replacement Text",
            description = "The text to replace the username with. Formatting codes are allowed.",
            category = "General",
            subcategory = "Bridge"
    )
    @Getter private String replacementText = "&b&lBRIDGE";

    @Property(
            type = PropertyType.SWITCH,
            name = "Low Arrow Warning",
            description = "A warning when you have below X arrows after switching worlds.",
            category = "Dungeons",
            subcategory = "General"
    )
    @Getter private boolean lowArrowWarning = false;

    @Property(
            type = PropertyType.SLIDER,
            name = "Low Arrow Threshold",
            description = "How many arrows to check for before activating the low arrow warning.",
            category = "Dungeons",
            subcategory = "General",
            min = 1,
            max = 2880
    )
    @Getter private int lowArrowThreshold = 400;

    public SpigetAddonsConfig() {
        super(new File("./config/spigetaddons.toml"), "SpigetAddons", new JVMAnnotationPropertyCollector(), new Sorter());
        initialize();

        addDependency("bridgeUsername", "replaceBridgeName");
        addDependency("replacementText", "replaceBridgeName");

        addDependency("lowArrowThreshold", "lowArrowWarning");
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
        markDirty();
    }
}
