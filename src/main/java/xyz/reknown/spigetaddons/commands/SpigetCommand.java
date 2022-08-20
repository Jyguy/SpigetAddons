package xyz.reknown.spigetaddons.commands;

import gg.essential.api.commands.Command;
import gg.essential.api.commands.DefaultHandler;
import gg.essential.api.utils.GuiUtil;
import xyz.reknown.spigetaddons.SpigetAddons;

public class SpigetCommand extends Command {
    public SpigetCommand() {
        super("spiget");
    }

    @DefaultHandler
    public void handle() {
        GuiUtil.open(SpigetAddons.INSTANCE.getConfig().gui());
    }
}
