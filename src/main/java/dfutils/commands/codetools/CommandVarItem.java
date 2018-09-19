package dfutils.commands.codetools;

import dfutils.codetools.CodeItems;
import diamondcore.utils.ItemUtils;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.ParametersAreNonnullByDefault;

import static diamondcore.utils.MessageUtils.errorMessage;
import static diamondcore.utils.MessageUtils.infoMessage;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CommandVarItem extends CommandBase implements IClientCommand {

    private static final Minecraft minecraft = Minecraft.getMinecraft();

    public String getName() {
        return "var";
    }

    public String getUsage(ICommandSender sender) {
        return "§b/var <name>";
    }

    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    public void execute(MinecraftServer server, ICommandSender sender, String[] commandArgs) {

        //Checks if player should be able to execute command.
        if (!minecraft.player.isCreative()) {
            errorMessage("You need to be in build mode or dev mode to do this!");
            return;
        }

        if (commandArgs.length == 0) {
            infoMessage("Usage: " + getUsage(sender));
            return;
        }

        String variableName = CommandBase.buildString(commandArgs, 0);
        if (variableName.endsWith(" -s")) {
            variableName = variableName.substring(0, variableName.length() - 3);
            ItemUtils.setItemInHotbar(CodeItems.getVarItem(variableName, 1, true), false);
        } else {
            ItemUtils.setItemInHotbar(CodeItems.getVarItem(variableName, 1, false), false);
        }
    }
}
