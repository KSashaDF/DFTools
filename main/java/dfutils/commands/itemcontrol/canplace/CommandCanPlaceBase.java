package dfutils.commands.itemcontrol.canplace;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.ParametersAreNonnullByDefault;

import static dfutils.commands.MessageUtils.commandError;
import static dfutils.commands.MessageUtils.commandInfo;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CommandCanPlaceBase extends CommandBase implements IClientCommand {
    
    private final Minecraft minecraft = Minecraft.getMinecraft();
    
    public String getName() {
        return "canplace";
    }
    
    public String getUsage(ICommandSender sender) {
        return "§e/canplace add <block>\n" +
                "§e/canplace remove <block>\n" +
                "§e/canplace clear";
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
            commandError("You need to be in build mode or dev mode to do this!");
            return;
        }
        
        if (commandArgs.length == 0) {
            commandInfo("Usage:\n" + getUsage(sender));
            return;
        }
        
        switch (commandArgs[0]) {
            case "add":
                CommandCanPlaceAdd.executeAddCanPlace(sender, commandArgs);
                return;
            
            case "remove":
                CommandCanPlaceRemove.executeRemoveCanPlace(sender, commandArgs);
                return;
            
            case "clear":
                CommandCanPlaceClear.executeClearCanPlace(sender, commandArgs);
                return;
            
            default:
                commandInfo("Usage:\n" + getUsage(sender));
        }
    }
}
