package dfutils.codetools.commands.locations;

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
public class CommandLocBase extends CommandBase implements IClientCommand {

    private final Minecraft minecraft = Minecraft.getMinecraft();

    public String getName() {
        return "loc";
    }

    public String getUsage(ICommandSender sender) {
        return "§e/loc align \n" +
                "§e/loc center \n" +
                "§e/loc move ";
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
            infoMessage("Usage: \n" + getUsage(sender));
            return;
        }

        switch (commandArgs[0]) {
            case "align":
                CommandLocAlign.executeExportTemplate(sender, commandArgs);
                break;

            case "center":
                break;

            case "move":
                break;
        }
    }
}
