package diamondcore.commands.internal;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;
import java.awt.datatransfer.StringSelection;

import static diamondcore.utils.MessageUtils.actionMessage;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CommandClipboard extends CommandBase implements IClientCommand {

    public String getName() {
        return "internal-set_clipboard";
    }

    public String getUsage(ICommandSender sender) {
        return "/internal-set_clipboard true|false <clipboard message>";
    }

    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    public void execute(MinecraftServer server, ICommandSender sender, String[] commandArgs) {

        //This simply sets the system clipboard to the text specified by the command.
        if (commandArgs.length > 1) {

            //If the show message argument is set to "true", send the action message.
            if (commandArgs[0].equals("true")) {
                actionMessage("Copied message to your clipboard!");
            }

            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(CommandBase.buildString(commandArgs, 1)), null);
        }
    }
}
