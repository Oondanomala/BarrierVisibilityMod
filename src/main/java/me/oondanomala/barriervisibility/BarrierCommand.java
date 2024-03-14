package me.oondanomala.barriervisibility;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class BarrierCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "barrier";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/barrier";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        BarrierVisibility.toggleBarriers();
    }
}
