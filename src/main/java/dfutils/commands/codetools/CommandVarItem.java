package dfutils.commands.codetools;

import dfutils.codetools.CodeItems;
import dfutils.commands.CommandUtils;
import dfutils.utils.ItemUtils;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.ParametersAreNonnullByDefault;

import static dfutils.utils.MessageUtils.errorMessage;
import static dfutils.utils.MessageUtils.infoMessage;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CommandVarItem extends CommandBase implements IClientCommand {

    private final Minecraft minecraft = Minecraft.getMinecraft();

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

        //Sends item to the server.
        ItemUtils.setItemInHotbar(CodeItems.getVarItem(CommandUtils.parseColorCodes(CommandBase.buildString(commandArgs, 0)), 1), false);
    }
}
